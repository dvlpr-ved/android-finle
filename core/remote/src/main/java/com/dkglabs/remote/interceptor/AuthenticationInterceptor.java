package com.dkglabs.remote.interceptor;

import java.io.IOException;
import java.util.TimeZone;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Himanshu Srivastava on 9/13/2022.
 */
public class AuthenticationInterceptor implements Interceptor {

    private String authToken = "";

    public AuthenticationInterceptor(String authToken) {
        this.authToken = authToken;
    }

    public AuthenticationInterceptor() {

    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();

        Request.Builder builder = originalRequest.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type", "application/json")
                .header("time-zone", TimeZone.getDefault().getID())
                .header("Authorization", authToken);
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }


}
