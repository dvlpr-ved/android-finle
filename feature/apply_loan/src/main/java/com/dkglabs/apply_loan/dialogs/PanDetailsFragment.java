package com.dkglabs.apply_loan.dialogs;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.Bypassing.BypassingDataHolder;
import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentPanDetailsBinding;
import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.model.response.PanResponse;


public class PanDetailsFragment extends BaseDialogFragment implements View.OnClickListener {
    private FragmentPanDetailsBinding binding = null;

    public PanDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PanResponse panResponse = PanDetailsFragmentArgs.fromBundle(getArguments()).getPanData();

        BypassingDataHolder.setName(panResponse.getName());


        binding.pan.setText(panResponse.getPanCard());
        binding.name.setText(panResponse.getName());
        binding.dob.setText(panResponse.getDob());
        binding.fatherName.setText(panResponse.getFather());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPanDetailsBinding.inflate(getLayoutInflater(), container, false);
        getDialog().setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.buttonContinue) {
            dismiss();
        }

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        //Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_panDetailsFragment_to_basicKycAadhaarFragment);
        Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_panDetailsFragment_to_basicKycAadhaarOtpFragment);
    }
}