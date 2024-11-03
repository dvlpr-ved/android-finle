package com.dkglabs.dealer.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.dealer.R;
import com.dkglabs.dealer.databinding.FragmentHomeMenuDialogBinding;
import com.dkglabs.model.viewmodel.AppViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class HomeMenuDialogFragment extends BaseDialogFragment implements View.OnClickListener {

    private FragmentHomeMenuDialogBinding binding = null;

    public HomeMenuDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeMenuDialogBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.cardPrivacy.setOnClickListener(this);
        binding.cardTerms.setOnClickListener(this);
        binding.cardLogout.setOnClickListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.cardTerms) {
            dismiss();
            AppViewModel appViewModel = new AppViewModel();
            appViewModel.setTerms(true);
            viewModel.setSelectedItem(appViewModel);
        } else if (id == R.id.cardPrivacy) {
            dismiss();
            AppViewModel appViewModel = new AppViewModel();
            appViewModel.setPrivacy(true);
            viewModel.setSelectedItem(appViewModel);
        } else if (id == R.id.cardLogout) {
            dismiss();
            Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_homeMenuDialogFragment_to_logoutDialogFragment);
        }
    }
}