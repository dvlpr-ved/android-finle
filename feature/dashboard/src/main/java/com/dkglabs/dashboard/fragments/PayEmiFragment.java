package com.dkglabs.dashboard.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.dashboard.R;
import com.dkglabs.dashboard.databinding.FragmentPayEmiBinding;
import com.dkglabs.model.response.LoanDataResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;

import java.util.ArrayList;
import java.util.List;

public class PayEmiFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {

    private int totalPayable;
    private String payEmiId;
    private List<LoanEmiDetailResponse> dueEmiList;
    private LoanDataResponse dueEmiLoanData;

    private FragmentPayEmiBinding binding = null;

    public PayEmiFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPayEmiBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar("Pay EMI");
        binding.buttonPay.setOnClickListener(this);
        dueEmiList = new ArrayList<>();
        binding.radioGroupEmi.setOnCheckedChangeListener(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dueEmiLoanData = PayEmiFragmentArgs.fromBundle(getArguments()).getLoanDataResponse();
        dueEmiList.addAll(dueEmiLoanData.getLoanEmiDetailsList());
        updateUi(dueEmiList);
    }

    private void updateUi(List<LoanEmiDetailResponse> dueEmiList) {
        binding.emi.setText("" + dueEmiLoanData.getLoanEmiDetailsList().get(0).getLoanEmiAmount());
        binding.dueEmi.setText("" + dueEmiList.size());

        int subTotal = 0;
        int lateCharge = 0;
        StringBuilder sb = new StringBuilder();
        for (LoanEmiDetailResponse loanEmiDetailResponse : dueEmiList) {
            subTotal += loanEmiDetailResponse.getLoanEmiAmount();
            sb.append(loanEmiDetailResponse.getLoanEmiId());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        totalPayable = subTotal + lateCharge;
        payEmiId = sb.toString();

        binding.subTotal.setText(String.format(getString(R.string.placeholder_rupee), subTotal));
        if (lateCharge > 0)
            binding.lateCharge.setText(String.format(getString(R.string.placeholder_rupee), lateCharge));
        else {
            UIUtils.hideViewGone(binding.lateChargeRow);
        }
        binding.dueAmount.setText(String.format(getString(R.string.placeholder_rupee), totalPayable));
        binding.totalPay.setText(String.format(getString(R.string.placeholder_pay_due), totalPayable));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonPay) {
            Bundle bundle = new Bundle();
            bundle.putInt("total_payable", totalPayable);
            bundle.putString("emi_id", payEmiId);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_payEmiFragment_to_paymentMethodFragment, bundle);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId == R.id.allEmi) {
            updateUi(dueEmiList);
        } else if (checkedId == R.id.oneEmi) {
            List<LoanEmiDetailResponse> emiDetailList = new ArrayList<>();
            emiDetailList.add(dueEmiList.get(0));
            updateUi(emiDetailList);
        }
    }
}