package com.dkglabs.loan_application.model;

import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.response.LoanApplicantResponse;

import java.io.Serializable;
import java.util.List;

public class ApplicantDetail implements Serializable {
    private String documents;
    private LoanApplicantResponse loanApplicantResponse;
    private List<DocDetails> docDetailsList;
    String userId;
    String fulFilVrfs;

    public String getFulFilVrfs() {
        return fulFilVrfs;
    }

    public void setFulFilVrfs(String fulFilVrfs) {
        this.fulFilVrfs = fulFilVrfs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ApplicantDetail(){

    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public LoanApplicantResponse getLoanApplicantResponse() {
        return loanApplicantResponse;
    }

    public void setLoanApplicantResponse(LoanApplicantResponse loanApplicantResponse) {
        this.loanApplicantResponse = loanApplicantResponse;
    }

    public List<DocDetails> getDocDetailsList() {
        return docDetailsList;
    }

    public void setDocDetailsList(List<DocDetails> docDetailsList) {
        this.docDetailsList = docDetailsList;
    }
}

