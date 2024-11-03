package com.dkglabs.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KycVideoRequestModel {

    @SerializedName("task")
    private String task;

    @SerializedName("essentials")
    private Essentials essentials;

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Essentials getEssentials() {
        return essentials;
    }

    public void setEssentials(Essentials essentials) {
        this.essentials = essentials;
    }

    public static class Essentials {
        @SerializedName("matchImage")
        private List<String> matchImage;

        @SerializedName("customVideoRecordTime")
        private String customVideoRecordTime;

        @SerializedName("callbackUrl")
        private String callbackUrl;

        public List<String> getMatchImage() {
            return matchImage;
        }

        public void setMatchImage(List<String> matchImage) {
            this.matchImage = matchImage;
        }

        public String getCustomVideoRecordTime() {
            return customVideoRecordTime;
        }

        public void setCustomVideoRecordTime(String customVideoRecordTime) {
            this.customVideoRecordTime = customVideoRecordTime;
        }

        public String getCallbackUrl() {
            return callbackUrl;
        }

        public void setCallbackUrl(String callbackUrl) {
            this.callbackUrl = callbackUrl;
        }
    }
}
