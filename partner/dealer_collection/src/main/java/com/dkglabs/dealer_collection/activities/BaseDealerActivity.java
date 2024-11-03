package com.dkglabs.dealer_collection.activities;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.dkglabs.base.activities.BaseActivity;
import com.dkglabs.base.manager.ApplicationCache;
import com.dkglabs.base.manager.CollectionPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.data.entity.LoanApplicant;
import com.dkglabs.data.entity.LoanApplicantEMI;
import com.dkglabs.dealer_collection.view_model.CollectionViewModel;
import com.dkglabs.model.CollectionCard;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.CollectionDetailsResponse;
import com.dkglabs.model.response.LoanApplicantResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.CollectionManager;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class BaseDealerActivity extends BaseActivity implements ResponseListener {

    protected LinearProgressIndicator progressIndicator;
    protected View view;
    private CollectionViewModel mCollectionViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCollectionViewModel = new CollectionViewModel(ApplicationCache.getInstance().getAppContext());
    }

    public void setProgressIndicator(LinearProgressIndicator progressIndicator) {
        this.progressIndicator = progressIndicator;
    }

    public void setView(View view) {
        this.view = view;
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateCollectionData();
    }

    private void updateCollectionData() {
        progressIndicator.setVisibility(View.VISIBLE);
        CollectionManager.getCollectionDetails(1001, PersistentManager.getUserResponse().getUserId(),  this);
//        CollectionManager.getCollectionDetailsAll(1001,  this);

    }


    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        CollectionDetailsResponse collectionDetailsResponse = (CollectionDetailsResponse) response.getResponseData();
        List<LoanApplicantResponse> loanResponseList = collectionDetailsResponse.getCollectionDetailsDtoList();
        if (loanResponseList != null) {
            setApplicantData(loanResponseList);
        }
        saveCollectionCardDetails(collectionDetailsResponse);
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showSnackbar(view, message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showGenericErrorDialog(view, getContext());
    }

    @Override
    public void commonCall(int requestCode) {
        progressIndicator.setVisibility(View.GONE);
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
                    if(emiResponse.getLoanMonth()==null){
                        applicantEMI.setLoanMonth(0l);
                    }
                    else{
                        applicantEMI.setLoanMonth(emiResponse.getLoanMonth());
                    }
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

        mCollectionViewModel.insertAllLoanApplicant(loanApplicantList);
        mCollectionViewModel.insertAllLoanApplicantEmi(loanApplicantEmiList);
    }

    private void saveCollectionCardDetails(CollectionDetailsResponse collectionDetailsResponse) {
        CollectionCard collectionCard = new CollectionCard();

        collectionCard.setTotalLoanDue(collectionDetailsResponse.getTotalLoanDue());
        collectionCard.setTotalLoanEmiDueAmount(collectionDetailsResponse.getTotalLoanEmiDueAmount());
        collectionCard.setTotalLateFeeAmount(collectionDetailsResponse.getTotalLateFeeAmount());
        collectionCard.setTotalLastEmiTxnAmount(collectionDetailsResponse.getTotalLastEmiTxnAmount());
        collectionCard.setTotalLoanAmount(collectionDetailsResponse.getTotalLoanAmount());
        collectionCard.setTotalPendingEmiAmount(collectionDetailsResponse.getTotalPendingEmiAmount());

        mCollectionViewModel.setCollectionCard(collectionCard);

        CollectionPersistentManager.setCollectionCard(collectionCard);
    }

}
