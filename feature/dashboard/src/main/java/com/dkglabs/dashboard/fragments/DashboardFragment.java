package com.dkglabs.dashboard.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.dashboard.utils.EmiConst;
import com.dkglabs.dashboard.R;
import com.dkglabs.dashboard.databinding.FragmentDashboardBinding;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanDataResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.CollectionManager;
import com.dkglabs.remote.manager.LoanManager;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.color.MaterialColors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Himanshu Srivastava on 9/18/2022.
 */
public class DashboardFragment extends BaseFragment implements View.OnClickListener, ResponseListener, SeekBar.OnSeekBarChangeListener, SeekBar.OnTouchListener {

    private DueEmiFragment dueEmiFragment;
    private LoanDataResponse loanDataResponse;
    private List<LoanEmiDetailResponse> dueEmiList;
    private List<LoanEmiDetailResponse> dueEmiList2;
    private int dueAmount = 0;
    private int paidAmount = 0;
    private FragmentDashboardBinding binding = null;

    public DashboardFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (LoanPersistentManager.getLoanId().equals("")) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dashboardFragment_to_blankDashboardFragment);
            return;
        }

        getLoanDetails();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding = null;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initializeView() {
        setUpBackToolbar(String.format(getString(R.string.hi_user_name), PersistentManager.getUserResponse().getFirstName()));
        UIUtils.hideViewGone(binding.llDashboard);
        binding.viewAllEmi.setOnClickListener(this);
        binding.payEmi.setOnClickListener(this);

        binding.loanProgress.loanProgressBar.setOnSeekBarChangeListener(this);
        binding.loanProgress.loanProgressBar.setOnTouchListener(this);

        FragmentTransaction transactionOptional = getChildFragmentManager().beginTransaction();
        dueEmiFragment = DueEmiFragment.newInstance(null);
        transactionOptional.replace(R.id.due_emi_fragment_container, dueEmiFragment).commit();

        intiPieChart();
    }

    private void getLoanDetails() {
        LoanManager.getLoanEmiDetails(1003,
                PersistentManager.getUserResponse().getLoanId(),
                this);
        binding.llProgress.setMessage("Loading loan details...");
        CollectionManager.getLoanPaymentDetails(1001,
                PersistentManager.getUserResponse().getUserId(),
                LoanPersistentManager.getLoanId(),
                this);

    }

    private void intiPieChart() {
        binding.pieChart.setNoDataText("EMI details");
        binding.pieChart.setNoDataTextColor(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorOnSurface, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_onSurface)));

        binding.pieChart.setDrawEntryLabels(false);
        /*Description description = new Description();
        description.setTextColor(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorPrimary, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_primary)));
        binding.pieChart.setDescription(description);*/
        binding.pieChart.getDescription().setEnabled(false);
        binding.pieChart.getLegend().setTextColor(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorOnSurface, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_onSurface)));
        binding.pieChart.setHoleColor(Color.TRANSPARENT);
        binding.pieChart.invalidate();
    }

    private void setPieChartData(Integer dueEmi, Integer paidEmi) {
        ArrayList<PieEntry> dataValue = new ArrayList<>();
        dataValue.add(new PieEntry(dueEmi, getString(R.string.text_due_emi)));
        dataValue.add(new PieEntry(paidEmi, getString(R.string.text_paid_emi)));

        PieDataSet pieDataSet = new PieDataSet(dataValue, "");

        int[] colorArr = new int[]{MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorError, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_error)),
                MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorTertiary, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_tertiary))};
        List<Integer> colorTextArr = new ArrayList<>();
        colorTextArr.add(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorOnError, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_onError)));
        colorTextArr.add(MaterialColors.getColor(getContext(), com.google.android.material.R.attr.colorOnTertiary, getContext().getResources().getColor(com.dkglabs.base.R.color.md_theme_light_onTertiary)));

        pieDataSet.setColors(colorArr);
        pieDataSet.setValueTextColors(colorTextArr);
        pieDataSet.setValueTextSize(14);

        PieData pieData = new PieData(pieDataSet);
        binding.pieChart.setData(pieData);
        binding.pieChart.invalidate();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.viewAllEmi) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("loanDataResponse", loanDataResponse);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dashboardFragment_to_emiDetailsFragment, bundle);
        } else if (id == R.id.payEmi) {
            LoanDataResponse dueEmiLoanData = loanDataResponse;
            if (!dueEmiList.isEmpty()) {
                dueEmiLoanData.setLoanEmiDetailsList(dueEmiList);
                DashboardFragmentDirections.ActionDashboardFragmentToPayEmiFragment direction = DashboardFragmentDirections.actionDashboardFragmentToPayEmiFragment(dueEmiLoanData);
                Navigation.findNavController(binding.getRoot()).navigate(direction);
            } else {
                Toast.makeText(context, "No Active EMI", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if (requestCode == 1003) {
            dueEmiList2 = (List<LoanEmiDetailResponse>) response.getResponseData();
        }
        if (requestCode == 1001) {
            loanDataResponse = (LoanDataResponse) response.getResponseData();
            if (loanDataResponse != null) {
                if (binding != null)
                    updateUi();
            } else {
                UIUtils.showView(binding.llProgressHolder);
                binding.llProgress.setMessage("Loading...");
                LoanManager.loanDetails(1002, PersistentManager.getUserResponse().getUserId(), PersistentManager.getUserResponse().getLoanId(), this);
            }
        }
        if (requestCode == 1002) {
            LoanResponse loanResponse = (LoanResponse) response.getResponseData();
            if (loanResponse != null) {
                LoanPersistentManager.setLoanId(loanResponse.getLoanId());
                LoanPersistentManager.setLoanResponse(loanResponse);
                if (loanResponse.getApplicationCompletionStatus().equals("Y"))
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dashboardFragment_to_nav_accept_loan);
                else {
                    if (PersistentManager.isDealerReq())
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dashboardFragment_to_nav_accept_loan);
                    else
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dashboardFragment_to_blankDashboardFragment);
                }
            } else {
                if (PersistentManager.isDealerReq())
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dashboardFragment_to_nav_accept_loan);
                else
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_dashboardFragment_to_blankDashboardFragment);
            }
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        if (requestCode == 1001) {
            if (binding != null) {
                UIUtils.showView(binding.llProgressHolder);
                binding.llProgress.setMessage("Loading...");
                LoanManager.loanDetails(1002, PersistentManager.getUserResponse().getUserId(), PersistentManager.getUserResponse().getLoanId(), this);
            }
        }
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        if (binding != null)
            UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
        Log.d("EMI ERROR :", t.getMessage());
    }

    @Override
    public void commonCall(int requestCode) {

    }

    @SuppressLint("SetTextI18n")
    private void updateUi() {
        binding.loanNumber.setText(loanDataResponse.getLoanId());
        binding.loanAccountNumber.setText(loanDataResponse.getNbfcLoanId());
        binding.loanType.setText(loanDataResponse.getLoanType());
        binding.loanTenure.setText(loanDataResponse.getLoanTenure() + " Months");
        binding.loanInterest.setText((String.format(getString(R.string.placeholder_rupee), loanDataResponse.getLoanInterest())));
        binding.interestRate.setText(loanDataResponse.getRateOfInterest() + "%");
        Integer totalPayable = loanDataResponse.getLoanAmount() + loanDataResponse.getLoanInterest();
        binding.loanProgress.totalLoanAmount.setText(String.format(getString(R.string.placeholder_rupee), totalPayable));
        binding.loanAmount.setText(String.format(getString(R.string.placeholder_rupee), loanDataResponse.getLoanAmount()));
        binding.totalEmi.setText("" + loanDataResponse.getLoanEmiDetailsList().size());

        int pendngEmi = 0;
        int paidEmi = 0;
        dueEmiList = new ArrayList<>();
//        for (LoanEmiDetailResponse emiDetailResponse : loanDataResponse.getLoanEmiDetailsList()) {
//            if (emiDetailResponse.getLoanEmiStatus().equals(EmiConst.EMI_PAID)) {
//                paidAmount += emiDetailResponse.getLoanEmiAmount();
//                ++paidEmi;
//            } else if (emiDetailResponse.getLoanEmiStatus().equals(EmiConst.EMI_PENDING)) {
//                dueAmount += emiDetailResponse.getLoanEmiAmount();
//                ++pendngEmi;
//                if (AppUtils.isEmiDueDatePass(AppConstants.SERVER_FORMAT, emiDetailResponse.getLoanEmiPaymentDate()))
//                    dueEmiList.add(emiDetailResponse);
//            }
//        }
        dueAmount = 0;
        paidAmount = 0;
        if (dueEmiList2 != null) {
            for (LoanEmiDetailResponse emiDetailResponse : dueEmiList2) {
                if (emiDetailResponse.getLoanEmiStatus().equals(EmiConst.EMI_PAID)) {
                    paidAmount += emiDetailResponse.getLoanEmiAmount();
                    ++paidEmi;
                } else if (emiDetailResponse.getLoanEmiStatus().equals(EmiConst.EMI_PENDING)) {
                    dueAmount += emiDetailResponse.getLoanEmiAmount();
                    ++pendngEmi;
//                if (AppUtils.isEmiDueDatePass(AppConstants.SERVER_FORMAT, emiDetailResponse.getLoanEmiPaymentDate()))
                    dueEmiList.add(emiDetailResponse);
                }
            }
            if (binding != null)
                UIUtils.hideViewGone(binding.llProgressHolder);
            if (loanDataResponse != null && loanDataResponse.getLoanEmiPaymentMode().equals(EmiConst.PAYMENT_MODE))
                binding.payEmi.setVisibility(View.GONE);
            UIUtils.showView(binding.llDashboard);
        } else {
            getLoanDetails();
        }

        binding.loanProgress.totalDueAmount.setText(String.format(getString(R.string.placeholder_rupee), dueAmount));
        binding.loanProgress.totalPaidAmount.setText(String.format(getString(R.string.placeholder_rupee), paidAmount));
        binding.emiPaid.setText("" + paidEmi);
        binding.pendngEmi.setText("" + pendngEmi);

        setPieChartData(pendngEmi, paidEmi);

        dueEmiFragment.setLoanEmiList(dueEmiList);
        setLoanProgress(totalPayable, paidAmount);
    }

    private void setLoanProgress(Integer totalPayable, int paidAmount) {
        int progress = paidAmount * 100 / totalPayable;
        binding.loanProgress.loanProgressBar.setProgress(progress);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
        binding.loanProgress.totalPaidAmount.setText(String.format(getString(R.string.placeholder_rupee), paidAmount));
        binding.loanProgress.totalPaidAmount.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
