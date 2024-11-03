package com.dkglabs.remote.interfaces;


import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.request.DocumentRequest;
import com.dkglabs.model.request.LoanRequest;
import com.dkglabs.model.request.LoginRequest;
import com.dkglabs.model.request.PaymentOrderRequest;
import com.dkglabs.model.request.PostSaveLoanRequest;
import com.dkglabs.model.request.PreSendRequest;
import com.dkglabs.model.request.PsyAnswerListRequest;
import com.dkglabs.model.request.SaveLoanFulfillment;
import com.dkglabs.model.request.SaveLoanPaymentDetailsRequest;
import com.dkglabs.model.request.UpdatePasswordRequest;
import com.dkglabs.model.request.UpdateUserRequest;
import com.dkglabs.model.request.UserEvScoreParams;
import com.dkglabs.model.request.UserRegisterRequest;
import com.dkglabs.model.response.AadhaarOtpResponse;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.CollectionDetailsResponse;
import com.dkglabs.model.response.DlResponse;
import com.dkglabs.model.response.ESignResponse;
import com.dkglabs.model.response.EvScoreResponse;
import com.dkglabs.model.response.GetDealerDetails;
import com.dkglabs.model.response.LoanDataResponse;
import com.dkglabs.model.response.LoanEmiDetailResponse;
import com.dkglabs.model.response.LoanProcessingDocument;
import com.dkglabs.model.response.LoanResponse;
import com.dkglabs.model.response.LoginResponse;
import com.dkglabs.model.response.NbfcDetails;
import com.dkglabs.model.response.PanResponse;
import com.dkglabs.model.response.PaymentOrderResponse;
import com.dkglabs.model.response.PaymentResponse;
import com.dkglabs.model.response.PostalPinResponse;
import com.dkglabs.model.response.PrePostSaveResponse;
import com.dkglabs.model.response.PsyResultResponse;
import com.dkglabs.model.response.QuestionAnswerListResponse;
import com.dkglabs.model.response.RequireDocDetailResponse;
import com.dkglabs.model.response.SaveLoanPaymentDetailsResponse;
import com.dkglabs.model.response.UserResponse;
import com.dkglabs.remote.utils.AppUrlConstants;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Himanshu Srivastava on 4/22/2023.
 */
public interface ApiServiceInterface {

    @POST(AppUrlConstants.LOGIN)
    Call<BaseResponse<LoginResponse>> userLogin(@Body LoginRequest request);

    @POST(AppUrlConstants.REGISTER)
    Call<BaseResponse<UserResponse>> userRegister(@Body UserRegisterRequest userRegisterRequest);

    @POST(AppUrlConstants.USER_DETAILS)
    Call<BaseResponse<UserResponse>> userDetails(@Body LoginRequest request);

    @POST(AppUrlConstants.UPDATE_USER)
    Call<BaseResponse<UserResponse>> updateUser(@Body UpdateUserRequest request);

    @POST(AppUrlConstants.UPDATE_PASSWORD)
    Call<BaseResponse<UserResponse>> updatePassword(@Body UpdatePasswordRequest request);

    @POST(AppUrlConstants.REMOVE_PROFILE_IMAGE)
    Call<BaseResponse> removeProfileImage(@Query("userId") String userId);

    @Multipart
    @POST(AppUrlConstants.UPLOAD_PROFILE_IMAGE)
    Call<BaseResponse<String>> uploadProfileImage(@Query("userId") String userId, @Part MultipartBody.Part file);

    @GET(AppUrlConstants.GET_PSY_TEST)
    Call<BaseResponse<QuestionAnswerListResponse>> getPsychometricQuestion();

    @GET(AppUrlConstants.GET_PSY_TEST_RESULT)
    Call<BaseResponse<PsyResultResponse>> getPsyTestResult(@Query("userId") String userId);

    @POST(AppUrlConstants.SAVE_PSY_TEST)
    Call<BaseResponse<PsyResultResponse>> savePsychometricQuestion(@Body PsyAnswerListRequest request);

