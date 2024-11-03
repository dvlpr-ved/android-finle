package com.dkglabs.loan_application.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dkglabs.base.fragments.BaseFragment;
import com.dkglabs.base.manager.BasePersistentManager;
import com.dkglabs.base.manager.PersistentManager;
import com.dkglabs.base.utils.UIUtils;
import com.dkglabs.data.entity.LoanApplicant;
import com.dkglabs.loan_application.R;
import com.dkglabs.loan_application.adapters.LoanApplicantAdapter;

import com.dkglabs.loan_application.databinding.FragmentApplicationBinding;
import com.dkglabs.loan_application.view_model.ApplicantViewModel;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoanApplicantResponse;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.manager.BaseManager;
import com.dkglabs.remote.manager.LoanManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class ApplicationFragment extends BaseFragment implements ResponseListener, View.OnClickListener {

    private List<LoanApplicantResponse> loanApplicantsList;
    private List<LoanApplicant> loanApplicant2;
    private List<LoanResponse> loanApplicant;
    private ApplicantViewModel applicantViewModel;
    private LoanApplicantAdapter adapter;

    private FragmentApplicationBinding binding = null;
    Dialog progressDialog;


    public ApplicationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentApplicationBinding.inflate(inflater, container, false);
        initializeView();
        return binding.getRoot();
    }

    private void initializeView() {
        setUpBackToolbar("Loan Application");
        loanApplicantsList = new ArrayList<>();
        loanApplicant = new ArrayList<>();
        loanApplicant2=new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        binding.recyclerView.setLayoutManager(manager);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        adapter = new LoanApplicantAdapter(context, loanApplicantsList, this);
        binding.recyclerView.setAdapter(adapter);
        progressDialog = UIUtils.showProgressDialog(getActivity(), "Verifying Users...");

//        LoanManager.allNbfcApprovedLoanApplicants(1001,  this);
        LoanManager.allNbfcApprovedLoanApplicantsWithId(1001,PersistentManager.getUserResponse().getUserId(),this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        applicantViewModel = new ViewModelProvider(requireActivity()).get(ApplicantViewModel.class);
        applicantViewModel.getAllApplicants().observe(getViewLifecycleOwner(), new Observer<List<LoanApplicant>>() {
            @Override
            public void onChanged(List<LoanApplicant> loanApplicantList) {
                if (loanApplicantList != null) {
                    if (loanApplicant2 != null && loanApplicant2.size() > 0)
                        loanApplicant2.clear();
                    loanApplicant2.addAll(loanApplicantList);
                    updateLoanList();
                }
            }
        });
    }
    private void updateLoanList() {
        if (loanApplicantsList != null && loanApplicantsList.size() > 0)
            loanApplicantsList.clear();
        if (loanApplicant != null && loanApplicant.size() > 0) {
            for (LoanResponse applicant : loanApplicant) {
                LoanApplicantResponse loanApplicantResponse = new LoanApplicantResponse();
                loanApplicantResponse.setLoanId(applicant.getLoanId());
                loanApplicantResponse.setUserId(applicant.getUserId());
                loanApplicantResponse.setName(applicant.getPersonalDetails().getName());
                loanApplicantResponse.setEmail(applicant.getContactDetails().getEmail());

                Double amt=0.0;
                if(!applicant.getLoanDetail().getLoanRequestAmt().equals("null"))
                    amt= Double.valueOf(applicant.getLoanDetail().getLoanRequestAmt());
                loanApplicantResponse.setPhone(applicant.getPersonalDetails().getMobileNumber());
//                loanApplicantResponse.setPartnerCode(applicant.getPartnerCode());
                if(applicant.getLoanDetail().getLoanRequestAmt().equals("null")) {
                    loanApplicantResponse.setLoanAmount(0.0);
                }
                else {
                    loanApplicantResponse.setLoanAmount(amt);
                }
//                loanApplicantResponse.setTerm(applicant.getTerm());
//                loanApplicantResponse.setLoanEmiAmount(applicant.getLoanEmiAmount());
//                loanApplicantResponse.setLoanEmiPaymentDate(applicant.getLoanEmiPaymentDate());
//                loanApplicantResponse.setEmiDue(applicant.getEmiDue());
//                loanApplicantResponse.setLateFeeDue(applicant.getLateFeeDue());
//                loanApplicantResponse.setExtraPaid(applicant.getExtraPaid());
//                loanApplicantResponse.setStatus(applicant.getStatus());
//                loanApplicantResponse.setSubStatus(applicant.getSubStatus());
//                loanApplicantResponse.setLastPaidAmount(applicant.getLastPaidAmount());
//                loanApplicantResponse.setLastPaidDate(applicant.getLastPaidDate());
//                loanApplicantResponse.setDueEmiAmount(applicant.getDueEmiAmount());
//                loanApplicantResponse.setCurrentEmiPaymentDate(applicant.getCurrentEmiPaymentDate());
                loanApplicantsList.add(loanApplicantResponse);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.buttonUpload){
            LoanApplicantResponse response = (LoanApplicantResponse) view.getTag();
            ApplicationFragmentDirections.ActionApplicationFragmentToApplicationDetailFragment direction = ApplicationFragmentDirections.actionApplicationFragmentToApplicationDetailFragment(response);
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        }
        if(id==R.id.btnEmi){
            LoanApplicantResponse response = (LoanApplicantResponse) view.getTag();
            ApplicationFragmentDirections.ActionApplicationFragmentToEmiCollectionFragment direction=ApplicationFragmentDirections.actionApplicationFragmentToEmiCollectionFragment(response);
            Navigation.findNavController(binding.getRoot()).navigate(direction);
        }

    }

    @Override
    public void onResponse(int requestCode, BaseResponse response) {
        if(requestCode==1001){
            loanApplicant= (List<LoanResponse>) response.getResponseData();
            UIUtils.dismissDialog(progressDialog);
            updateLoanList();
        }
    }

    @Override
    public void onValidationFailure(int requestCode, int errorTypeCode, String message) {

    }

    @Override
    public void onFailure(int requestCode, Throwable t) {

    }

    @Override
    public void commonCall(int requestCode) {

    }
}