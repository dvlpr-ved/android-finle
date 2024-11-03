package com.dkglabs.remote.manager;

import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.CollectionDetailsResponse;
import com.dkglabs.model.response.LoanDataResponse;
import com.dkglabs.remote.interfaces.ApiServiceInterface;
import com.dkglabs.remote.interfaces.ResponseListener;

import retrofit2.Call;

/**
 * Created by Himanshu Srivastava on 23,August,2023
 * Project e_savari
 */
public class CollectionManager extends BaseManager {
    public static void getLoanPaymentDetails(int requestCode, String userId, String loanId, ResponseListener listener) {
        Call<BaseResponse<LoanDataResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getLoanPaymentDetails(userId, loanId);
        callApi(requestCode, call, listener);
    }

    public static void getCollectionDetails(int requestCode, String partnerId, ResponseListener listener) {
        Call<BaseResponse<CollectionDetailsResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getCollectionDetails(partnerId);
        callApi(requestCode, call, listener);
    }

    public static void getCollectionDetailsAll(int requestCode, ResponseListener listener) {
        Call<BaseResponse<CollectionDetailsResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getCollectionDetailsAll();
        callApi(requestCode, call, listener);
    }
}