    @PUT(AppUrlConstants.SAVE_PERSONAL_DETAILS)
    Call<BaseResponse<LoanResponse>> savePersonalDetails(@Body LoanRequest request);

    @POST(AppUrlConstants.SAVE_LOAN_DETAIL)
    Call<BaseResponse> saveLoanDetails(@Body LoanRequest request);

    @POST(AppUrlConstants.SAVE_INCOME_DETAILS)
    Call<BaseResponse<LoanResponse>> saveIncomeDetails(@Body LoanRequest request);

    @POST(AppUrlConstants.SAVE_DOCUMENT_DETAILS)
    Call<BaseResponse<LoanResponse>> saveDocumentDetails(@Body LoanRequest request);

    @POST(AppUrlConstants.SAVE_COLLATERAL_DETAILS)
    Call<BaseResponse> saveCollateralDetails(@Body LoanRequest request);

    @POST(AppUrlConstants.SAVE_BANK_DETAILS)
    Call<BaseResponse<LoanResponse>> saveBankDetails(@Body LoanRequest request);

    @POST(AppUrlConstants.SAVE_ADDRESS_DETAILS)
    Call<BaseResponse> saveAddressDetails(@Body LoanRequest request);

    @POST(AppUrlConstants.SAVE_CONTACT_DETAILS)
    Call<BaseResponse> saveContactDetails(@Body LoanRequest request);

    @POST(AppUrlConstants.VALIDATE_PAN)
    Call<BaseResponse<PanResponse>> validatePAN(@Body DocumentRequest request);

    @POST(AppUrlConstants.VALIDATE_AADHAAR)
    Call<BaseResponse<AadhaarOtpResponse>> validateAadhaar(@Body DocumentRequest request);

    @POST(AppUrlConstants.VALIDATE_LICENCE)
    Call<BaseResponse<DlResponse>> validateLicence(@Body DocumentRequest request);

    @GET(AppUrlConstants.GET_AADHAAR_OKYC_OTP)
    Call<BaseResponse<AadhaarOtpResponse>> getAadhaarOkycOtp(@Query("aadhaarNumber") String aadhaarNumber);

    @POST(AppUrlConstants.APPLY_LOAN)
    Call<BaseResponse> applyLoan(@Query("userId") String userId, @Query("loanId") String loanId);

    @GET(AppUrlConstants.LOAN_DETAILS)
    Call<BaseResponse<LoanResponse>> loanDetails(@Query("userId") String userId, @Query("loanId") String loanId);

    @GET(AppUrlConstants.LOAN_DOCUMENTS)
    Call<BaseResponse<RequireDocDetailResponse>> loanDocuments(@Query("loanId") String loanId);

    @GET(AppUrlConstants.DEALER_LONE_DOCUMENTS)
    Call<BaseResponse<List<DocDetails>>> dealerDocuments(@Query("userId") String userId);

    @GET(AppUrlConstants.ALL_LOAN_APPLICANTS)
    Call<BaseResponse<List<LoanResponse>>> getAllLoanApplicants();

    @GET(AppUrlConstants.ALL_NBFC_APPROVED_APPLICANTS)
    Call<BaseResponse<List<LoanResponse>>> allNbfcApprovedLoanApplicants();

    @GET(AppUrlConstants.ALL_NBFC_APPROVED_APPLICANTS)
    Call<BaseResponse<List<LoanResponse>>> allNbfcApprovedLoanApplicantsWithId(@Query("partnerId") String partnerId);

    @GET(AppUrlConstants.GET_LOAN_PROCESSING_DOCUMENTS)
    Call<BaseResponse<LoanProcessingDocument>> getLoanProcessingDocuments(@Query("loanId") String loanId);

    @GET(AppUrlConstants.GET_ESIGNED_LOAN_DOCUMENT)
    Call<BaseResponse<ESignResponse>> getEsignedLoanDocument(@Query("loanId") String loanId);

    @POST(AppUrlConstants.E_SIGN_LOAN_DOCUMENT)
    Call<BaseResponse<ESignResponse>> esignLoanDocument(@Query("loanId") String loanId);

