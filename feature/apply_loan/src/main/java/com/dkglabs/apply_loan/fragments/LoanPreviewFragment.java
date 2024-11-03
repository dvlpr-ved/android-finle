package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.Bypassing.BypassingDataHolder;
import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentLoanPreviewBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;

public class LoanPreviewFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private FragmentLoanPreviewBinding binding = null;

    public LoanPreviewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(6);
        viewModel.setSelectedItem(applyLoanModel);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putString("title", "title");
                bundle.putString("message", "message");
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_loanBackPressFragment, bundle);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoanPreviewBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonApply.setOnClickListener(this);
        progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.loading_loan_details));
        LoanManager.loanDetails(1000, PersistentManager.getUserResponse().getUserId(), LoanPersistentManager.getLoanId(), this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonApply) {
            progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.finishing_loan_application));
            LoanManager.applyLoan(1001, PersistentManager.getUserResponse().getUserId(), LoanPersistentManager.getLoanId(), this);
        }
    }

    private void updateUi(LoanResponse loanResponse) {
        binding.applicantName.setText(loanResponse.getPersonalDetails().getName());
        binding.panNo.setText(loanResponse.getDocumentDetails().getPanNo());
        binding.loanAmount.setText(loanResponse.getLoanDetail().getLoanRequestAmt());
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        switch (requestCode) {
            case 1000:
                LoanResponse loanResponse = (LoanResponse) response.getResponseData();
                LoanPersistentManager.setLoanResponse(loanResponse);
                updateUi(loanResponse);
                break;
            case 1001:
                String loanDetailsResponse = (String) response.getResponseData();
                if (loanDetailsResponse.equals("Loan Details saved.")) {
                    UIUtils.showToast(context, "Loan applied");
                    closeActivity();
                }
                break;
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.dismissDialog(progressDialog);
    }
}