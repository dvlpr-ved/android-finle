package com.dkglabs.data.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.dkglabs.data.dao.LoanApplicantDao;
import com.dkglabs.data.dao.LoanApplicantEmiDao;
import com.dkglabs.data.dao.database.FinleDatabase;
import com.dkglabs.data.entity.LoanApplicant;
import com.dkglabs.data.entity.LoanApplicantEMI;

import java.util.List;

public class ApplicantRepository {

    private final LoanApplicantDao mLoanApplicantDao;
    private final LoanApplicantEmiDao mLoanApplicantEmiDao;
    private final LiveData<List<LoanApplicant>> mLoanApplicants;

    private final LiveData<List<LoanApplicantEMI>> mLoanApplicantsEmi;


    public ApplicantRepository(Application application) {
        FinleDatabase db = FinleDatabase.getInstance(application);
        mLoanApplicantDao = db.loanApplicantDao();
        mLoanApplicantEmiDao = db.loanApplicantEmiDao();
        mLoanApplicants = mLoanApplicantDao.fetchAll();
        mLoanApplicantsEmi = mLoanApplicantEmiDao.fetchAll();
    }

    public List<LoanApplicant> searchApplicant(String searchQuery) {
        return mLoanApplicantDao.searchApplicant(searchQuery);
    }

    public LiveData<List<LoanApplicant>> getAllApplicants() {
        return mLoanApplicants;
    }

    public void insertLoanApplicant(LoanApplicant loanApplicant) {
        FinleDatabase.databaseWriteExecutor.execute(() -> mLoanApplicantDao.insert(loanApplicant));
    }

    public void insertAllLoanApplicant(List<LoanApplicant> loanApplicantList) {
        FinleDatabase.databaseWriteExecutor.execute(() -> mLoanApplicantDao.insertAll(loanApplicantList));
    }
    public void insertAllLoanApplicantEmi(List<LoanApplicantEMI> loanApplicantEmiList) {
        FinleDatabase.databaseWriteExecutor.execute(() -> mLoanApplicantEmiDao.insertAll(loanApplicantEmiList));
    }
}
