package com.dkglabs.account.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dkglabs.account.databinding.FragmentLogoutDialogBinding;
import com.dkglabs.account.R;
import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.model.viewmodel.AppViewModel;


public class LogoutDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private FragmentLogoutDialogBinding binding = null;

    public LogoutDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLogoutDialogBinding.inflate(getLayoutInflater(), container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonLogout.setOnClickListener(this);
        binding.buttonDismiss.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.buttonLogout) {
            dismiss();
            AppViewModel appViewModel = new AppViewModel();
            appViewModel.setLogoutPressed(true);
            viewModel.setSelectedItem(appViewModel);
        } else if (id == R.id.buttonDismiss) {
            dismiss();
        }

    }
}