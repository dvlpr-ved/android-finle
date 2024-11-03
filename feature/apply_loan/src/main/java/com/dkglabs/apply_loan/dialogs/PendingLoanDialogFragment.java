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
import com.dkglabs.apply_loan.databinding.FragmentLoanBackPressBinding;
import com.dkglabs.apply_loan.databinding.FragmentPendingLoanDialogBinding;
import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.model.viewmodel.ApplyLoanModel;


public class PendingLoanDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private String title;
    private String message;
    private FragmentPendingLoanDialogBinding binding = null;

    public PendingLoanDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.dialogTitle.setText(String.format(getString(R.string.text_dialog_username), PersistentManager.getUserResponse().getFirstName()));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPendingLoanDialogBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonContinue.setOnClickListener(this);
        binding.buttonNew.setOnClickListener(this);
        binding.tvContactUs.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.buttonNew) {
            dismiss();
        } else if (id == R.id.buttonContinue) {
            dismiss();
            ApplyLoanModel applyLoanModel = new ApplyLoanModel();
            applyLoanModel.setContinueApply(true);
            viewModel.setSelectedItem(applyLoanModel);
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_pendingLoanDialogFragment_to_nav_apply_loan);
    }
}