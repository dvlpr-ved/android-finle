package com.dkglabs.dealer_collection.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dkglabs.dealer_collection.R;
import com.dkglabs.dealer_collection.databinding.FragmentDealerEmiDetaisBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.dealer_collection.model.EmiPayModel;
import com.dkglabs.model.response.LoanApplicantResponse;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class DealerEmiDetais extends BaseFragment implements View.OnClickListener  {
    FragmentDealerEmiDetaisBinding binding;
    LoanApplicantResponse loanApplicantResponse;
    public DealerEmiDetais() {
        // Required empty public constructor

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentDealerEmiDetaisBinding.inflate(inflater, container, false);
        loanApplicantResponse=EmiPayModel.getLoanApplicantResponse();
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
    }

    @Override
    public void onStart() {
        super.onStart();
        updateUi();
    }

    private void updateUi() {
        double emiPaid=loanApplicantResponse.getLoanAmount() - (double)loanApplicantResponse.getDueEmiAmount();
        double monthly_emi=0.0;

        if(loanApplicantResponse.getEmiDue()!=0)
            monthly_emi=loanApplicantResponse.getLoanEmiAmount()/loanApplicantResponse.getEmiDue();

        binding.dealerEmiData.totalEmi.setText(loanApplicantResponse.getLoanAmount().toString());
        binding.dealerEmiData.emiPaid.setText(emiPaid + "");
        binding.dealerEmiData.pendngEmi.setText(loanApplicantResponse.getDueEmiAmount().toString());
        binding.dealerEmiData.lastPaidEmi.setText("Last Paid Amount : " + loanApplicantResponse.getLastPaidAmount());
        monthly_emi=Math.ceil(monthly_emi);
        binding.dealerEmiData.monthlyEmiAmount.setText("₹ "+ monthly_emi);

        binding.dealerEmiData.payEmi.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==binding.dealerEmiData.payEmi.getId()){
            Navigation.findNavController(v).navigate(R.id.action_dealerEmiDetais2_to_paymentDealerEmiFragment);
        }
    }
}