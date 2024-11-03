package com.dkglabs.remote.interfaces;

import com.dkglabs.model.response.BaseResponse;

/**
 * Created by Himanshu Srivastava on 14,April,2023
 * Project Steel Sail
 */
public interface ResponseListener {

    void onResponse(int requestCode, BaseResponse response);

    void onValidationFailure(int requestCode, int errorTypeCode, String message);

    void onFailure(int requestCode, Throwable t);

    void commonCall(int requestCode);

}

