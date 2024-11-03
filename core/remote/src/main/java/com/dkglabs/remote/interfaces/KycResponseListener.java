package com.dkglabs.remote.interfaces;

import java.util.List;

public interface KycResponseListener<T> {
    void onSuccess(List<T> data, Integer requestCode);
    void onError(String errorMessage,Integer requestCode);
    void onSuccess(T data,Integer requestCode);
}
