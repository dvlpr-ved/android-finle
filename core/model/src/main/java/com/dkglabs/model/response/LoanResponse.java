package com.dkglabs.model.response;

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

import java.io.Serializable;

import lombok.Data;

/**
 * Created by Himanshu Srivastava on 16,June,2023
 * Project e_savari
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanResponse implements Serializable {

    @JsonProperty("approvalDetails")
    private ApprovalDetails approvalDetails;
    @JsonProperty("addressDetails")
    private AddressDetails addressDetails;
    @JsonProperty("applicationCompletionStatus")
    private String applicationCompletionStatus;
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
    @JsonProperty("loanStatus")
    private String loanStatus;
    @JsonProperty("personalDetails")
    private PersonalDetails personalDetails;
    @JsonProperty("psyTestSatuas")
    private String psyTestSatuas;
    @JsonProperty("psyTestResultSatuas")
    private String psyTestResultSatuas;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("partnerId")
    private String partnerId;
    @JsonProperty("nbfcId")
    private String nbfcId;
    @JsonProperty("subscriptionFlag")
    private Boolean subscriptionFlag;

}
