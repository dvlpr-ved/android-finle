package com.dkglabs.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KycVideoModel {

    @SerializedName("essentials")
    private Essentials essentials;

    @SerializedName("id")
    private String id;

    @SerializedName("patronId")
    private String patronId;

    @SerializedName("task")
    private String task;

    @SerializedName("result")
    private Result result;

    public Essentials getEssentials() {
        return essentials;
    }

    public void setEssentials(Essentials essentials) {
        this.essentials = essentials;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatronId() {
        return patronId;
    }

    public void setPatronId(String patronId) {
        this.patronId = patronId;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Essentials {
        @SerializedName("matchImage")
        private List<String> matchImage;

        @SerializedName("customVideoRecordTime")
        private String customVideoRecordTime;

        @SerializedName("processFullVideo")
        private String processFullVideo;

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

        public String getProcessFullVideo() {
            return processFullVideo;
        }

        public void setProcessFullVideo(String processFullVideo) {
            this.processFullVideo = processFullVideo;
        }
    }

    public static class Result {
        @SerializedName("token")
        private String token;

        @SerializedName("videoUrl")
        private String videoUrl;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }
    }
}
