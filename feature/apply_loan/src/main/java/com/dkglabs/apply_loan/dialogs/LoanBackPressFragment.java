package com.dkglabs.apply_loan.dialogs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentLoanBackPressBinding;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.base.fragments.BaseDialogFragment;


public class LoanBackPressFragment extends BaseDialogFragment implements View.OnClickListener {

    private String title;
    private String message;
    private FragmentLoanBackPressBinding binding = null;

    public LoanBackPressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*        title = getArguments().getString(" title");
        message = getArguments().getString(" message");

        LogsManager.printLog("Dialog", "Title : " + title + "\nMessage : " + message);

        binding.textTitle.setText(title);
        binding.textMessage.setText(message);*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoanBackPressBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonCancel.setOnClickListener(this);
        binding.buttonDismiss.setOnClickListener(this);
        binding.buttonNegative.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.buttonCancel) {
            dismiss();
        } else if (id == R.id.buttonDismiss) {
            dismiss();
            ApplyLoanModel applyLoanModel = new ApplyLoanModel();
            applyLoanModel.setExitApply(true);
            viewModel.setSelectedItem(applyLoanModel);
        }

    }
}