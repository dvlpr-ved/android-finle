package com.dkglabs.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.dkglabs.data.entity.LoanApplicant;

import java.util.List;

@Dao
public interface LoanApplicantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LoanApplicant loanApplicant);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<LoanApplicant> loanApplicants);

    @Query("SELECT * FROM LoanApplicant")
    LiveData<List<LoanApplicant>> fetchAll();

    @Query("SELECT * FROM LoanApplicant WHERE loanId = :loanID")
    LoanApplicant fetchApplicantByLoanId(String loanID);

    @Query("SELECT * FROM LoanApplicant WHERE name like :name")
    LoanApplicant fetchApplicantByName(String name);

    @Query("SELECT * FROM LoanApplicant WHERE name LIKE '%' || :searchQuery || '%' OR loanId LIKE '%' || :searchQuery || '%' OR phone LIKE '%' || :searchQuery || '%'")
    List<LoanApplicant> searchApplicant(String searchQuery);


}
