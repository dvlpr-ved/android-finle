package com.dkglabs.apply_loan.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.dkglabs.apply_loan.databinding.FragmentEmiCollectionBinding;
import com.dkglabs.apply_loan.utils.EmiDateUtils;
import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.model.request.SaveLoanPaymentDetailsRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.DocumentManager;
import com.google.android.material.radiobutton.MaterialRadioButton;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EmiCollectionFragment extends BaseFragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener, ResponseListener {
    private static final int REQ_SAVE_LOAN_PAYMENT_DETAILS = 101;
    private LoanResponse loanResponse;
    FragmentEmiCollectionBinding binding;

    private String email = "";
    private String date;
    private String frequency;
    private String paymentMethod;
    private static final String loanTenure = "36";
    private static final String interestRate = "25";
    Dialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loanResponse = EmiCollectionFragmentArgs.fromBundle(getArguments()).getLoanResponse();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEmiCollectionBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        double scientificNotation = Double.parseDouble(loanResponse.getLoanDetail().getLoanRequestAmt());
        BigDecimal bigDecimal = BigDecimal.valueOf(scientificNotation);
        String decimalRepresentation = bigDecimal.toPlainString();
        binding.txtAmount.setText(decimalRepresentation);
        binding.setLoanResponse(loanResponse);
        binding.radioFrequency.setOnCheckedChangeListener(this);
        binding.radioPaymentMethod.setOnCheckedChangeListener(this);
        binding.buttonNext.setOnClickListener(this);
        binding.txtAmount.setOnClickListener(this);
        binding.txtInterest.setEnabled(false);
        date = EmiDateUtils.getFirstEmiDate();
        binding.textEmiDate.setText(date);

        if (loanResponse.getContactDetails().getEmail() != null && !loanResponse.getContactDetails().getEmail().isEmpty()) {
            email = loanResponse.getContactDetails().getEmail();
            binding.tilEmail.getEditText().setText(email);
            binding.tilEmail.setEnabled(false);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == binding.buttonNext.getId()) {
            checkAll();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

        if (checkedId == binding.radioDaily.getId() || checkedId == binding.radioMonthly.getId()) {
            handleRadioFreq(checkedId);
        }
        if (checkedId == binding.radioManual.getId()) {
            handelRadioPayment(checkedId);
        }
        if (checkedId == binding.radioAuto.getId()) {
//            Toast.makeText(requireContext(), "Auto Pay Not Available", Toast.LENGTH_SHORT).show();
            handelRadioPayment(checkedId);
        }

    }

    private void handleRadioFreq(int checkedId) {
        MaterialRadioButton radioButton;
        radioButton = binding.getRoot().findViewById(checkedId);
        frequency = radioButton.getText().toString().toLowerCase();
    }

    private void handelRadioPayment(int checkedId) {
        MaterialRadioButton radioButton;
        radioButton = binding.getRoot().findViewById(checkedId);
        paymentMethod = radioButton.getText().toString().toLowerCase();
    }


    private void checkAll() {

        if (loanTenure.length() == 0) {
            Toast.makeText(requireContext(), "Enter Loan Tenure", Toast.LENGTH_SHORT).show();
            return;
        }
        if (interestRate.length() == 0) {
            Toast.makeText(requireContext(), "Enter Interest Rate", Toast.LENGTH_SHORT).show();
            return;
        }
        if (date == null) {
            Toast.makeText(requireContext(), "Enter EMI Submission Date", Toast.LENGTH_SHORT).show();
            return;
        }
        if (frequency == null) {
            Toast.makeText(requireContext(), "Enter The Frequency", Toast.LENGTH_SHORT).show();
            return;
        }
        if (paymentMethod == null) {
            Toast.makeText(requireContext(), "Enter Payment Method", Toast.LENGTH_SHORT).show();
            return;
        }
        email = binding.tilEmail.getEditText().getText().toString();
        if (email.isEmpty() || !AppUtils.validateEmail(email)) {
            Toast.makeText(requireContext(), "Enter valid Email", Toast.LENGTH_SHORT).show();
            return;
        }

        createEMI();
    }

    private void createEMI() {
        SaveLoanPaymentDetailsRequest saveLoanPaymentDetailsRequest = new SaveLoanPaymentDetailsRequest();
        saveLoanPaymentDetailsRequest.setLoanAmount(Double.parseDouble(loanResponse.getLoanDetail().getLoanRequestAmt()));
        saveLoanPaymentDetailsRequest.setLoanEmiFrequency(frequency);
        saveLoanPaymentDetailsRequest.setEmailId(email);
        saveLoanPaymentDetailsRequest.setLoanEmiPaymentDate(date);
        saveLoanPaymentDetailsRequest.setLoanEmiPaymentMode(paymentMethod);
        saveLoanPaymentDetailsRequest.setLoanId(loanResponse.getLoanId());
        //saveLoanPaymentDetailsRequest.setLoanInterest(Double.parseDouble(interestRate));
        saveLoanPaymentDetailsRequest.setLoanTenure(Integer.parseInt(loanTenure));
        saveLoanPaymentDetailsRequest.setLoanType(" ");
        saveLoanPaymentDetailsRequest.setNbfcLoanId(loanResponse.getNbfcId());
        saveLoanPaymentDetailsRequest.setRateOfInterest(Double.parseDouble(interestRate));
        saveLoanPaymentDetailsRequest.setUserId(loanResponse.getUserId());
        dialog = UIUtils.showProgressDialog(getActivity(), "Creating EMI Please Wait");
        dialog.setCancelable(false);

        DocumentManager.saveLoanPaymentDetails(REQ_SAVE_LOAN_PAYMENT_DETAILS, saveLoanPaymentDetailsRequest, this);
    }


    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if (requestCode == REQ_SAVE_LOAN_PAYMENT_DETAILS) {
            UIUtils.showToast(context, "EMI Created Successfully");
            Navigation.findNavController(binding.getRoot()).navigate(EmiCollectionFragmentDirections.actionEmiCollectionFragmentToEnachStatusFragment());
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailure(int requestCode, Throwable t) {
        UIUtils.showGenericErrorDialog(binding.getRoot(), context);
    }

    @Override
    public void commonCall(int requestCode) {
        UIUtils.dismissDialog(dialog);
    }
}