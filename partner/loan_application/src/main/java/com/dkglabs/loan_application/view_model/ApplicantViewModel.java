package com.dkglabs.loan_application.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dkglabs.data.entity.LoanApplicant;
import com.dkglabs.data.entity.LoanApplicantEMI;
import com.dkglabs.data.repository.ApplicantRepository;

import java.util.List;

public class ApplicantViewModel extends AndroidViewModel {

    private final LiveData<List<LoanApplicant>> mLoanApplicant;
    private final ApplicantRepository mApplicantRepository;

    public ApplicantViewModel(@NonNull Application application) {
        super(application);

        mApplicantRepository = new ApplicantRepository(application);
        mLoanApplicant = mApplicantRepository.getAllApplicants();
    }
    public LiveData<List<LoanApplicant>> getAllApplicants() {
        return mLoanApplicant;
    }

    public void insertAllLoanApplicant(List<LoanApplicant> loanApplicantList) {
        mApplicantRepository.insertAllLoanApplicant(loanApplicantList);
    }
    public void insertAllLoanApplicantEmi(List<LoanApplicantEMI> loanApplicantEmiList) {
        mApplicantRepository.insertAllLoanApplicantEmi(loanApplicantEmiList);
    }
}
