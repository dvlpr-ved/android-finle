package com.dkglabs.apply_loan.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class KYCVideoRequestModel implements Parcelable {
    String url;
    String partnerId;
    String token;
    String accessToken;

    public KYCVideoRequestModel() {
    }

    public KYCVideoRequestModel(String url, String partnerId, String token, String accessToken) {
        this.url = url;
        this.partnerId = partnerId;
        this.token = token;
        this.accessToken = accessToken;
    }

    protected KYCVideoRequestModel(Parcel in) {
        url = in.readString();
        partnerId = in.readString();
        token = in.readString();
        accessToken = in.readString();
    }

    public static final Creator<KYCVideoRequestModel> CREATOR = new Creator<KYCVideoRequestModel>() {
        @Override
        public KYCVideoRequestModel createFromParcel(Parcel in) {
            return new KYCVideoRequestModel(in);
        }

        @Override
        public KYCVideoRequestModel[] newArray(int size) {
            return new KYCVideoRequestModel[size];
        }
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(partnerId);
        dest.writeString(token);
        dest.writeString(accessToken);
    }
}
