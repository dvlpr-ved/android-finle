package com.dkglabs.auth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;

import com.dkglabs.auth.R;
import com.dkglabs.auth.databinding.FragmentPhoneBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.base.manager.PersistentManager;
import com.google.firebase.auth.FirebaseAuth;

public class PhoneFragment extends BaseFragment implements View.OnClickListener {

    private FragmentPhoneBinding binding = null;

    public PhoneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPhoneBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        PersistentManager.setNewUser(false);
        FirebaseAuth.getInstance().signOut();
        binding.buttonGetOtp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonGetOtp) {
            validatePhone();
        }
    }

    private void validatePhone() {
        if (!AppUtils.isInternetAvailable(getActivity().getApplication(), binding.getRoot(), getString(com.dkglabs.base.R.string.text_retry), view -> {
            validatePhone();
        })) {
            return;
        }

        String phone = binding.editTextPhone.getText().toString();
        if (phone.length() < 9) {
            UIUtils.hideKeyboard(getActivity());
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.valid_phone_number));
            binding.editTextPhone.requestFocus();
            return;
        }
        openOtpFragment();
    }

    private void openOtpFragment() {
        String code = binding.countryCode.getSelectedCountryCodeWithPlus();
        String phone = binding.editTextPhone.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("code", code);
        bundle.putString("phone", phone);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_phoneFragment_to_otpFragment, bundle);
    }
}