package com.dkglabs.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dkglabs.data.dao.LoanApplicantEmiDao;
import com.dkglabs.data.dao.database.FinleDatabase;
import com.dkglabs.data.entity.LoanApplicantEMI;

import java.util.List;

public class ApplicantEmiRepository {

    private final LoanApplicantEmiDao mLoanApplicantEmiDao;
    private final LiveData<List<LoanApplicantEMI>> mLoanApplicantsEmi;


    public ApplicantEmiRepository(Application application) {
        FinleDatabase db = FinleDatabase.getInstance(application);
        mLoanApplicantEmiDao = db.loanApplicantEmiDao();
        mLoanApplicantsEmi = mLoanApplicantEmiDao.fetchAll();
    }

    public LiveData<List<LoanApplicantEMI>> getAllApplicantsEmi() {
        return mLoanApplicantsEmi;
    }

    public void insertLoanApplicantEmi(LoanApplicantEMI loanApplicantEmi) {
        FinleDatabase.databaseWriteExecutor.execute(() -> mLoanApplicantEmiDao.insert(loanApplicantEmi));
    }

    public void insertAllLoanApplicantEmi(List<LoanApplicantEMI> loanApplicantEmiList) {
        FinleDatabase.databaseWriteExecutor.execute(() -> mLoanApplicantEmiDao.insertAll(loanApplicantEmiList));
    }
}
