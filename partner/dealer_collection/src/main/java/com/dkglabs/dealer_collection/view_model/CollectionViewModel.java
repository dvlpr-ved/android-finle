package com.dkglabs.dealer_collection.view_model;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dkglabs.data.entity.LoanApplicant;
import com.dkglabs.data.entity.LoanApplicantEMI;
import com.dkglabs.data.interfaces.DatabaseListener;
import com.dkglabs.data.repository.ApplicantEmiRepository;
import com.dkglabs.data.repository.ApplicantRepository;
import com.dkglabs.model.CollectionCard;

import java.util.List;

public class CollectionViewModel extends AndroidViewModel {

    private final LiveData<List<LoanApplicant>> mLoanApplicant;
    private final ApplicantEmiRepository mApplicantEmiRepository;
    private final LiveData<List<LoanApplicantEMI>> mLoanApplicantEmi;
    private final ApplicantRepository mApplicantRepository;
    private List<LoanApplicant> mSearchLoanApplicant;
    public MutableLiveData<CollectionCard> mCollectionCard = new MutableLiveData<>();

    public CollectionViewModel(@NonNull Application application) {
        super(application);

        mApplicantRepository = new ApplicantRepository(application);
        mApplicantEmiRepository = new ApplicantEmiRepository(application);
        mLoanApplicant = mApplicantRepository.getAllApplicants();
        mLoanApplicantEmi = mApplicantEmiRepository.getAllApplicantsEmi();
    }

    public LiveData<CollectionCard> getCollectionCard() {
        return mCollectionCard;
    }

    public void setCollectionCard(CollectionCard collectionCard) {
        mCollectionCard.setValue(collectionCard);
    }

    public LiveData<List<LoanApplicant>> getAllApplicants() {
        return mLoanApplicant;
    }

    public LiveData<List<LoanApplicantEMI>> getAllApplicantsEmi() {
        return mLoanApplicantEmi;
    }

    public void insertLoanApplicant(LoanApplicant loanApplicant) {
        mApplicantRepository.insertLoanApplicant(loanApplicant);
    }

    public void insertAllLoanApplicant(List<LoanApplicant> loanApplicantList) {
        mApplicantRepository.insertAllLoanApplicant(loanApplicantList);
    }

    public void insertLoanApplicantEmi(LoanApplicantEMI loanApplicantEMI) {
        mApplicantEmiRepository.insertLoanApplicantEmi(loanApplicantEMI);
    }

    public void insertAllLoanApplicantEmi(List<LoanApplicantEMI> loanApplicantEmiList) {
        mApplicantEmiRepository.insertAllLoanApplicantEmi(loanApplicantEmiList);
    }

    public void searchApplicant(String searchQuery, DatabaseListener.TaskCompleteListener listener) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                mSearchLoanApplicant = mApplicantRepository.searchApplicant(searchQuery);
                new Handler(Looper.getMainLooper()).post(() -> listener.onSuccess(mSearchLoanApplicant));
            }
        });
        thread.start();
    }
}
