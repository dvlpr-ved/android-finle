package com.dkglabs.model.applyloan;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**a
 * Created by Himanshu Srivastava on 10,July,2023
 * Project e_savari
 */
public class DocDetails  implements Parcelable, Serializable {
    @JsonProperty("vrfsCode")
    private String vrfsCode;
    @JsonProperty("vrfCode")
    private String vrfCode;
    @JsonProperty("vrfSName")
    private String vrfSName;
    @JsonProperty("vrfName")
    private String vrfName;
    @JsonProperty("mandatory")
    private String mandatory;
    @JsonProperty("uploadStatus")
    private Boolean uploadStatus = false;
    private Boolean isUploading = false;
    private Boolean uploadFailed = false;

    public DocDetails() {
    }

    protected DocDetails (Parcel in) {
        vrfsCode = in.readString();
        vrfCode = in.readString();
        vrfSName = in.readString();
        vrfName = in.readString();
        mandatory = in.readString();
        byte tmpUploadStatus = in.readByte();
        uploadStatus = tmpUploadStatus == 0 ? null : tmpUploadStatus == 1;
        byte tmpIsUploading = in.readByte();
        isUploading = tmpIsUploading == 0 ? null : tmpIsUploading == 1;
        byte tmpUploadFailed = in.readByte();
        uploadFailed = tmpUploadFailed == 0 ? null : tmpUploadFailed == 1;
    }

    public static final Creator<DocDetails> CREATOR = new Creator<DocDetails>() {
        @Override
        public DocDetails createFromParcel(Parcel in) {
            return new DocDetails(in);
        }

        @Override
        public DocDetails[] newArray(int size) {
            return new DocDetails[size];
        }
    };

    public String getVrfsCode() {
        return vrfsCode;
    }

    public void setVrfsCode(String vrfsCode) {
        this.vrfsCode = vrfsCode;
    }

    public String getVrfCode() {
        return vrfCode;
    }

    public void setVrfCode(String vrfCode) {
        this.vrfCode = vrfCode;
    }

    public String getVrfSName() {
        return vrfSName;
    }

    public void setVrfSName(String vrfSName) {
        this.vrfSName = vrfSName;
    }

    public String getVrfName() {
        return vrfName;
    }

    public void setVrfName(String vrfName) {
        this.vrfName = vrfName;
    }

    public String getMandatory() {
        return mandatory;
    }

    public void setMandatory(String mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean isUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(Boolean uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public Boolean isUploading() {
        return isUploading;
    }

    public void setUploading(Boolean uploading) {
        isUploading = uploading;
    }

    public Boolean isUploadFailed() {
        return uploadFailed;
    }

    public void setUploadFailed(Boolean uploadFailed) {
        this.uploadFailed = uploadFailed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(vrfsCode);
        dest.writeString(vrfCode);
        dest.writeString(vrfSName);
        dest.writeString(vrfName);
        dest.writeString(mandatory);
        dest.writeByte((byte) (uploadStatus == null ? 0 : uploadStatus ? 1 : 2));
        dest.writeByte((byte) (isUploading == null ? 0 : isUploading ? 1 : 2));
        dest.writeByte((byte) (uploadFailed == null ? 0 : uploadFailed ? 1 : 2));
    }
}
