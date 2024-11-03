package com.dkglabs.base.manager;

import com.dkglabs.base.utils.DataUtil;
import com.dkglabs.model.response.AadhaarOtpResponse;
import com.dkglabs.model.response.AadhaarResponse;
import com.dkglabs.model.response.LoanResponse;

/**
 * Created by Himanshu Srivastava on 05,July,2023
 * Project e_savari
 */
public class LoanPersistentManager extends BasePersistentManager {

    public static String getLoanId() {
        return (String) readFromPreference("loan_id", String.class, "");
    }

    public static void setLoanId(String loanId) {
        writeToPreference("loan_id", loanId);
    }

    public static LoanResponse getLoanResponse() {
        String respString = (String) readFromPreference("loan_response", String.class, null);
        LoanResponse response = DataUtil.getObjectFromJson(respString, LoanResponse.class);
        return response;
    }

    public static void setLoanResponse(LoanResponse loanResponse) {
        String respString = null;
        if (loanResponse != null) {
            respString = DataUtil.getStringFromObj(loanResponse);
        }
        writeToPreference("loan_response", respString);
    }

    public static void removeLoanResponse() {
        removeKey("loan_response");
    }

    public static AadhaarResponse getAadhaarResponse() {
        String respString = (String) readFromPreference("aadhaar_response", String.class, null);
        AadhaarResponse response = DataUtil.getObjectFromJson(respString, AadhaarResponse.class);
        return response;
    }

    public static void setAadhaarResponse(AadhaarResponse aadhaarResponse) {
        String respString = null;
        if (aadhaarResponse != null) {
            respString = DataUtil.getStringFromObj(aadhaarResponse);
        }
        writeToPreference("aadhaar_response", respString);
    }

    public static AadhaarOtpResponse getAadhaarOtpResponse() {
        String respString = (String) readFromPreference("aadhaar_otp_response", String.class, null);
        AadhaarOtpResponse response = DataUtil.getObjectFromJson(respString, AadhaarOtpResponse.class);
        return response;
    }

    public static void setAadhaarOtpResponse(AadhaarOtpResponse aadhaarResponse) {
        String respString = null;
        if (aadhaarResponse != null) {
            respString = DataUtil.getStringFromObj(aadhaarResponse);
        }
        writeToPreference("aadhaar_otp_response", respString);
    }

    public static void removeAadhaarResponse() {
        removeKey("aadhaar_response");
    }

    public static void removeAadhaarOtpResponse() {
        removeKey("aadhaar_otp_response");
    }

}
