package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentLoanDocumentBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.RequireDocDetailResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;

public class LoanDocumentFragment extends BaseFragment implements View.OnClickListener, ResponseListener {

    private MandatoryDocFragment mandatoryDocFragment;
    private OptionalDocFragment optionalDocFragment;
    private FragmentLoanDocumentBinding binding = null;

    public LoanDocumentFragment() {
        // Required empty public constructor
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
        binding = FragmentLoanDocumentBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(5);
        viewModel.setSelectedItem(applyLoanModel);

        mandatoryDocFragment = MandatoryDocFragment.newInstance(null);
        FragmentTransaction transactionMandatory = getChildFragmentManager().beginTransaction();
        transactionMandatory.replace(R.id.mandatory_doc_fragment_container, mandatoryDocFragment).commit();

        FragmentTransaction transactionOptional = getChildFragmentManager().beginTransaction();
        optionalDocFragment = OptionalDocFragment.newInstance(null);
        transactionOptional.replace(R.id.optional_doc_fragment_container, optionalDocFragment).commit();

        progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.checking_for_document));
        LoanManager.loanDocuments(1001, LoanPersistentManager.getLoanId(),  this);
    }

    private void initializeView() {
        binding.buttonFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonFinish) {
            Navigation.findNavController(binding.getRoot()).navigate(LoanDocumentFragmentDirections.actionLoanUserDocumentFragmentToPreviewLoanDetailsFragment());
        }
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        switch (requestCode) {
            case 1001:
                RequireDocDetailResponse docDetailResponse = (RequireDocDetailResponse) response.getResponseData();
                mandatoryDocFragment.setDocDetailsList(docDetailResponse.getRequiredNbfcDocDetailList());
                optionalDocFragment.setDocDetailsList(docDetailResponse.getOptionalNbfcDocDetailList());
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
        UIUtils.dismissDialog(progressDialog);
    }
}