package com.dkglabs.apply_loan.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentBankDetailsBinding;
import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.applyloan.BankDetails;


public class BankDetailsFragment extends BaseDialogFragment implements View.OnClickListener {
    private FragmentBankDetailsBinding binding = null;
    private Boolean buttonContinue = false;

    public BankDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        BankDetails bankDetails = BankDetailsFragmentArgs.fromBundle(getArguments()).getBankData();

        binding.accountNumber.setText(bankDetails.getAccountNumber());
        binding.ifscCode.setText(bankDetails.getIfscCode());
        binding.accountHolderName.setText(bankDetails.getAccHolderName());

        if (!bankDetails.getAccountVerificationFlag().equals("Y")) {
            UIUtils.showView(binding.buttonChange);
            UIUtils.showView(binding.bankNotVerified);
            UIUtils.hideViewGone(binding.bankVerified);
            UIUtils.hideViewGone(binding.buttonContinue);
        } else {
            UIUtils.hideViewGone(binding.buttonChange);
            UIUtils.showView(binding.bankVerified);
            UIUtils.showView(binding.buttonContinue);
            UIUtils.hideViewGone(binding.bankNotVerified);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBankDetailsBinding.inflate(getLayoutInflater(), container, false);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonContinue.setOnClickListener(this);
        binding.buttonChange.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.buttonContinue) {
            buttonContinue = true;
            dismiss();
        } else if (id == R.id.buttonChange) {
            dismiss();
        }

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (buttonContinue)
            Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_bankDetailsFragment_to_electricityBillFragment);
    }
}