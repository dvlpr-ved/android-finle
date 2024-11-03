package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentLoanUserAddressBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.applyloan.AddressDetails;
import com.dkglabs.model.request.LoanRequest;
import com.dkglabs.model.response.AadhaarAddress;
import com.dkglabs.model.response.AadhaarOtpResponse;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.PostOffice;
import com.dkglabs.model.response.PostalPinResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.PinListener;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;
import com.dkglabs.remote.manager.PostalManager;

import java.util.List;

public class LoanUserAddressFragment extends BaseFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, ResponseListener {

    //private AadhaarResponse aadhaarResponse;
    private AadhaarOtpResponse aadhaarOtpResponse;
    private AadhaarAddress aadhaarAddress;
    private FragmentLoanUserAddressBinding binding = null;

    public LoanUserAddressFragment() {
        // Required empty public constructor
    }

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoanUserAddressBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(1);
        viewModel.setSelectedItem(applyLoanModel);


        //aadhaarResponse = LoanPersistentManager.getAadhaarResponse();
        aadhaarOtpResponse = LoanUserAddressFragmentArgs.fromBundle(getArguments()).getAadhaarData();
        aadhaarAddress = aadhaarOtpResponse.getData().getAddress();
        binding.permanentAddress.setText(String.format("%s (%s)", aadhaarOtpResponse.getData().getAddress().toString(), aadhaarOtpResponse.getData().getZip()));
    }

    private void initializeView() {
        binding.buttonNext.setOnClickListener(this);
        binding.sameAddress.setOnCheckedChangeListener(this);
        binding.presentPostalAddress.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.length() == 6 && !binding.sameAddress.isChecked()) {
                    PostalManager.getPostalPinDetails(111, charSequence.toString(), new PinListener() {
                        @Override
                        public void onResponse(int requestCode, PostalPinResponse response) {
                            if (response != null) {
                                List<PostOffice> postOffice = response.getPostOffice();
                                binding.presentDistrict.getEditText().setText(postOffice.get(0).getDistrict());
                                binding.presentCountry.getEditText().setText(postOffice.get(0).getCountry());
                                binding.presentState.getEditText().setText(postOffice.get(0).getState());
                            }
                        }

                        @Override
                        public void onFailure(int requestCode, Throwable t) {

                        }
                    });
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonNext) {
            validateData();
        }
    }

    private void validateData() {
        UIUtils.hideKeyboard(getActivity());
        removeErrors();

        if (binding.sameAddress.isChecked()) {
            setAddress();
        }

        String presentAddressLineOne = binding.presentAddressLineOne.getEditText().getText().toString().trim();
        String presentCity = binding.presentCity.getEditText().getText().toString().trim();
        String presentPostalAddress = binding.presentPostalAddress.getEditText().getText().toString().trim();
        String presentDistrict = binding.presentDistrict.getEditText().getText().toString().trim();
        String presentState = binding.presentState.getEditText().getText().toString().trim();
        String presentCountry = binding.presentCountry.getEditText().getText().toString().trim();


        if (presentAddressLineOne.isEmpty()) {
            UIUtils.showToast(getContext(), getString(R.string.msg_current_address));
            binding.presentAddressLineOne.requestFocus();
            return;
        }

        if (!binding.sameAddress.isChecked() && presentCity.isEmpty()) {
            UIUtils.showToast(getContext(), getString(R.string.msg_current_city));
            binding.presentCity.requestFocus();
            return;
        }

        if (presentPostalAddress.isEmpty()) {
            UIUtils.showToast(getContext(), getString(R.string.msg_pin_code));
            binding.presentPostalAddress.requestFocus();
            return;
        }

        if (presentPostalAddress.length() < 6) {
            UIUtils.showToast(getContext(), getString(R.string.msg_valid_pin_code));
            binding.presentPostalAddress.requestFocus();
            return;
        }

        if (presentDistrict.isEmpty()) {
            UIUtils.showToast(getContext(), getString(R.string.msg_district_name));
            binding.presentDistrict.requestFocus();
            return;
        }

        if (presentState.isEmpty()) {
            UIUtils.showToast(getContext(), getString(R.string.msg_state_name));
            binding.presentState.requestFocus();
            return;
        }

        if (presentCountry.isEmpty()) {
            UIUtils.showToast(getContext(), getString(R.string.msg_country_name));
            binding.presentCountry.requestFocus();
            return;
        }

        String permanentAddressLineOne = aadhaarAddress.getAddressLineOne();

        AddressDetails addressDetails = new AddressDetails();
        addressDetails.setPermanentAddressLineOne(permanentAddressLineOne);
        addressDetails.setPermanentCity(aadhaarAddress.getPo());
        addressDetails.setPermanentPostalAddress(aadhaarOtpResponse.getData().getZip());
        addressDetails.setPermanentDistrict(aadhaarAddress.getDist());
        addressDetails.setPermanentState(aadhaarAddress.getState());
        addressDetails.setPermanentCountry(aadhaarAddress.getCountry());

        addressDetails.setPresentAddressLineOne(presentAddressLineOne);
        addressDetails.setPresentCity(presentCity);
        addressDetails.setPresentPostalAddress(presentPostalAddress);
        addressDetails.setPresentDistrict(presentDistrict);
        addressDetails.setPresentState(presentState);
        addressDetails.setPresentCountry(presentCountry);

        LoanRequest request = new LoanRequest();
        request.setAddressDetails(addressDetails);
        request.setUserId(PersistentManager.getUserResponse().getUserId());
        request.setLoanId(LoanPersistentManager.getLoanId());

        progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.saving_address_details));
        LoanManager.saveAddressDetails(1001, request,  this);
    }

    private void removeErrors() {
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (isChecked) {
            disableView();
            setAddress();
        } else {
            clearAddress();
            enableView();
        }

    }

    private void enableView() {
        UIUtils.enableView(binding.presentAddressLineOne);
        UIUtils.enableView(binding.presentCity);
        UIUtils.enableView(binding.presentPostalAddress);
        UIUtils.enableView(binding.presentDistrict);
        UIUtils.enableView(binding.presentState);
        UIUtils.enableView(binding.presentCountry);
    }

    private void clearAddress() {
        binding.presentAddressLineOne.getEditText().setText("");
        binding.presentCity.getEditText().setText("");
        binding.presentPostalAddress.getEditText().setText("");
        binding.presentDistrict.getEditText().setText("");
        binding.presentState.getEditText().setText("");
        binding.presentCountry.getEditText().setText("");
    }

    private void disableView() {
        UIUtils.disableView(binding.presentAddressLineOne);
        UIUtils.disableView(binding.presentCity);
        UIUtils.disableView(binding.presentPostalAddress);
        UIUtils.disableView(binding.presentDistrict);
        UIUtils.disableView(binding.presentState);
        UIUtils.disableView(binding.presentCountry);
    }

    private void setAddress() {
        String permanentAddressLineOne = aadhaarAddress.getAddressLineOne();

        binding.presentAddressLineOne.getEditText().setText(permanentAddressLineOne);
        binding.presentCity.getEditText().setText(aadhaarAddress.getPo());
        binding.presentPostalAddress.getEditText().setText(aadhaarOtpResponse.getData().getZip());
        binding.presentDistrict.getEditText().setText(aadhaarAddress.getDist());
        binding.presentState.getEditText().setText(aadhaarAddress.getState());
        binding.presentCountry.getEditText().setText(aadhaarAddress.getCountry());
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        String loanResponse = (String) response.getResponseData();
        if (loanResponse.equals("Address Details saved.")) {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loanUserAddressFragment_to_basicKycDlFragment);
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        UIUtils.showSnackbar(binding.getRoot(), message.isEmpty() ? getString(com.dkglabs.base.R.string.generic_error_msg) : message);
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showSnackbar(binding.getRoot(), getString(com.dkglabs.base.R.string.generic_error_msg));
    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.dismissDialog(progressDialog);
    }
}