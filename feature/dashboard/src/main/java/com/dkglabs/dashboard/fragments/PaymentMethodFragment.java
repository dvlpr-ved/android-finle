package com.dkglabs.dashboard.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.dashboard.R;
import com.dkglabs.dashboard.databinding.FragmentPaymentMethodBinding;
import com.dkglabs.payment.activities.PaymentActivity;
import com.google.android.material.radiobutton.MaterialRadioButton;


public class PaymentMethodFragment extends BaseFragment implements View.OnClickListener {

    private int totalPayable;
    private String payEmiId;

    private ActivityResultLauncher<Intent> paymentResult = null;
    private FragmentPaymentMethodBinding binding = null;

    public PaymentMethodFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(binding.getRoot()).popBackStack();
            }
        };

        paymentResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                if (o.getResultCode() == Activity.RESULT_OK) {
                    UIUtils.showToast(context, "Payment Success");
                    Navigation.findNavController(binding.getRoot()).popBackStack();
                } else if(o.getResultCode() == Activity.RESULT_CANCELED){
                    UIUtils.showToast(context, "Payment Failed");
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentMethodBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        totalPayable = bundle.getInt("total_payable");
        payEmiId = bundle.getString("emi_id");
        binding.totalPay.setText(String.format(getString(R.string.placeholder_pay_due), totalPayable));
    }

    private void initializeView() {
        setUpBackToolbar("Payment Methods");
        binding.buttonConfirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonConfirm) {
            openPaymentActivity();
        }
    }

    private void openPaymentActivity() {
        int id = -1;
        id = binding.radioGroupPayment.getCheckedRadioButtonId();
        if (id == -1) {
            UIUtils.showSnackbar(binding.getRoot(), "Select payment method.");
            return;
        }

        MaterialRadioButton radioButton = binding.getRoot().findViewById(id);
        String paymentMethod = radioButton.getTag().toString();
        Intent intent = new Intent(getActivity(), PaymentActivity.class);
        intent.putExtra("payment_method", paymentMethod);
        intent.putExtra("total_payable", String.valueOf(totalPayable));
        intent.putExtra("emi_id", payEmiId);
        paymentResult.launch(intent);
    }
}