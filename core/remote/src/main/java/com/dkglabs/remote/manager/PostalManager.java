package com.dkglabs.remote.manager;

import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.model.response.PostalPinResponse;
import com.dkglabs.remote.interfaces.ApiServiceInterface;
import com.dkglabs.remote.interfaces.PinListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Himanshu Srivastava on 3/24/2023.
 */
public class PostalManager {

    public static void getPostalPinDetails(int requestCode, String pin, PinListener listener) {
        PostalPinResponse postalPinResponse = null;
        Call<List<PostalPinResponse>> call = PostalApiManager.createService(ApiServiceInterface.class).getPostalPinDetails(pin);
        call.enqueue(new Callback<List<PostalPinResponse>>() {
            @Override
            public void onResponse(Call<List<PostalPinResponse>> call, Response<List<PostalPinResponse>> response) {
                LogsManager.printLog("LOGS : onResponse");
                validateResponse(requestCode, response, listener);
            }

            @Override
            public void onFailure(Call<List<PostalPinResponse>> call, Throwable t) {
                LogsManager.printLog("LOGS : onFailure - " + t.getMessage());
                listener.onFailure(requestCode, t);
            }
        });
    }

    private static void validateResponse(int requestCode, Response responseR, PinListener listener) {
        List<PostalPinResponse> responseList = (List<PostalPinResponse>) responseR.body();
        if (responseList.size() > 0 && !responseList.isEmpty()) {
            PostalPinResponse response = responseList.get(0);
            if (response != null && response != null && response.getStatus().equals("Success")) {
                listener.onResponse(requestCode, response);
            } else {
                listener.onFailure(requestCode, new Throwable("Failed"));
            }
        }
    }
}
