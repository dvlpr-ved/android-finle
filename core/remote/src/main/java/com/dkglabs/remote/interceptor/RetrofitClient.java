package com.dkglabs.remote.interceptor;

import com.dkglabs.remote.utils.KycUtilManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
    private static Retrofit retrofit = null;

    public static Retrofit getRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(KycUtilManager.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit;
    }

    public static void setRetrofit(Retrofit retrofit) {
        RetrofitClient.retrofit = retrofit;
    }

    // HeaderInterceptor class to add headers to each request
    private static class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();
            // Add headers to the request
            Request.Builder requestBuilder = originalRequest.newBuilder()
                    .header("Accept", "*/*")
                    .header("Accept-Language", "en-US,en;q=0.8")
                    .header("Content-Type", "application/json");
            Request newRequest = requestBuilder.build();
            return chain.proceed(newRequest);
        }
    }
}
