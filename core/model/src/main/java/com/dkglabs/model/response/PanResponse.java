package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Himanshu Srivastava on 30,June,2023
 * Project e_savari
 */
public class PanResponse implements Serializable {

    @JsonProperty("documentType")
    private String documentType;
    @JsonProperty("dob")
    private String dob;
    @JsonProperty("doi")
    private String doi;
    @JsonProperty("father")
    private String father;
    @JsonProperty("name")
    private String name;
    @JsonProperty("panCard")
    private String panCard;
    @JsonProperty("loanId")
    private String loanId;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPanCard() {
        return panCard;
    }

    public void setPanCard(String panCard) {
        this.panCard = panCard;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }
}
