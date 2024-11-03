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
import com.dkglabs.apply_loan.databinding.FragmentDlDetailsBinding;
import com.dkglabs.apply_loan.databinding.FragmentPanDetailsBinding;
import com.dkglabs.base.fragments.BaseDialogFragment;
import com.dkglabs.model.response.DlResponse;
import com.dkglabs.model.response.PanResponse;


public class DlDetailsFragment extends BaseDialogFragment implements View.OnClickListener {
    private FragmentDlDetailsBinding binding = null;

    public DlDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DlResponse dlResponse = DlDetailsFragmentArgs.fromBundle(getArguments()).getDlData();

        binding.name.setText(dlResponse.getName());
        binding.dob.setText(dlResponse.getDob());
        binding.address.setText(dlResponse.getAddressString());
        binding.licenceExpDate.setText(dlResponse.getDoe());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDlDetailsBinding.inflate(getLayoutInflater(), container, false);
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
        Navigation.findNavController(getParentFragment().getView()).navigate(R.id.action_dlDetailsFragment_to_loanDetailFragment);
    }
}