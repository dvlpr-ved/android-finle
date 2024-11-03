package com.dkglabs.dealer_collection.model;

import com.dkglabs.model.response.LoanApplicantResponse;

import java.io.Serializable;
import java.util.Stack;

public class EmiPayModel implements Serializable {
   static LoanApplicantResponse loanApplicantResponse;

   public static LoanApplicantResponse getLoanApplicantResponse() {
      return loanApplicantResponse;
   }

   public static void setLoanApplicantResponse(LoanApplicantResponse loanApplicantResponse) {
      EmiPayModel.loanApplicantResponse = loanApplicantResponse;
   }

   public EmiPayModel() {
   }
}
