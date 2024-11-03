package com.dkglabs.auth.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.navigation.Navigation;

import com.dkglabs.auth.R;
import com.dkglabs.auth.databinding.FragmentRegisterUserBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.interfaces.DialogActionInterface;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.UserRegisterRequest;


/**
 * Created by Himanshu Srivastava on 9/14/2022.
 */
public class RegisterUserFragment extends BaseFragment implements View.OnClickListener {

    private String mobileNumber;
    private String code;

    private FragmentRegisterUserBinding binding = null;

    public RegisterUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                alertDialog = UIUtils.showAlertDialog(getActivity(), getString(R.string.cancel_create_an_account), getString(R.string.cancel_create_account_msg), getString(R.string.text_yes), getString(R.string.text_no), (DialogActionInterface) (dialog, tag) -> {
                    switch (tag) {
                        case DialogActionInterface.POSITIVE_TAG:
                            setEnabled(false);
                            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registerUserFragment_to_phoneFragment);
                        case DialogActionInterface.NEGATIVE_TAG:
                            dialog.dismiss();
                            break;
                    }
                });
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterUserBinding.inflate(inflater, container, false);
        initializeView();
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return binding.getRoot();
    }

    private void initializeView() {
        binding.buttonNext.setOnClickListener(this);
        mobileNumber = getArguments().getString("mobile_number");
    }

    public void validateUserData() {

        String firstName = binding.textInputFirstName.getEditText().getText().toString().trim();
        String lastName = binding.textInputLastName.getEditText().getText().toString().trim();
        String email = binding.textInputEmail.getEditText().getText().toString().trim();

        if (AppUtils.isStringEmpty(firstName)) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.first_name_msg));
            binding.textInputFirstName.requestFocus();
            binding.textInputFirstName.setError(getString(R.string.first_name_msg));
            return;
        }
        if (AppUtils.isStringEmpty(lastName)) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.last_name_msg));
            binding.textInputLastName.requestFocus();
            binding.textInputLastName.setError(getString(R.string.last_name_msg));
            return;
        }

        if (AppUtils.isContainsData(email) && !AppUtils.validateEmail(email)) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.enter_valid_email_msg));
            binding.textInputEmail.requestFocus();
            binding.textInputEmail.setError(getString(R.string.enter_valid_email_msg));
            return;
        }


        if (AppUtils.isInternetAvailable(getActivity().getApplication(), binding.getRoot().getRootView())) {
            UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
            userRegisterRequest.setFirstName(firstName);
            userRegisterRequest.setLastName(lastName);
            userRegisterRequest.setEmail(email);
            userRegisterRequest.setMobileNumber(mobileNumber);
            Bundle bundle = new Bundle();
            bundle.putSerializable("user", userRegisterRequest);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registerUserFragment_to_userPasswordFragment, bundle);
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonNext) {
            validateUserData();
        }
    }
}