    @Multipart
    @POST(AppUrlConstants.UPLOAD_DOCUMENT)
    Call<BaseResponse<String>> uploadDocument(@Query("userId") String userId, @Query("vrfCode") String vrfCode, @Query("vrfsCode") String vrfsCode, @Part MultipartBody.Part file);

    @Multipart
    @POST(AppUrlConstants.UPLOAD_DOCUMENT)
    Call<BaseResponse<String>> uploadDocumentDealerApplication(@Query("userId") String userId, @Query("vrfCode") String vrfCode, @Query("vrfsCode") String vrfsCode, @Part MultipartBody.Part file);

    @GET(AppUrlConstants.PIN_CODE + "/{pincode}")
    Call<List<PostalPinResponse>> getPostalPinDetails(@Path(value = "pincode") String pincode);

    @GET(AppUrlConstants.GET_EV_SCORE)
    Call<BaseResponse<EvScoreResponse>> getEvScore(@Query("userId") String userId, @Query("loanId") String loanId);

    @POST(AppUrlConstants.SAVE_EV_SCORE_PARAMS)
    Call<BaseResponse> saveEVScoreParams(@Body UserEvScoreParams evScoreParams);

    @GET(AppUrlConstants.CALCULATE_EV_SCORE)
    Call<BaseResponse<EvScoreResponse>> calculateEvScore(@Query("userId") String userId, @Query("loanId") String loanId);

    @GET(AppUrlConstants.GET_COLLECTION_DETAILS)
    Call<BaseResponse<CollectionDetailsResponse>> getCollectionDetails(@Query("partnerId") String partnerId);

    @GET(AppUrlConstants.GET_COLLECTION_DETAILS)
    Call<BaseResponse<CollectionDetailsResponse>> getCollectionDetailsAll();

    @GET(AppUrlConstants.GET_LOAN_PAYMENT_DETAILS)
    Call<BaseResponse<LoanDataResponse>> getLoanPaymentDetails(@Query("userId") String userId, @Query("loanId") String loanId);

    @GET(AppUrlConstants.GET_LOAN_EMI_DETAIL)
    Call<BaseResponse<EvScoreResponse>> getLoanEmiDetail(@Query("loanEmiId") String loanEmiId);

    @GET(AppUrlConstants.GET_LOAN_EMI_DETAILS)
    Call<BaseResponse<List<LoanEmiDetailResponse>>> getLoanEmiDetails(@Query("loanId") String loanId);

    @POST(AppUrlConstants.PAY_CREATE_ORDER)
    Call<BaseResponse<PaymentOrderResponse>> createOrder(@Body PaymentOrderRequest orderId);

    @GET(AppUrlConstants.PAY_STATUS)
    Call<BaseResponse<PaymentResponse>> status(@Query("orderId") String orderId);

    @POST(AppUrlConstants.PRE_SAVE_DISBURSEMENT)
    Call<BaseResponse<PrePostSaveResponse>> savePreDisbursement(@Body PreSendRequest body);

    @POST(AppUrlConstants.POST_SAVE_DISBURSEMENT)
    Call<BaseResponse<PrePostSaveResponse>> savePostDisbursement(@Body PostSaveLoanRequest body);

    @POST(AppUrlConstants.SAVE_LOAN_FULFILMENT)
    Call<BaseResponse<PrePostSaveResponse>> saveLoanFulFilment(@Body SaveLoanFulfillment body);

    @POST(AppUrlConstants.SAVE_LOAN_PAYMENT_DETAIL)
    Call<BaseResponse<SaveLoanPaymentDetailsResponse>> saveLoanPaymentDetails(@Body SaveLoanPaymentDetailsRequest body);

    @GET(AppUrlConstants.GET_DEALER_DETAIL)
    Call<BaseResponse<GetDealerDetails>> getDealerDetail(@Query("dealerId") String dealerId);

    @GET(AppUrlConstants.GET_NBFC_DETAILS)
    Call<BaseResponse<List<NbfcDetails>>> getNbfcDetails();
}