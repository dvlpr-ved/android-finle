package com.dkglabs.remote.manager;

import com.dkglabs.model.request.PsyAnswerListRequest;
import com.dkglabs.model.request.PsyAnswerRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.PsyResultResponse;
import com.dkglabs.model.response.QuestionAnswerListResponse;
import com.dkglabs.remote.interfaces.ApiServiceInterface;
import com.dkglabs.remote.interfaces.ResponseListener;

import java.util.List;

import retrofit2.Call;

/**
 * Created by Himanshu Srivastava on 9/13/2022.
 */
public class PsychometricManager extends BaseManager {

    public static void getPsychometricQuestion(int requestCode, ResponseListener listener) {
        Call<BaseResponse<QuestionAnswerListResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getPsychometricQuestion();
        callApi(requestCode, call, listener);
    }

    public static void savePsychometricQuestion(int requestCode, PsyAnswerListRequest request, ResponseListener listener) {
        Call<BaseResponse<PsyResultResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).savePsychometricQuestion(request);
        callApi(requestCode, call, listener);
    }

    public static void getPsyTestResult(int requestCode, String userId, ResponseListener listener) {
        Call<BaseResponse<PsyResultResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).getPsyTestResult(userId);
        callApi(requestCode, call, listener);
    }


}
