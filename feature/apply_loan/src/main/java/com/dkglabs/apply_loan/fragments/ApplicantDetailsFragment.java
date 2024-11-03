package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentApplicantDetailsBinding;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.model.applyloan.IncomeDetails;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.model.request.LoanRequest;
import com.dkglabs.model.applyloan.PersonalDetails;
import com.dkglabs.remote.manager.LoanManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ApplicantDetailsFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, ResponseListener {

    private final int PERSONAL_DETAILS = 1001;

    private boolean personal = false;

    private List<String> residenceOwnerList;
    private List<String> qualificationList;
    private List<String> yearList;
    private FragmentApplicantDetailsBinding binding = null;

    public ApplicantDetailsFragment() {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentApplicantDetailsBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(3);
        viewModel.setSelectedItem(applyLoanModel);
    }

    private void initializeView() {

        UIUtils.hideViewGone(binding.residenceOwnershipOther);
        residenceOwnerList = new ArrayList<>();
        residenceOwnerList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_residence_owner)));
        ArrayAdapter<String> residenceAdapter = getArrayAdapter(residenceOwnerList);
        binding.residenceOwnership.setAdapter(residenceAdapter);
        binding.residenceOwnership.setOnItemClickListener(this);

        yearList = new ArrayList<>();
        yearList.addAll(Arrays.asList("3 months", "6 months", "9 months", "1 year", "2 year", "3 year", "4 year", "5 year", "More than 5 years"));
        ArrayAdapter<String> yearAdapter = getArrayAdapter(yearList);
        binding.yearOfResidence.setAdapter(yearAdapter);

        qualificationList = new ArrayList<>();
        qualificationList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_qualification)));
        ArrayAdapter<String> qualificationAdapter = getArrayAdapter(qualificationList);
        binding.qualification.setAdapter(qualificationAdapter);

        binding.buttonNext.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buttonNext) validateData();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 2) {
            UIUtils.showView(binding.residenceOwnershipOther);
        } else {
            UIUtils.hideViewGone(binding.residenceOwnershipOther);
        }
    }

    private void validateData() {
        UIUtils.hideKeyboard(getActivity());
        removeErrors();
        String qualification = binding.qualification.getText().toString().trim();
        String residenceOwnership = binding.residenceOwnership.getText().toString().trim();
        String residenceOwnershipOther = binding.residenceOwnershipOther.getEditText().getText().toString().trim();
        String yearOfResidence = binding.yearOfResidence.getText().toString().trim();


        if (qualification.isEmpty()) {
            binding.qualification.setError(getString(R.string.msg_qualification));
            binding.qualification.requestFocus();
            return;
        }

        int id = -1;
        id = binding.radioGroupMarital.getCheckedRadioButtonId();
        if (id == -1) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.msg_marital_status));
            binding.radioGroupMarital.requestFocus();
            return;
        }
        RadioButton radioButton = binding.getRoot().findViewById(id);
        String maritalStatus = radioButton.getTag().toString().trim();

        int idGender = -1;
        idGender = binding.radioGroupGender.getCheckedRadioButtonId();
        if (idGender == -1) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.msg_gender));
            binding.radioGroupGender.requestFocus();
            return;
        }
        RadioButton radioButtonGender = binding.getRoot().findViewById(idGender);
        String gender = radioButtonGender.getTag().toString().trim();

        if (residenceOwnership.isEmpty()) {
            binding.residenceOwnership.setError(getString(R.string.msg_residence_ownership));
            binding.residenceOwnership.requestFocus();
            return;
        }

        if (residenceOwnership.equals(getString(R.string.residence_other)) && residenceOwnershipOther.isEmpty()) {
            binding.residenceOwnershipOther.setError(getString(R.string.msg_enter_residence_ownership));
            binding.residenceOwnershipOther.requestFocus();
            return;
        } else {
            residenceOwnership.concat(" - ").concat(residenceOwnershipOther);
        }

        if (yearOfResidence.isEmpty()) {
            binding.yearOfResidence.setError(getString(R.string.msg_year_of_residence));
            binding.yearOfResidence.requestFocus();
            return;
        }

        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setGender(gender);
        personalDetails.setQualification(qualification);
        personalDetails.setMaritalStatus(maritalStatus);
        personalDetails.setResidenceOwnership(residenceOwnership);
        personalDetails.setYearOfResidence(yearOfResidence);

        LoanRequest request = new LoanRequest();
        request.setPersonalDetails(personalDetails);
        request.setUserId(PersistentManager.getLoginResponse().getUserId());
        request.setLoanId(LoanPersistentManager.getLoanId());
        progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.saving_details));

        LoanManager.savePersonalDetails(PERSONAL_DETAILS, request,  this);
    }

    private void removeErrors() {
        binding.qualification.setError(null);
        binding.residenceOwnership.setError(null);
        binding.residenceOwnershipOther.setError(null);
        binding.yearOfResidence.setError(null);
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if (requestCode == PERSONAL_DETAILS) {
            LoanResponse loanResponse1 = (LoanResponse) response.getResponseData();
            if (loanResponse1 != null) {
                personal = true;
            }
        }

        if (personal) {
            Navigation.findNavController(binding.getRoot()).navigate(ApplicantDetailsFragmentDirections.actionApplicantDetailFragmentToLoanUserContactFragment());
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