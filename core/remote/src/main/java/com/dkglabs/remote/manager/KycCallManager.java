package com.dkglabs.remote.manager;

import android.content.Context;
import android.net.Uri;

import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.model.request.KycLoginRequest;
import com.dkglabs.model.request.KycResultRequestModel;
import com.dkglabs.model.request.KycVideoRequestModel;
import com.dkglabs.model.response.ImageUrlModel;
import com.dkglabs.model.response.KycAuthModel;
import com.dkglabs.model.response.KycResultModel;
import com.dkglabs.model.response.KycVideoModel;

import com.dkglabs.remote.interceptor.RetrofitClient;
import com.dkglabs.remote.interfaces.KycApiServices;
import com.dkglabs.remote.interfaces.KycResponseListener;
import com.dkglabs.remote.utils.KycUtilManager;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

public class KycCallManager extends ApiManagerKyc {
    public static void getAuthToken(KycResponseListener listener,Integer requestCode){
        Call<KycAuthModel> call= RetrofitClient.getRetrofit().create(KycApiServices.class).getTokenAndId(new KycLoginRequest());
        getKycData(listener,call,requestCode);
    }

    public static void getImageUrl(KycResponseListener listener, Integer requestCode, Context context, Uri uri){
        File file = new File(uri.getPath());
        LogsManager.printLog("FILE_PATH",uri.getPath()+"\n"+file.getAbsolutePath());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        RequestBody ttl = RequestBody.create(MediaType.parse("text/plain"),KycUtilManager.TTL);

        RequestBody mimeType = RequestBody.create(MediaType.parse("text/plain"), KycUtilManager.MIME_TYPE);
        Call<ImageUrlModel> call=RetrofitClient.getRetrofit().create(KycApiServices.class).uploadFile(body,ttl,mimeType);

        getKycData(listener,call,requestCode);
    }

    public static void getKycVideoVerificationUrl(KycResponseListener listener, Integer requestCode, String partnerId, String accessToken, KycVideoRequestModel kycVideoRequestModel){
        Call<KycVideoModel> call=RetrofitClient.getRetrofit().create(KycApiServices.class).getVideoVerificationUrl(partnerId,accessToken,kycVideoRequestModel);
        getKycData(listener,call,requestCode);
    }

    public static void  getKycVideoVerificationResult(KycResponseListener listener, Integer requestCode, String partnerId, String accessToken , KycResultRequestModel kycResultRequestModel){
        Call<KycResultModel> call=RetrofitClient.getRetrofit().create(KycApiServices.class).getKycVerificationResult(partnerId,accessToken,kycResultRequestModel);
        getKycData(listener,call,requestCode);
    }
}
