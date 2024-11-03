package com.dkglabs.apply_loan.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentNbfcDetailsBinding;
import com.dkglabs.apply_loan.model.NbfcAndDealerData;
import com.dkglabs.apply_loan.urls.AllImportantUrlNbfcSpecific;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.remote.utils.AppUrlConstants;

public class NbfcDetailsFragment extends BaseFragment implements View.OnClickListener {
    FragmentNbfcDetailsBinding binding;
    NbfcAndDealerData nbfcAndDealerData;
    private String TERMS_AND_CONDITION=AppUrlConstants.TERMS_CONDITION;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Bundle bundle = new Bundle();
                bundle.putString("title", "title");
                bundle.putString("message", "message");
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_global_loanBackPressFragment, bundle);
            }
        };
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        nbfcAndDealerData=NbfcDetailsFragmentArgs.fromBundle(getArguments()).getNbfcAndDealer();
        binding=FragmentNbfcDetailsBinding.inflate(inflater, container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView(nbfcAndDealerData);
        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void initializeView(NbfcAndDealerData nbfcData) {
        SetSpecificContent(nbfcData);

        binding.buttonNext.setOnClickListener(this);
        binding.txtPrivacyPolicy.setOnClickListener(this);
        binding.txtPrivacyPolicy.setText(nbfcData.getNbfcName().toLowerCase() + " " + getString(R.string.terms_and_conditions));
        binding.txtNbfcName.setText(nbfcData.getNbfcName());
    }

    private void SetSpecificContent(NbfcAndDealerData nbfcData) {
        if(nbfcData.nbfcId.equals("50000002")){
            binding.imgNbfcLogo.setImageResource(R.drawable.ic_fexprime_logo);

            binding.lottieNbfcDetails.setVisibility(View.INVISIBLE);
            binding.imgNbfcLogo.setVisibility(View.VISIBLE);

            TERMS_AND_CONDITION= AllImportantUrlNbfcSpecific.getFexPrimeTerms();
        }
    }

    @Override
    public void onClick(View v) {
        if (v == binding.buttonNext) {
            if (binding.chkPrivacyPolicy.isChecked()) {
                Navigate();
            } else {
                Toast.makeText(getContext(), "Please agree to the terms and conditions.", Toast.LENGTH_SHORT).show();
            }
        }
        if (v == binding.txtPrivacyPolicy) {
            try {
                Uri uri = Uri.parse(TERMS_AND_CONDITION);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Navigate() {
        com.dkglabs.apply_loan.fragments.NbfcDetailsFragmentDirections.ActionNbfcDetailsFragmentToBasicKycPanFragment direction =
                NbfcDetailsFragmentDirections.actionNbfcDetailsFragmentToBasicKycPanFragment(nbfcAndDealerData);
        Navigation.findNavController(binding.getRoot()).navigate(direction);
    }
}
