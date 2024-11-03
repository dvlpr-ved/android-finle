package com.dkglabs.remote.manager;

import com.dkglabs.model.request.PaymentOrderRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.PaymentOrderResponse;
import com.dkglabs.model.response.PaymentResponse;
import com.dkglabs.remote.interfaces.ApiServiceInterface;
import com.dkglabs.remote.interfaces.ResponseListener;

import retrofit2.Call;

/**
 * Created by Himanshu Srivastava on 19,September,2023
 * Project e_savari
 */
public class PaymentManager extends BaseManager {
    public static void createOrder(int requestCode, PaymentOrderRequest request, ResponseListener listener) {
        Call<BaseResponse<PaymentOrderResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).createOrder(request);
        callApi(requestCode, call, listener);
    }

    public static void status(int requestCode, String orderId, ResponseListener listener) {
        Call<BaseResponse<PaymentResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).status(orderId);
        callApi(requestCode, call, listener);
    }
}
