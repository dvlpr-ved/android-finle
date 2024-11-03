package com.dkglabs.apply_loan.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class NbfcAndDealerData implements Parcelable {
    public  String partnerId;
    public  String nbfcId;
    public String nbfcName;

    public NbfcAndDealerData() {
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getNbfcId() {
        return nbfcId;
    }

    public void setNbfcId(String nbfcId) {
        this.nbfcId = nbfcId;
    }

    public String getNbfcName() {
        return nbfcName;
    }

    public void setNbfcName(String nbfcName) {
        this.nbfcName = nbfcName;
    }

    protected NbfcAndDealerData(Parcel in) {
        partnerId = in.readString();
        nbfcId = in.readString();
        nbfcName = in.readString();
    }

    public static final Creator<NbfcAndDealerData> CREATOR = new Creator<NbfcAndDealerData>() {
        @Override
        public NbfcAndDealerData createFromParcel(Parcel in) {
            return new NbfcAndDealerData(in);
        }

        @Override
        public NbfcAndDealerData[] newArray(int size) {
            return new NbfcAndDealerData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(partnerId);
        dest.writeString(nbfcId);
        dest.writeString(nbfcName);
    }
}
