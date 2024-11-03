package com.dkglabs.loan_application.activities;

import android.os.Bundle;
import android.view.View;

import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.ApplicationCache;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.data.entity.LoanApplicant;
import com.dkglabs.data.entity.LoanApplicantEMI;
import com.dkglabs.loan_application.databinding.ActivityLoanApplicationBinding;
import com.dkglabs.loan_application.view_model.ApplicantViewModel;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.CollectionDetailsResponse;
import com.dkglabs.model.response.LoanApplicantResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.CollectionManager;

import java.util.ArrayList;
import java.util.List;

public class LoanApplicationActivity extends BaseActivity implements ResponseListener {

    private ApplicantViewModel applicantViewModel;
    private ActivityLoanApplicationBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoanApplicationBinding.inflate(getLayoutInflater());
        initializeView();
        setContentView(binding.getRoot());
    }

    private void initializeView() {
        applicantViewModel = new ApplicantViewModel(ApplicationCache.getInstance().getAppContext());
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateLoanData();
    }

    private void updateLoanData() {
        binding.toolbarLayout.progressBar.setVisibility(View.VISIBLE);
//        CollectionManager.getCollectionDetails(1001, PersistentManager.getUserResponse().getUserId(),  this);
        CollectionManager.getCollectionDetailsAll(1001,  this);

    }


    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        CollectionDetailsResponse collectionDetailsResponse = (CollectionDetailsResponse) response.getResponseData();
        List<LoanApplicantResponse> loanResponseList = collectionDetailsResponse.getCollectionDetailsDtoList();
        if (loanResponseList != null) {
            setApplicantData(loanResponseList);
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
        binding.toolbarLayout.progressBar.setVisibility(View.GONE);
    }

    private void setApplicantData(List<LoanApplicantResponse> loanResponseList) {
        List<LoanApplicant> loanApplicantList = new ArrayList<>();
        List<LoanApplicantEMI> loanApplicantEmiList = new ArrayList<>();

        for (LoanApplicantResponse applicantResponse : loanResponseList) {
            LoanApplicant loanApplicant = new LoanApplicant();
            loanApplicant.setLoanId(applicantResponse.getLoanId());
            loanApplicant.setUserId(applicantResponse.getUserId());
            loanApplicant.setName(applicantResponse.getName());
            loanApplicant.setPhone(applicantResponse.getPhone());
            loanApplicant.setPartnerCode(applicantResponse.getPartnerCode());
            loanApplicant.setLoanAmount(applicantResponse.getLoanAmount());
            loanApplicant.setTerm(applicantResponse.getTerm());
            loanApplicant.setLoanEmiAmount(applicantResponse.getLoanEmiAmount());
            loanApplicant.setLoanEmiPaymentDate(applicantResponse.getLoanEmiPaymentDate());
            loanApplicant.setEmiDue(applicantResponse.getEmiDue());
            loanApplicant.setLateFeeDue(applicantResponse.getLateFeeDue());
            loanApplicant.setExtraPaid(applicantResponse.getExtraPaid());
            loanApplicant.setStatus(applicantResponse.getStatus());
            loanApplicant.setSubStatus(applicantResponse.getSubStatus());
            loanApplicant.setLastPaidAmount(applicantResponse.getLastPaidAmount());
            loanApplicant.setLastPaidDate(applicantResponse.getLastPaidDate());
            loanApplicant.setDueEmiAmount(applicantResponse.getDueEmiAmount());
            loanApplicant.setCurrentEmiPaymentDate(applicantResponse.getCurrentEmiPaymentDate());

            List<LoanEmiDetailResponse> loanEmiDetailResponseList = new ArrayList<>();
            loanEmiDetailResponseList.addAll(applicantResponse.getEmiDetails());

            if (loanEmiDetailResponseList != null && loanEmiDetailResponseList.size() > 0) {
                for (LoanEmiDetailResponse emiResponse : loanEmiDetailResponseList) {
                    LoanApplicantEMI applicantEMI = new LoanApplicantEMI();

                    applicantEMI.setLoanEmiId(emiResponse.getLoanEmiId());
                    applicantEMI.setLoanId(emiResponse.getLoanId());
                    if(emiResponse.getLoanMonth()==null)
                        applicantEMI.setLoanMonth(0l);
                    else
                        applicantEMI.setLoanMonth(emiResponse.getLoanMonth());
                    applicantEMI.setLoanEmiAmount(emiResponse.getLoanEmiAmount());
                    applicantEMI.setLoanCurrentBalance(emiResponse.getLoanCurrentBalance());
                    applicantEMI.setLoanEmiInterest(emiResponse.getLoanEmiInterest());
                    applicantEMI.setLoanEmiPrincipal(emiResponse.getLoanEmiPrincipal());
                    applicantEMI.setLoanEmiPaymentDate(emiResponse.getLoanEmiPaymentDate());
                    applicantEMI.setLoanEmiStatus(emiResponse.getLoanEmiStatus());
                    applicantEMI.setRemarks(emiResponse.getRemarks());
                    applicantEMI.setCreatedBy(emiResponse.getCreatedBy());
                    applicantEMI.setCreatedDate(emiResponse.getCreatedDate());
                    applicantEMI.setUpdatedDate(emiResponse.getUpdatedDate());

                    loanApplicantEmiList.add(applicantEMI);
                }
            }

            loanApplicantList.add(loanApplicant);
        }
        applicantViewModel.insertAllLoanApplicant(loanApplicantList);
        applicantViewModel.insertAllLoanApplicantEmi(loanApplicantEmiList);
    }
}