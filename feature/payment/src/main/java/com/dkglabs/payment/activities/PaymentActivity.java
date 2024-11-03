package com.dkglabs.payment.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.cashfree.pg.api.CFPaymentGatewayService;
import com.cashfree.pg.core.api.CFSession;
import com.cashfree.pg.core.api.callback.CFCheckoutResponseCallback;
import com.cashfree.pg.core.api.exception.CFException;
import com.cashfree.pg.core.api.utils.CFErrorResponse;
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutPayment;
import com.cashfree.pg.core.api.webcheckout.CFWebCheckoutTheme;
import com.cashfree.pg.ui.api.upi.intent.CFIntentTheme;
import com.cashfree.pg.ui.api.upi.intent.CFUPIIntentCheckout;
import com.cashfree.pg.ui.api.upi.intent.CFUPIIntentCheckoutPayment;
import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.model.request.PaymentOrderRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.PaymentOrderResponse;
import com.dkglabs.model.response.PaymentResponse;
import com.dkglabs.payment.R;
import com.dkglabs.payment.databinding.ActivityPaymentBinding;
import com.dkglabs.payment.utils.PaymentConst;
import com.dkglabs.payment.view_model.PaymentViewModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.PaymentManager;

import java.util.Arrays;

public class PaymentActivity extends BaseActivity implements ResponseListener, CFCheckoutResponseCallback {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final int STATUS_CHECK_DELAY = 10000;
    private Runnable runnable = null;
    private static final int CREATE_ORDER = 1001;
    private static final int PAYMENT_STATUS = 1002;
    String orderID = "ORDER_ID";
    String paymentSessionID = "TOKEN";
    //For production
    //CFSession.Environment cfEnvironment = CFSession.Environment.PRODUCTION;
    //For test
    CFSession.Environment cfEnvironment = CFSession.Environment.SANDBOX;
    private String paymentMethod;
    private String totalPayable;
    private String payEmiId;
    private FragmentManager fragmentManager;
    private NavController navController;
    private ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeView();
    }

    private void initializeView() {
        fragmentManager = getSupportFragmentManager();
        NavHostFragment navHostFragment = (NavHostFragment) fragmentManager.findFragmentById(R.id.navHostFragment);
        navController = navHostFragment.getNavController();

        paymentMethod = getIntent().getStringExtra("payment_method");
        totalPayable = getIntent().getStringExtra("total_payable");
        payEmiId = getIntent().getStringExtra("emi_id");
        try {
            // If you are using a fragment then you need to add this line inside onCreate() of your Fragment
            CFPaymentGatewayService.getInstance().setCheckoutCallback(this);
        } catch (CFException e) {
            LogsManager.printLog(e);
        }

        createOrder(totalPayable, payEmiId);

        PaymentViewModel paymentViewModel = new PaymentViewModel();
        viewModel = getViewModel();
        viewModel.setSelectedItem(paymentViewModel);
        viewModel.getSelectedItem().observe(this, new Observer() {
            @Override
            public void onChanged(Object o) {
                paymentActions((PaymentViewModel) o);
            }
        });
    }

    private void paymentActions(PaymentViewModel paymentViewModel) {
        switch (paymentViewModel.getPaymentAction()) {
            case PaymentConst.PAYMENT_RETRY:
                navController.navigate(R.id.processingPaymentFragment);
                createOrder(totalPayable, payEmiId);
                break;
            case PaymentConst.PAYMENT_DONE:
                setResult(Activity.RESULT_OK);
                closeActivity();
                break;
            case PaymentConst.PAYMENT_CANCEL:
                setResult(Activity.RESULT_CANCELED);
                closeActivity();
                break;
        }
    }

    private void createOrder(String totalPayable, String payEmiId) {
        PaymentOrderRequest orderRequest = new PaymentOrderRequest();
        orderRequest.setAmount(totalPayable);
        orderRequest.setLoanEmiId(payEmiId);
        orderRequest.setCustomerName(PersistentManager.getUserResponse().getFirstName() + " " + PersistentManager.getUserResponse().getLastName());
        orderRequest.setCustomerPhone(PersistentManager.getUserResponse().getMobileNumber());
        orderRequest.setCustomerEmail(PersistentManager.getUserResponse().getEmail());
        orderRequest.setCustomerId(PersistentManager.getUserResponse().getUserId());
        orderRequest.setLoanId(PersistentManager.getUserResponse().getLoanId());

        PaymentManager.createOrder(CREATE_ORDER, orderRequest,  this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        switch (requestCode) {
            case CREATE_ORDER:
                PaymentOrderResponse orderResponse = (PaymentOrderResponse) response.getResponseData();
                if (orderResponse != null) {
                    paymentSessionID = orderResponse.getPaymentSessionId();
                    orderID = orderResponse.getOrderId();
                    doCheckoutPayment();
                }
                return;
            case PAYMENT_STATUS:
                PaymentResponse paymentResponse = (PaymentResponse) response.getResponseData();
                switch (paymentResponse.getStatus()) {
                    case PaymentConst.PAYMENT_INT:

                        break;
                    case PaymentConst.PAYMENT_TXF:
                        navController.navigate(R.id.paymentFailedFragment);
                        handler.removeCallbacks(runnable);
                        break;
                    case PaymentConst.PAYMENT_TXS:
                        navController.navigate(R.id.paymentSuccessFragment);
                        handler.removeCallbacks(runnable);
                        break;
                }

        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {

    }

    @Override
    public void onFailure(int requestCode, Throwable t) {

    }

    @Override
    public void commonCall(int requestCode) {

    }

    @Override
    public void onPaymentVerify(String orderID) {
        LogsManager.printLog("Payment Status", "SDK : Success");
        startPaymentVerify(orderID);
    }

    @Override
    public void onPaymentFailure(CFErrorResponse cfErrorResponse, String orderID) {
        navController.navigate(R.id.paymentFailedFragment);
        LogsManager.printLog("Payment Status", "SDK : Fail");
    }

    private void startPaymentVerify(String orderID) {
        PaymentManager.status(PAYMENT_STATUS, orderID,  this);
        handler.postDelayed(runnable = () -> {
            startPaymentVerify(orderID);
        }, STATUS_CHECK_DELAY);
    }

    private void doCheckoutPayment() {
        try {
            CFSession cfSession = new CFSession.CFSessionBuilder()
                    .setEnvironment(cfEnvironment)
                    .setPaymentSessionID(paymentSessionID)
                    .setOrderId(orderID).build();
            if (paymentMethod.equals("web")) {
                doDropCheckoutPayment(cfSession);
            } else if (paymentMethod.equals("upi")) {
                doUPIIntentCheckoutPayment(cfSession);
            }
        } catch (CFException exception) {
            LogsManager.printLog(exception);
        }
    }

    private void doDropCheckoutPayment(CFSession cfSession) {
        try {
            CFWebCheckoutTheme cfTheme = new CFWebCheckoutTheme.CFWebCheckoutThemeBuilder()
                    .setNavigationBarBackgroundColor("#006687")
                    .setNavigationBarTextColor("#FFFFFF")
                    .build();
            CFWebCheckoutPayment cfWebCheckoutPayment = new CFWebCheckoutPayment.CFWebCheckoutPaymentBuilder()
                    .setSession(cfSession)
                    .setCFWebCheckoutUITheme(cfTheme)
                    .build();
            CFPaymentGatewayService gatewayService = CFPaymentGatewayService.getInstance();
            gatewayService.doPayment(PaymentActivity.this, cfWebCheckoutPayment);
        } catch (CFException exception) {
            LogsManager.printLog(exception);
        }
    }

    private void doUPIIntentCheckoutPayment(CFSession cfSession) {
        try {
            CFIntentTheme cfTheme = new CFIntentTheme.CFIntentThemeBuilder()
                    .setBackgroundColor("#FFFFFF")
                    .setPrimaryTextColor("#006687")
                    .build();

            CFUPIIntentCheckout cfupiIntentCheckout = new CFUPIIntentCheckout.CFUPIIntentBuilder()
                    // Use either the enum or the application package names to order the UPI apps as per your needed
                    // Remove both if you want to use the default order which cashfree provides based on the popularity
                    // NOTE - only one is needed setOrder or setOrderUsingPackageName
                    .setOrder(Arrays.asList(
                            CFUPIIntentCheckout.CFUPIApps.BHIM,
                            CFUPIIntentCheckout.CFUPIApps.PAYTM,
                            CFUPIIntentCheckout.CFUPIApps.GOOGLE_PAY,
                            CFUPIIntentCheckout.CFUPIApps.PHONEPE))
                    //.setOrderUsingPackageName(Arrays.asList("com.dreamplug.androidapp", "in.org.npci.upiapp"))
                    .build();

            CFUPIIntentCheckoutPayment cfupiIntentCheckoutPayment = new CFUPIIntentCheckoutPayment.CFUPIIntentPaymentBuilder()
                    .setSession(cfSession)
                    .setCfUPIIntentCheckout(cfupiIntentCheckout)
                    .setCfIntentTheme(cfTheme).build();
            CFPaymentGatewayService gatewayService = CFPaymentGatewayService.getInstance();
            gatewayService.doPayment(PaymentActivity.this, cfupiIntentCheckoutPayment);
        } catch (CFException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null)
            binding = null;
        handler.removeCallbacks(runnable);
    }
}