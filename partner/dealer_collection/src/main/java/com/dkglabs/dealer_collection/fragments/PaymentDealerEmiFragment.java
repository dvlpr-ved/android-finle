package com.dkglabs.dealer_collection.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.dealer_collection.R;
import com.dkglabs.dealer_collection.databinding.FragmentPaymentDealerEmiBinding;
import com.dkglabs.dealer_collection.model.EmiPayModel;
import com.dkglabs.model.response.LoanApplicantResponse;

import java.util.ArrayList;


public class PaymentDealerEmiFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{
    FragmentPaymentDealerEmiBinding binding;
    protected LoanApplicantResponse loanApplicantResponse;
    protected String payEmiId;

    protected double totalPayable;
    public PaymentDealerEmiFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentPaymentDealerEmiBinding.inflate(inflater,container,false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar("Pay EMI");
        binding.fragmentDealerPay.buttonPay.setOnClickListener(this);
        binding.fragmentDealerPay.radioGroupEmi.setOnCheckedChangeListener(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loanApplicantResponse= EmiPayModel.getLoanApplicantResponse();
        updateUi(loanApplicantResponse);
    }

    private void updateUi(LoanApplicantResponse loanApplicantResponse) {
        payEmiId=loanApplicantResponse.getLoanId();

        double emiPaid=loanApplicantResponse.getLoanAmount() - (double)loanApplicantResponse.getDueEmiAmount();
        double monthly_emi=0.0;

        if(loanApplicantResponse.getEmiDue()!=0)
            monthly_emi=loanApplicantResponse.getLoanEmiAmount()/loanApplicantResponse.getEmiDue();

        binding.fragmentDealerPay.emi.setText(loanApplicantResponse.getLoanAmount().toString());
        binding.fragmentDealerPay.dueEmi.setText(loanApplicantResponse.getDueEmiAmount().toString());
        binding.fragmentDealerPay.tableRowSub.setVisibility(View.GONE);
        binding.fragmentDealerPay.lateCharge.setText(loanApplicantResponse.getLateFeeDue().toString());

        totalPayable=loanApplicantResponse.getDueEmiAmount() + loanApplicantResponse.getLateFeeDue();
        binding.fragmentDealerPay.dueAmount.setText("₹ "+totalPayable);
        binding.fragmentDealerPay.totalPay.setText("₹ "+totalPayable);
        binding.fragmentDealerPay.buttonPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==binding.fragmentDealerPay.buttonPay.getId()){
            Bundle bundle = new Bundle();
            bundle.putInt("total_payable",(int)totalPayable);
            bundle.putString("emi_id", payEmiId);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_paymentDealerEmiFragment_to_paymentMethodFragment2, bundle);
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }
}