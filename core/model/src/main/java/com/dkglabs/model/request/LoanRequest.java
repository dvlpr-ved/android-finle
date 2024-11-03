package com.dkglabs.model.request;

import com.dkglabs.model.applyloan.AddressDetails;
import com.dkglabs.model.applyloan.ApprovalDetails;
import com.dkglabs.model.applyloan.BankDetails;
import com.dkglabs.model.applyloan.CollateralDetails;
import com.dkglabs.model.applyloan.ContactDetails;
import com.dkglabs.model.applyloan.DocumentDetails;
import com.dkglabs.model.applyloan.IncomeDetails;
import com.dkglabs.model.applyloan.KycDetails;
import com.dkglabs.model.applyloan.LoanDetail;
import com.dkglabs.model.applyloan.PersonalDetails;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Created by Himanshu Srivastava on 15,June,2023
 * Project e_savari
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "approvalDetails",
        "addressDetails",
        "bankDetails",
        "collateralDetails",
        "contactDetails",
        "documentDetails",
        "incomeDetails",
        "kycDetails",
        "loanDetail",
        "loanStageFlag",
        "loanId",
        "personalDetails",
        "psyResult",
        "userId"
})
public class LoanRequest {

    @JsonProperty("approvalDetails")
    private ApprovalDetails approvalDetails;
    @JsonProperty("addressDetails")
    private AddressDetails addressDetails;
    @JsonProperty("bankDetails")
    private BankDetails bankDetails;
    @JsonProperty("collateralDetails")
    private CollateralDetails collateralDetails;
    @JsonProperty("contactDetails")
    private ContactDetails contactDetails;
    @JsonProperty("documentDetails")
    private DocumentDetails documentDetails;
    @JsonProperty("incomeDetails")
    private IncomeDetails incomeDetails;
    @JsonProperty("kycDetails")
    private KycDetails kycDetails;
    @JsonProperty("loanDetail")
    private LoanDetail loanDetail;
    @JsonProperty("loanStageFlag")
    private String loanStageFlag;
    @JsonProperty("loanId")
    private String loanId;
    @JsonProperty("personalDetails")
    private PersonalDetails personalDetails;
    @JsonProperty("psyResult")
    private String psyResult;
    @JsonProperty("userId")
    private String userId;

    public ApprovalDetails getApprovalDetails() {
        return approvalDetails;
    }

    public void setApprovalDetails(ApprovalDetails approvalDetails) {
        this.approvalDetails = approvalDetails;
    }

    public AddressDetails getAddressDetails() {
        return addressDetails;
    }

    public void setAddressDetails(AddressDetails addressDetails) {
        this.addressDetails = addressDetails;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public CollateralDetails getCollateralDetails() {
        return collateralDetails;
    }

    public void setCollateralDetails(CollateralDetails collateralDetails) {
        this.collateralDetails = collateralDetails;
    }

    public ContactDetails getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public DocumentDetails getDocumentDetails() {
        return documentDetails;
    }

    public void setDocumentDetails(DocumentDetails documentDetails) {
        this.documentDetails = documentDetails;
    }

    public IncomeDetails getIncomeDetails() {
        return incomeDetails;
    }

    public void setIncomeDetails(IncomeDetails incomeDetails) {
        this.incomeDetails = incomeDetails;
    }

    public KycDetails getKycDetails() {
        return kycDetails;
    }

    public void setKycDetails(KycDetails kycDetails) {
        this.kycDetails = kycDetails;
    }

    public LoanDetail getLoanDetail() {
        return loanDetail;
    }

    public void setLoanDetail(LoanDetail loanDetail) {
        this.loanDetail = loanDetail;
    }

    public String getLoanStageFlag() {
        return loanStageFlag;
    }

    public void setLoanStageFlag(String loanStageFlag) {
        this.loanStageFlag = loanStageFlag;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public PersonalDetails getPersonalDetails() {
        return personalDetails;
    }

    public void setPersonalDetails(PersonalDetails personalDetails) {
        this.personalDetails = personalDetails;
    }

    public String getPsyResult() {
        return psyResult;
    }

    public void setPsyResult(String psyResult) {
        this.psyResult = psyResult;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}