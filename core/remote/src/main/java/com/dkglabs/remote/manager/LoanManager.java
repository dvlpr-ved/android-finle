package com.dkglabs.remote.manager;

import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.request.LoanRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.ESignResponse;
import com.dkglabs.model.response.LoanDataResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;
import com.dkglabs.model.response.LoanProcessingDocument;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.model.response.RequireDocDetailResponse;
import com.dkglabs.remote.interfaces.ApiServiceInterface;
import com.dkglabs.remote.interfaces.ResponseListener;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Himanshu Srivastava on 15,June,2023
 * Project e_savari
 */
public class LoanManager extends BaseManager {
    public static void applyLoan(int requestCode, String userId, String loanId, ResponseListener listener) {
        Call<BaseResponse> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).applyLoan(userId, loanId);
        callApi(requestCode, call, listener);
    }

    public static void loanDetails(int requestCode, String userId, String loanId, ResponseListener listener) {
        Call<BaseResponse<LoanResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).loanDetails(userId, loanId);
        callApi(requestCode, call, listener);
    }

    public static void savePersonalDetails(int requestCode, LoanRequest request, ResponseListener listener) {
        Call<BaseResponse<LoanResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).savePersonalDetails(request);
        callApi(requestCode, call, listener);
    }

    public static void saveLoanDetails(int requestCode, LoanRequest request, ResponseListener listener) {
        Call<BaseResponse> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveLoanDetails(request);
        callApi(requestCode, call, listener);
    }

    public static void saveIncomeDetails(int requestCode, LoanRequest request, ResponseListener listener) {
        Call<BaseResponse<LoanResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveIncomeDetails(request);
        callApi(requestCode, call, listener);
    }

    public static void saveDocumentDetails(int requestCode, LoanRequest request, ResponseListener listener) {
        Call<BaseResponse<LoanResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveDocumentDetails(request);
        callApi(requestCode, call, listener);
    }

    public static void saveBankDetails(int requestCode, LoanRequest request, ResponseListener listener) {
        Call<BaseResponse<LoanResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveBankDetails(request);
        callApi(requestCode, call, listener);
    }

    public static void saveCollateralDetails(int requestCode, LoanRequest request, ResponseListener listener) {
        Call<BaseResponse> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveCollateralDetails(request);
        callApi(requestCode, call, listener);
    }

    public static void saveAddressDetails(int requestCode, LoanRequest request, ResponseListener listener) {
        Call<BaseResponse> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveAddressDetails(request);
        callApi(requestCode, call, listener);
    }

    public static void saveContactDetails(int requestCode, LoanRequest request, ResponseListener listener) {
        Call<BaseResponse> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveContactDetails(request);
        callApi(requestCode, call, listener);
    }

    public static void loanDocuments(int requestCode, String loanId, ResponseListener listener) {
        Call<BaseResponse<RequireDocDetailResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).loanDocuments(loanId);
        callApi(requestCode, call, listener);
    }

    public static void dealerDocuments(int requestCode, String userId, ResponseListener listener) {
        Call<BaseResponse<List<DocDetails>>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).dealerDocuments(userId);
        callApi(requestCode, call, listener);
    }

    public static void getAllLoanApplicants(int requestCode, ResponseListener listener) {
        Call<BaseResponse<List<LoanResponse>>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getAllLoanApplicants();
        callApi(requestCode, call, listener);
    }

    public static void allNbfcApprovedLoanApplicants(int requestCode, ResponseListener listener) {
        Call<BaseResponse<List<LoanResponse>>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).allNbfcApprovedLoanApplicants();
        callApi(requestCode, call, listener);
    }

    public static void allNbfcApprovedLoanApplicantsWithId(int requestCode, String partnerId, ResponseListener listener) {
        Call<BaseResponse<List<LoanResponse>>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).allNbfcApprovedLoanApplicantsWithId(partnerId);
        callApi(requestCode, call, listener);
    }

    public static void getLoanEmiDetails(int requestCode, String loanId, ResponseListener listener) {
        Call<BaseResponse<List<LoanEmiDetailResponse>>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getLoanEmiDetails(loanId);
        callApi(requestCode, call, listener);
    }

    public static void getLoanProcessingDocuments(int requestCode, String loanId, ResponseListener listener) {
        Call<BaseResponse<LoanProcessingDocument>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getLoanProcessingDocuments(loanId);
        callApi(requestCode, call, listener);
    }

    public static void getEsignedLoanDocument(int requestCode, String loanId, ResponseListener listener) {
        Call<BaseResponse<ESignResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getEsignedLoanDocument(loanId);
        callApi(requestCode, call, listener);
    }

    public static void esignLoanDocument(int requestCode, String loanId, ResponseListener listener) {
        Call<BaseResponse<ESignResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).esignLoanDocument(loanId);
        callApi(requestCode, call, listener);
    }

    public static void getLoanPaymentDetails(int requestCode, String userId, String loanId, ResponseListener listener) {
        Call<BaseResponse<LoanDataResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getLoanPaymentDetails(userId, loanId);
        callApi(requestCode, call, listener);
    }

}
