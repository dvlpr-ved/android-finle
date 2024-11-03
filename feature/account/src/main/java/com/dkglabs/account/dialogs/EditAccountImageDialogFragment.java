package com.dkglabs.account.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dkglabs.account.databinding.FragmentEditAccountImageDialogBinding;
import com.dkglabs.account.R;
import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.model.viewmodel.AppViewModel;

/**
 * Created by Himanshu Srivastava on 06/03/2023.
 */
public class EditAccountImageDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private FragmentEditAccountImageDialogBinding binding = null;

    public EditAccountImageDialogFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentEditAccountImageDialogBinding.inflate(getLayoutInflater(), container, false);

        initializeView();

        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonCancel.setOnClickListener(this);
        binding.buttonGallery.setOnClickListener(this);
        binding.buttonCamera.setOnClickListener(this);
        binding.buttonRemove.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonCancel) {
            dismiss();
        } else if (id == R.id.buttonCamera) {
            dismiss();
            AppViewModel appViewModel = new AppViewModel();
            appViewModel.setChangeProfile("camera");
            viewModel.setSelectedItem(appViewModel);
        } else if (id == R.id.buttonGallery) {
            dismiss();
            AppViewModel appViewModel = new AppViewModel();
            appViewModel.setChangeProfile("gallery");
            viewModel.setSelectedItem(appViewModel);
        } else if (id == R.id.buttonRemove) {
            dismiss();
            AppViewModel appViewModel = new AppViewModel();
            appViewModel.setChangeProfile("remove");
            viewModel.setSelectedItem(appViewModel);
        }
    }
}
