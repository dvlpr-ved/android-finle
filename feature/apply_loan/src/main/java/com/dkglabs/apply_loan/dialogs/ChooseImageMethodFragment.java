package com.dkglabs.apply_loan.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentChooseImageMethodBinding;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.base.fragments.BaseDialogFragment;

/**
 * Created by Himanshu Srivastava on 06/03/2023.
 */
public class ChooseImageMethodFragment extends BaseDialogFragment implements View.OnClickListener {
    private int action;
    private FragmentChooseImageMethodBinding binding = null;

    public ChooseImageMethodFragment() {
    }

    public static ChooseImageMethodFragment newInstance() {
        ChooseImageMethodFragment fragment = new ChooseImageMethodFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentChooseImageMethodBinding.inflate(getLayoutInflater(), container, false);

        initializeView();

        return binding.getRoot();
    }

    private void initializeView() {
        Bundle arg = getArguments();
        action = arg.getInt("action");
        binding.buttonCancel.setOnClickListener(this);
        binding.buttonGallery.setOnClickListener(this);
        binding.buttonCamera.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonCancel) {
            dismiss();
        } else if (id == R.id.buttonCamera) {
            dismiss();
            ApplyLoanModel applyLoanModel = new ApplyLoanModel();
            applyLoanModel.setDocAction(action);
            applyLoanModel.setFileMethod("camera");
            viewModel.setSelectedItem(applyLoanModel);
        } else if (id == R.id.buttonGallery) {
            dismiss();
            ApplyLoanModel applyLoanModel = new ApplyLoanModel();
            applyLoanModel.setDocAction(action);
            applyLoanModel.setFileMethod("gallery");
            viewModel.setSelectedItem(applyLoanModel);
        }
    }
}
