package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentLoanStatusBinding;
import com.dkglabs.apply_loan.utils.LoanStatusConst;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.ui.stateprogressbar.StateProgressBar;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanProcessingDocument;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;
import com.google.android.material.color.MaterialColors;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class LoanStatusFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private static final int GET_LOAN_LOAN_PROCESSING_DOCUMENTS = 101;
    private LoanResponse loanResponse = null;

    private FragmentLoanStatusBinding binding = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanResponse = LoanPersistentManager.getLoanResponse();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoanStatusBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.loanStateTitle.setText(String.format(getString(R.string.hi_loan_status), PersistentManager.getUserResponse().getFirstName()));

        ArrayList<String> loanState = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_loan_state)));
        binding.loanStateProgressBar.setStateDescriptionData(loanState);

        binding.buttonAcceptOffer.setOnClickListener(this);
        binding.checkboxTermsConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.buttonAcceptOffer.setEnabled(true);
                } else {
                    binding.buttonAcceptOffer.setEnabled(false);
                }
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switch (loanResponse.getLoanStatus()) {
            case LoanStatusConst.INCOMPLETE:
                binding.loanStatusMessage.setText("Your loan application is incomplete. Please contact our support team.");
                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                break;
            case LoanStatusConst.AWAITING_APPROVAL:
                binding.loanStatusMessage.setText("We are currently reviewing your documents. We'll get back to you soon with the best possible offer.");
                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                break;
            case LoanStatusConst.APPROVED:
            case LoanStatusConst.PENDING:
                binding.loanStateProgressBar.setCurrentStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setBackgroundColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreenContainer));
                binding.loanStateProgressBar.setForegroundColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                binding.loanStatusMessage.setText("Congratulations! Your loan has been approved.");
                binding.setLoanResponse(loanResponse);
                binding.llLoanOffer.setVisibility(View.VISIBLE);
                binding.loanStatusMessage.setTextColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));

                binding.setEmi(String.format(getString(com.dkglabs.base.R.string.placeholder_amount), calculateEmi(Double.valueOf(loanResponse.getLoanDetail().getLoanRequestAmt()), 36, 25.0)));

                if (loanResponse.getSubscriptionFlag()) {
                    Navigation.findNavController(binding.getRoot()).navigate(LoanStatusFragmentDirections.actionLoanStatusFragmentToEnachStatusFragment());
                }
                break;
            case LoanStatusConst.REJECTED:
                ArrayList<String> loanState = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.array_loan_state_rejected)));
                binding.loanStateProgressBar.setStateDescriptionData(loanState);
                binding.loanStateProgressBar.setCurrentStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorError));
                binding.loanStateProgressBar.setBackgroundColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorErrorContainer));
                binding.loanStateProgressBar.setForegroundColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorError));
                binding.loanStateProgressBar.setStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorError));
                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                binding.loanStatusMessage.setText("We regret to inform you that your loan application has been rejected. Please try again after 3 months.");
                binding.loanStatusMessage.setTextColor(MaterialColors.getColor(binding.getRoot(), com.google.android.material.R.attr.colorError));
                break;
//            case LoanStatusConst.PENDING:
//                binding.loanStateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
//                binding.loanStatusMessage.setText("Your loan application is currently pending.");
//                break;
            case LoanStatusConst.ACTIVE_LOAN:
                binding.loanStateProgressBar.setCurrentStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setBackgroundColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreenContainer));
                binding.loanStateProgressBar.setForegroundColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setStateDescriptionColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                binding.loanStateProgressBar.setAllStatesCompleted(true);
                binding.loanStatusMessage.setText("Your loan is currently active. Please click on the 'my loan journey' button on the home screen to see your repayment history and details.");
                binding.loanStatusMessage.setTextColor(MaterialColors.getColor(binding.getRoot(), com.dkglabs.base.R.attr.colorGreen));
                break;
            case LoanStatusConst.CLOSED_LOAN:
                binding.loanStateProgressBar.setAllStatesCompleted(true);
                binding.loanStatusMessage.setText("Your loan has been closed. Please click on the 'my loan journey' button on the home screen to see your repayment history and details.");
                break;

        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.buttonAcceptOffer.getId()) {
            getLoanProcessingDocuments();
        }
    }

    private void getLoanProcessingDocuments() {
        progressDialog = UIUtils.showProgressDialog(getActivity(), progressDialog, "Processing request. Please wait...");
        LoanManager.getLoanProcessingDocuments(GET_LOAN_LOAN_PROCESSING_DOCUMENTS, loanResponse.getLoanId(), this);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        switch (requestCode) {
            case GET_LOAN_LOAN_PROCESSING_DOCUMENTS:
                LoanProcessingDocument loanProcessingDocument = (LoanProcessingDocument) response.getResponseData();
                Navigation.findNavController(binding.getRoot()).navigate(LoanStatusFragmentDirections.actionLoanStatusFragmentToLoanKfsFragment(loanProcessingDocument));
                break;
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
        switch (requestCode) {
            case GET_LOAN_LOAN_PROCESSING_DOCUMENTS:
                UIUtils.dismissDialog(progressDialog);
                break;
        }
    }


    private Double calculateEmi(Double principal, int month, Double rate) {
        if (principal <= 0.0 || rate <= 0.0 || month <= 0) {
            return 0.0;
        }
        Double r = (rate / (12 * 100));
        return ((principal * r * AppUtils.getPower(1 + r, month)) / (AppUtils.getPower(1 + r, month) - 1));
    }
}