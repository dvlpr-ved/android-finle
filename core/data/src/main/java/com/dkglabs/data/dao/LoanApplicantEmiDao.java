package com.dkglabs.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dkglabs.data.entity.LoanApplicantEMI;

import java.util.List;

@Dao
public interface LoanApplicantEmiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LoanApplicantEMI loanApplicantEMI);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LoanApplicantEMI> loanApplicantEMIs);

    @Query("SELECT * FROM LoanApplicantEMI")
    LiveData<List<LoanApplicantEMI>> fetchAll();

    @Query("SELECT * FROM LoanApplicantEMI WHERE loanId = :loanID")
    LoanApplicantEMI fetchApplicantEmiByLoanId(String loanID);

    @Query("SELECT * FROM LoanApplicantEMI WHERE loanEmiId =:loanEmiId")
    LoanApplicantEMI fetchApplicantEmiByEmiId(String loanEmiId);

}
