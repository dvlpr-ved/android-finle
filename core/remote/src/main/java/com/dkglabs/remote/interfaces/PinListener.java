package com.dkglabs.remote.interfaces;

import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.PostalPinResponse;

/**
 * Created by Himanshu Srivastava on 19,June,2023
 * Project e_savari
 */
public interface PinListener {

    void onResponse(int requestCode, PostalPinResponse response);

    void onFailure(int requestCode, Throwable t);

}
