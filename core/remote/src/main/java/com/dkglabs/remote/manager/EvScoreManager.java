package com.dkglabs.remote.manager;

import com.dkglabs.model.request.UserEvScoreParams;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.EvScoreResponse;
import com.dkglabs.remote.interfaces.ApiServiceInterface;
import com.dkglabs.remote.interfaces.ResponseListener;

import retrofit2.Call;

/**
 * Created by Himanshu Srivastava on 15,June,2023
 * Project e_savari
 */
public class EvScoreManager extends BaseManager {

    public static void getEvScore(int requestCode, String userId, String loanId, ResponseListener listener) {
        Call<BaseResponse<EvScoreResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getEvScore(userId, loanId);
        callApi(requestCode, call, listener);
    }

    public static void saveEVScoreParams(int requestCode, UserEvScoreParams evScoreParams, ResponseListener listener) {
        Call<BaseResponse> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).saveEVScoreParams(evScoreParams);
        callApi(requestCode, call, listener);
    }

    public static void calculateEvScore(int requestCode, String userId, String loanId, ResponseListener listener) {
        Call<BaseResponse<EvScoreResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).calculateEvScore(userId, loanId);
        callApi(requestCode, call, listener);
    }

}
