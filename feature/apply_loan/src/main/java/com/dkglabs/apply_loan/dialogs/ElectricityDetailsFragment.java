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
import com.dkglabs.apply_loan.databinding.FragmentElectricityDetailsBinding;
import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.model.response.ElectricityBillResponse;


public class ElectricityDetailsFragment extends BaseDialogFragment implements View.OnClickListener {
    private FragmentElectricityDetailsBinding binding = null;
    private ElectricityBillResponse billResponse;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


//        binding.name.setText(billResponse.getResult().getName());
//        binding.consumerNo.setText(billResponse.getEssentials().getConsumerNo());
//        binding.address.setText(billResponse.getResult().getAddress());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billResponse = ElectricityDetailsFragmentArgs.fromBundle(getArguments()).getElectricityData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentElectricityDetailsBinding.inflate(getLayoutInflater(), container, false);
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
        Navigation.findNavController(getParentFragment().getView()).navigate(ElectricityDetailsFragmentDirections.actionElectricityDetailsFragmentToLoanUserDocumentFragment());
    }
}