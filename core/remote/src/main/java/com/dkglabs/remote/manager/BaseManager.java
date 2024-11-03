package com.dkglabs.remote.manager;

import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.ResponseData;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.utils.ServerConst;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu Srivastava on 3/24/2023.
 */
public class BaseManager {

    protected static void callApi(final int requestCode, Object call1, final ResponseListener listener) {
        Call<BaseResponse<ResponseData>> call = (Call<BaseResponse<ResponseData>>) call1;
        call.enqueue(new Callback<BaseResponse<ResponseData>>() {
            @Override
            public void onResponse(Call<BaseResponse<ResponseData>> call, Response<BaseResponse<ResponseData>> response) {
                LogsManager.printLog("LOGS : onResponse");
                validateResponse(requestCode, response, listener);
            }

            @Override
            public void onFailure(Call<BaseResponse<ResponseData>> call, Throwable t) {
                LogsManager.printLog("LOGS : onFailure - " + t.getMessage());
                listener.commonCall(requestCode);
                listener.onFailure(requestCode, t);
            }
        });
    }

    private static BaseResponse validateResponse(int requestCode, Response responseR, ResponseListener responseListener) {

        Response<BaseResponse> response = responseR;
        BaseResponse rtnResponse = null;

        if (responseListener != null)
            responseListener.commonCall(requestCode);

        if (response != null && response.isSuccessful() && response.body() != null && responseListener != null) {
            BaseResponse baseResponse = response.body();
            if (baseResponse.getResponseStatus().getStatusCode() == ServerConst.STATUS_OK) {
                LogsManager.printLog("LOGGER0: " + baseResponse.getResponseData());
                rtnResponse = baseResponse;
                responseListener.onResponse(requestCode, baseResponse);
            } else {
                LogsManager.printLog("Error in API Call: " + baseResponse.getResponseStatus().getMessage());
                if (responseListener != null)
                    responseListener.onFailure(requestCode, null);
            }
        } else {
            LogsManager.printLog("LOGGER2: " + response.toString());
            //responseListener.onFailure(requestCode, null);
            ResponseBody responseBody = response.errorBody();
            if (responseBody != null && responseListener != null) {
                responseListener.onValidationFailure(requestCode, response.code(), getErrorBodyMessage(responseBody));
            } else {
                LogsManager.printLog("LOGGER3: " + response.toString());
                if (responseListener != null)
                    responseListener.onFailure(requestCode, null);
            }
        }
        return rtnResponse;
    }

    private static String getErrorBodyMessage(ResponseBody errorBody) {
        String valuesMsg = "There is some problem. Please try again later.";
        try {
            String errorJson = errorBody.string();
            JSONObject object = new JSONObject(errorJson);
            JSONObject object2 = object.getJSONObject("responseStatus");
            valuesMsg = object2.optString("message", valuesMsg);
        } catch (Exception e) {
            LogsManager.printLog(e);
        }
        return valuesMsg;
    }
}
