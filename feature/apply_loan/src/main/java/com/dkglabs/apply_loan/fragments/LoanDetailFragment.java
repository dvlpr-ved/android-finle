package com.dkglabs.apply_loan.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.R;
import com.dkglabs.apply_loan.databinding.FragmentLoanDetailsBinding;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.LoanPersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.applyloan.CollateralDetails;
import com.dkglabs.model.applyloan.IncomeDetails;
import com.dkglabs.model.applyloan.LoanDetail;
import com.dkglabs.model.request.LoanRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.model.viewmodel.ApplyLoanModel;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.LoanManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoanDetailFragment extends BaseFragment implements View.OnClickListener, AdapterView.OnItemClickListener, ResponseListener {

    private final int LOAN_DETAILS = 1001;
    private final int INCOME_DETAILS = 1002;
    private final int COLLATERAL_DETAILS = 1003;

    private boolean loan = false;
    private boolean income = false;
    private boolean collateral = false;
    private boolean loanProgress = false;
    private boolean incomeProgress = false;
    private boolean collateralProgress = false;

    private List<String> borrowerList;
    private List<String> collateralList;
    private List<String> memberList;

    private FragmentLoanDetailsBinding binding = null;

    public LoanDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ApplyLoanModel applyLoanModel = new ApplyLoanModel();
        applyLoanModel.setLoanState(2);
        viewModel.setSelectedItem(applyLoanModel);
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
        binding = FragmentLoanDetailsBinding.inflate(getLayoutInflater(), container, false);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        borrowerList = new ArrayList<>();
        borrowerList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_type_of_borrower)));
        ArrayAdapter<String> borrowerAdapter = getArrayAdapter(borrowerList);
        binding.typeOfBorrower.setAdapter(borrowerAdapter);

        memberList = new ArrayList<>();
        memberList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_member)));
        ArrayAdapter<String> memberAdapter = getArrayAdapter(memberList);
        binding.householdDepends.setAdapter(memberAdapter);

        collateralList = new ArrayList<>();
        collateralList.addAll(Arrays.asList(getResources().getStringArray(R.array.array_collateral)));
        ArrayAdapter<String> collateralAdapter = getArrayAdapter(collateralList);
        binding.typeOfCollateral.setAdapter(collateralAdapter);
        binding.typeOfCollateral.setOnItemClickListener(this);

        binding.buttonNext.setOnClickListener(this);

        binding.radioGroupOtherLoan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (radioGroup.getCheckedRadioButtonId() == R.id.radioYes) {
                    UIUtils.showView(binding.otherLoanAmount);
                } else {
                    UIUtils.hideViewGone(binding.otherLoanAmount);
                }
            }
        });

        LoanManager.loanDetails(1000, PersistentManager.getUserResponse().getUserId(), LoanPersistentManager.getLoanId(),  this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.buttonNext) {
            validateData();
        }
    }

    private void validateData() {
        loan = false;
        income = false;
        collateral = false;
        loanProgress = false;
        incomeProgress = false;
        collateralProgress = false;
        UIUtils.hideKeyboard(getActivity());
        removeErrors();

        String typeOfBorrower = binding.typeOfBorrower.getText().toString().trim();
        String loanAmount = binding.loanAmount.getEditText().getText().toString().trim();
        String annualIncome = binding.annualIncome.getEditText().getText().toString().trim();
        String otherLoanAmount = binding.otherLoanAmount.getEditText().getText().toString().trim();
        String typeOfCollateral = binding.typeOfCollateral.getText().toString().trim();
        String collateralOther = binding.collateralOther.getEditText().getText().toString().trim();
        String householdDepends = binding.householdDepends.getText().toString().trim();
        String numberOfEarningMember = binding.txtNumberOfEarningMember.getEditText().getText().toString().trim();
//        String emFirstName = binding.emFirstName.getEditText().getText().toString().trim();
//        String emLastName = binding.emLastName.getEditText().getText().toString().trim();

        if (typeOfBorrower.isEmpty()) {
            binding.typeOfBorrower.setError(getString(R.string.msg_means_of_earning));
            binding.typeOfBorrower.requestFocus();
            return;
        }

        if (loanAmount.isEmpty()) {
            binding.loanAmount.setError(getString(R.string.msg_enter_loan_amount));
            binding.loanAmount.requestFocus();
            return;
        }

        if (annualIncome.isEmpty()) {
            binding.annualIncome.setError(getString(R.string.msg_enter_annual_income));
            binding.annualIncome.requestFocus();
            return;
        }

        if (householdDepends.isEmpty()) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.msg_household_depends));
            binding.householdDepends.requestFocus();
            return;
        }

        if (typeOfCollateral.isEmpty()) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.msg_collateral));
            binding.typeOfCollateral.requestFocus();
            return;
        }
        if (typeOfCollateral.equals(getString(R.string.collateral_other)) && collateralOther.isEmpty()) {
            UIUtils.showSnackbar(binding.getRoot(), getString(R.string.msg_collateral_type));
            binding.collateralOther.getEditText().requestFocus();
            return;
        } else {
            typeOfCollateral = typeOfCollateral + " - " + collateralOther;
        }

        int id = -1;
        id = binding.radioGroupOtherLoan.getCheckedRadioButtonId();

        if (id == -1) {
            UIUtils.showView(binding.otherLoanError);
            binding.radioGroupOtherLoan.requestFocus();
            return;
        }
        RadioButton radioButton = binding.getRoot().findViewById(id);
        String otherLoan = radioButton.getTag().toString();
        if (otherLoan.equals("Y") && otherLoanAmount.isEmpty()) {
            binding.otherLoanAmount.setError(getString(R.string.msg_loan_amount));
            binding.otherLoanAmount.requestFocus();
            return;
        }

        LoanDetail loanDetail = new LoanDetail();
        loanDetail.setLoanRequestAmt(loanAmount);

        IncomeDetails incomeDetails = new IncomeDetails();
        incomeDetails.setDependentMember(householdDepends);
