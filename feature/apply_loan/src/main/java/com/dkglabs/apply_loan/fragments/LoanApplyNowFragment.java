package com.dkglabs.apply_loan.fragments;

import static android.provider.SimPhonebookContract.SimRecords.PHONE_NUMBER;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentLoanApplyNowBinding;
import com.dkglabs.base.fragments.BaseFragment;

import com.dkglabs.base.utils.UIUtils;

public class LoanApplyNowFragment extends BaseFragment implements View.OnClickListener{

    private FragmentLoanApplyNowBinding binding = null;

    public LoanApplyNowFragment() {
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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoanApplyNowBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonApplyNow.setOnClickListener(this);
        binding.txtTermsAndConditions.setOnClickListener(this);
        binding.txtMissCall.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==binding.txtMissCall.getId()){
            openNumberPad();
        }
        if (view.getId() == R.id.buttonApplyNow){
            validateData();
        }
        if(view.getId()==binding.txtTermsAndConditions.getId()){
            loadTermsAndConditions();
        }
    }

    private void loadTermsAndConditions() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loanApplyNowFragment_to_termsAndConditions);
    }

    private void validateData() {
        if (!binding.termsCheckBox.isChecked()) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.text_loan_terms_error));
            return;
        }
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loanApplyNowFragment_to_dealerMappingFragment);
    }
    private void openNumberPad() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + PHONE_NUMBER));
        startActivity(intent);
    }
}