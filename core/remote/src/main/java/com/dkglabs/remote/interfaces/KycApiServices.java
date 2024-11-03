package com.dkglabs.remote.interfaces;

import com.dkglabs.model.request.KycLoginRequest;
import com.dkglabs.model.request.KycResultRequestModel;
import com.dkglabs.model.request.KycVideoRequestModel;
import com.dkglabs.model.response.ImageUrlModel;
import com.dkglabs.model.response.KycAuthModel;
import com.dkglabs.model.response.KycResultModel;
import com.dkglabs.model.response.KycVideoModel;
import com.dkglabs.remote.utils.KycUtilManager;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface KycApiServices {
    @POST(KycUtilManager.Login)
    Call<KycAuthModel> getTokenAndId(@Body KycLoginRequest kycLoginRequest);

    @Multipart
    @POST(KycUtilManager.IMAGE_URL)
    Call<ImageUrlModel> uploadFile(
            @Part MultipartBody.Part file,
            @Part("ttl") RequestBody ttl,
            @Part("mime type") RequestBody mimeType
    );
    @POST(KycUtilManager.VIDEO_URL)
    Call<KycVideoModel> getVideoVerificationUrl(
            @Path("patronId") String patronId,
            @Header("Authorization") String accessToken,
            @Body KycVideoRequestModel kycVideoRequestModel
    );

    @POST(KycUtilManager.VIDEO_RESULT)
    Call<KycResultModel> getKycVerificationResult(
            @Path("patronId") String patronId,
            @Header("Authorization") String accessToken,
            @Body KycResultRequestModel resultRequestModel
    );

}
