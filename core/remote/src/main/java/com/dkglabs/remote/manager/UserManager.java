package com.dkglabs.remote.manager;

import android.net.Uri;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.model.request.LoginRequest;
import com.dkglabs.model.request.UpdatePasswordRequest;
import com.dkglabs.model.request.UpdateUserRequest;
import com.dkglabs.model.request.UserRegisterRequest;
import com.dkglabs.model.response.BaseResponse;
import com.dkglabs.model.response.LoginResponse;
import com.dkglabs.model.response.UserResponse;
import com.dkglabs.remote.interfaces.ApiServiceInterface;
import com.dkglabs.remote.interfaces.ResponseListener;
import com.dkglabs.remote.utils.AppUrlConstants;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * Created by Himanshu Srivastava on 9/13/2022.
 */
public class UserManager extends BaseManager {

    public static void userLogin(int requestCode, LoginRequest loginRequest, ResponseListener listener) {
        Call<BaseResponse<LoginResponse>> call = ApiManager.createService(ApiServiceInterface.class).userLogin(loginRequest);
        callApi(requestCode, call, listener);
    }

    public static void registerUser(int requestCode, UserRegisterRequest userRegisterRequest, ResponseListener listener) {
        Call<BaseResponse<UserResponse>> call = ApiManager.createService(ApiServiceInterface.class).userRegister(userRegisterRequest);
        callApi(requestCode, call, listener);
    }

    public static void userDetails(int requestCode, LoginRequest loginRequest, ResponseListener listener) {
        Call<BaseResponse<UserResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).userDetails(loginRequest);
        callApi(requestCode, call, listener);
    }

    public static void updateUser(int requestCode, UpdateUserRequest request, ResponseListener listener) {
        Call<BaseResponse<UserResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).updateUser(request);
        callApi(requestCode, call, listener);
    }

    public static void updatePassword(int requestCode, UpdatePasswordRequest request, ResponseListener listener) {
        Call<BaseResponse<UserResponse>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).updatePassword(request);
        callApi(requestCode, call, listener);
    }

    public static void uploadProfileImage(int requestCode, String imageUri, String userId, ResponseListener listener) {
        Uri uri = Uri.parse(imageUri);
        File file = new File(uri.getPath());
        LogsManager.printLog("FILE_PATH", file.getAbsolutePath());
        RequestBody requestBody = RequestBody.create(file, MediaType.parse("image/*"));
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "PROFILE_IMAGE", requestBody);
        Call<BaseResponse<String>> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).uploadProfileImage(userId, filePart);
        callApi(requestCode, call, listener);
    }

    public static GlideUrl downloadImage(String userId, String token) {
        return new GlideUrl(AppUrlConstants.SERVER_URL + AppUrlConstants.DOWNLOAD_PROFILE_IMAGE + "?userId=" + userId,
                new LazyHeaders.Builder()
                        .addHeader("Authorization", token)
                        .build());
    }

    public static void removeProfileImage(int requestCode, String userId, ResponseListener listener) {
        Call<BaseResponse> call = ApiManager.createServiceWithAuth(ApiServiceInterface.class).removeProfileImage(userId);
        callApi(requestCode, call, listener);
    }
}