//        incomeDetails.setEarningMemberFirstName(emFirstName);
//        incomeDetails.setEarningMemberLastName(emLastName);
        incomeDetails.setNumberOfEarningMember(numberOfEarningMember);
        incomeDetails.setBorrowerType(typeOfBorrower);
        incomeDetails.setAnnualIncome(annualIncome);
        incomeDetails.setOtherLoan(otherLoan);
        incomeDetails.setOtherLoanAmount(otherLoanAmount);

        CollateralDetails collateralDetails = new CollateralDetails();
        collateralDetails.setCollateralType(typeOfCollateral);

        LoanRequest request = new LoanRequest();
        request.setIncomeDetails(incomeDetails);
        request.setLoanDetail(loanDetail);
        request.setCollateralDetails(collateralDetails);
        request.setUserId(PersistentManager.getUserResponse().getUserId());
        request.setLoanId(LoanPersistentManager.getLoanId());

        progressDialog = UIUtils.showProgressDialog(getActivity(), getString(R.string.saving_details));
        LoanManager.saveIncomeDetails(INCOME_DETAILS, request,  this);
        LoanManager.saveLoanDetails(LOAN_DETAILS, request,  this);
        LoanManager.saveCollateralDetails(COLLATERAL_DETAILS, request,  this);
    }

    private void removeErrors() {
        binding.typeOfBorrower.setError(null);
        binding.annualIncome.setError(null);
        binding.otherLoanAmount.setError(null);
        binding.loanAmount.setError(null);
        UIUtils.hideViewGone(binding.otherLoanError);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        if (i == 2) {
            UIUtils.showView(binding.collateralOther);
        } else {
            UIUtils.hideViewGone(binding.collateralOther);
        }
    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if (requestCode == 1000) {
            LoanResponse loanResponse = (LoanResponse) response.getResponseData();
            LoanPersistentManager.setLoanResponse(loanResponse);
        } else if (requestCode == INCOME_DETAILS) {
            LoanResponse loanResponse = (LoanResponse) response.getResponseData();
            if (loanResponse != null)
                income = true;
        } else {
            String loanResponse = (String) response.getResponseData();
            switch (requestCode) {
                /*case INCOME_DETAILS:
                    if (loanResponse.equals("Income Details saved.")) {
                        income = true;
                    }
                    break;*/
                case LOAN_DETAILS:
                    if (loanResponse.equals("Loan Details saved.")) {
                        loan = true;
                    }
                    break;
                case COLLATERAL_DETAILS:
                    if (loanResponse.equals("Collateral Details saved.")) {
                        collateral = true;
                    }
                    break;
            }

            if (income && loan && collateral) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loanDetailFragment_to_applicantDetailFragment);
            }
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
        switch (requestCode) {
            case INCOME_DETAILS:
                incomeProgress = true;
                break;
            case LOAN_DETAILS:
                loanProgress = true;
                break;
            case COLLATERAL_DETAILS:
                collateralProgress = true;
                break;
        }

        if (incomeProgress && loanProgress && collateralProgress) {
            UIUtils.dismissDialog(progressDialog);
        }
    }
}