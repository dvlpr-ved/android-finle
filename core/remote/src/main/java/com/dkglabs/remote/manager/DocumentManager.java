package com.dkglabs.remote.manager;

import android.content.Context;
import android.net.Uri;

import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.model.applyloan.DocDetails;
import com.dkglabs.model.request.DocumentRequest;
import com.dkglabs.model.request.PostSaveLoanRequest;
import com.dkglabs.model.request.PreSendRequest;
import com.dkglabs.model.request.SaveLoanFulfillment;
import com.dkglabs.model.request.SaveLoanPaymentDetailsRequest;
import com.dkglabs.model.response.AadhaarOtpResponse;
import com.dkglabs.model.response.AadhaarResponse;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.DlResponse;
import com.dkglabs.model.response.GetDealerDetails;
import com.dkglabs.model.response.NbfcDetails;
import com.dkglabs.model.response.PanResponse;
import com.dkglabs.model.response.PrePostSaveResponse;
import com.dkglabs.model.response.SaveLoanPaymentDetailsResponse;
import com.dkglabs.remote.interfaces.ApiServiceInterface;
import com.dkglabs.remote.interfaces.ResponseListener;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Himanshu Srivastava on 30,June,2023
 * Project e_savari
 */
public class DocumentManager extends BaseManager {
    public static void validatePAN(int requestCode, DocumentRequest request, ResponseListener listener) {
        Call<BaseResponse<PanResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).validatePAN(request);
        callApi(requestCode, call, listener);
    }

    public static void validatePanAndAddDealerNbfcMaping(int requestCode, DocumentRequest request, ResponseListener listener) {
        Call<BaseResponse<PanResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).validatePAN(request);
        callApi(requestCode, call, listener);
    }

    public static void validateAadhaar(int requestCode, DocumentRequest request, ResponseListener listener) {
        Call<BaseResponse<AadhaarOtpResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).validateAadhaar(request);
        callApi(requestCode, call, listener);
    }

    public static void getAadhaarOkycOtp(int requestCode, String request, ResponseListener listener) {
        Call<BaseResponse<AadhaarOtpResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getAadhaarOkycOtp(request);
        callApi(requestCode, call, listener);
    }

    public static void validateLicence(int requestCode, DocumentRequest request, ResponseListener listener) {
        Call<BaseResponse<DlResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).validateLicence(request);
        callApi(requestCode, call, listener);
    }

    public static void uploadDocument(int requestCode, Context context, Uri uri, String userId, DocDetails request, ResponseListener listener) {
        File file = new File(uri.getPath());

        LogsManager.printLog("FILE_PATH", file.getAbsolutePath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", request.getVrfSName(), requestBody);
        Call<BaseResponse<String>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).uploadDocumentDealerApplication(userId, request.getVrfCode(), request.getVrfsCode(), filePart);
        callApi(requestCode, call, listener);
    }

    public static void uploadDocumentCustomerApplication(int requestCode, Context context, Uri uri, String userId, DocDetails request, ResponseListener listener) {
        File file = AppUtils.getFile(context, uri);

        LogsManager.printLog("FILE_PATH", file.getAbsolutePath());
        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", request.getVrfSName(), requestBody);
        Call<BaseResponse<String>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).uploadDocument(userId, request.getVrfCode(), request.getVrfsCode(), filePart);
        callApi(requestCode, call, listener);
    }

    public static void saveLoanPreDisbursemen(int requestCode, PreSendRequest body, ResponseListener listener) {
        Call<BaseResponse<PrePostSaveResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).savePreDisbursement(body);
        callApi(requestCode, call, listener);
    }

    public static void saveLoanPostDisbursemen(int requestCode, PostSaveLoanRequest body, ResponseListener listener) {
        Call<BaseResponse<PrePostSaveResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).savePostDisbursement(body);
        callApi(requestCode, call, listener);
    }

    public static void saveLoanFullfilment(int requestCode, SaveLoanFulfillment body, ResponseListener listener) {
        Call<BaseResponse<PrePostSaveResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveLoanFulFilment(body);
        callApi(requestCode, call, listener);
    }

    public static void saveLoanPaymentDetails(int requestCode, SaveLoanPaymentDetailsRequest body, ResponseListener listener) {
        Call<BaseResponse<SaveLoanPaymentDetailsResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveLoanPaymentDetails(body);
        callApi(requestCode, call, listener);
    }

    public static void getDealerDetail(int requestCode, String dealerId, ResponseListener listener) {
        Call<BaseResponse<GetDealerDetails>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getDealerDetail(dealerId);
        callApi(requestCode, call, listener);
    }

    public static void getNbfcDetails(int requestCode, ResponseListener listener) {
        Call<BaseResponse<List<NbfcDetails>>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getNbfcDetails();
        callApi(requestCode, call, listener);
    }
}
