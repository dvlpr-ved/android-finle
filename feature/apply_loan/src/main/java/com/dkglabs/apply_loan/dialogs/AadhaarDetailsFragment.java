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
import com.dkglabs.apply_loan.databinding.FragmentAadhaarDetailsBinding;
import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.model.response.AadhaarAddress;
import com.dkglabs.model.response.AadhaarOtpResponse;


public class AadhaarDetailsFragment extends BaseDialogFragment implements View.OnClickListener {
    private AadhaarOtpResponse aadhaarResponse;
    private FragmentAadhaarDetailsBinding binding = null;

    public AadhaarDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //AadhaarResponse aadhaarResponse = AadhaarDetailsFragmentArgs.fromBundle(getArguments()).getAadhaarData();
        String BypassingName= BypassingDataHolder.getName();

        aadhaarResponse = AadhaarDetailsFragmentArgs.fromBundle(getArguments()).getAadhaarData();
        AadhaarAddress aadhaarAddress = aadhaarResponse.getData().getAddress();
        binding.address.setText(aadhaarAddress.toString());
//        binding.name.setText(aadhaarResponse.getData().getFullName());
        binding.name.setText(BypassingName);
//        binding.pin.setText(aadhaarResponse.getData().getZip());
        binding.pin.setText("XXXXXX");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAadhaarDetailsBinding.inflate(inflater, container, false);
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
        AadhaarDetailsFragmentDirections.ActionAadhaarDetailsFragmentToLoanUserAddressFragment direction = AadhaarDetailsFragmentDirections.actionAadhaarDetailsFragmentToLoanUserAddressFragment(aadhaarResponse);
        Navigation.findNavController(getParentFragment().getView()).navigate(direction);
    }
}