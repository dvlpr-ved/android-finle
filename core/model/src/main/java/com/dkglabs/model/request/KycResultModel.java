package com.dkglabs.model.request;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KycResultModel {
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
        @SerializedName("token")
        private String token;

        @SerializedName("patronId")
        private String patronId;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getPatronId() {
            return patronId;
        }

        public void setPatronId(String patronId) {
            this.patronId = patronId;
        }
    }

    public static class Result {
        @SerializedName("token")
        private String token;

        @SerializedName("videoUrl")
        private String videoUrl;

        @SerializedName("isUsed")
        private int isUsed;

        @SerializedName("videoVerification")
        private VideoVerification videoVerification;

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

        public int getIsUsed() {
            return isUsed;
        }

        public void setIsUsed(int isUsed) {
            this.isUsed = isUsed;
        }

        public VideoVerification getVideoVerification() {
            return videoVerification;
        }

        public void setVideoVerification(VideoVerification videoVerification) {
            this.videoVerification = videoVerification;
        }
    }

    public static class VideoVerification {
        @SerializedName("videoFaceMatch")
        private List<String> videoFaceMatch;

        @SerializedName("audioMatch")
        private AudioMatch audioMatch;

        @SerializedName("matchImageFaceMatch")
        private MatchImageFaceMatch matchImageFaceMatch;

        @SerializedName("videoForensics")
        private VideoForensics videoForensics;

        @SerializedName("otp")
        private String otp;

        @SerializedName("video")
        private String video;

        @SerializedName("faceFound")
        private String faceFound;

        @SerializedName("isAudioProcessed")
        private String isAudioProcessed;

        @SerializedName("isVideoProcessed")
        private String isVideoProcessed;

        public List<String> getVideoFaceMatch() {
            return videoFaceMatch;
        }

        public void setVideoFaceMatch(List<String> videoFaceMatch) {
            this.videoFaceMatch = videoFaceMatch;
        }

        public AudioMatch getAudioMatch() {
            return audioMatch;
        }

        public void setAudioMatch(AudioMatch audioMatch) {
            this.audioMatch = audioMatch;
        }

        public MatchImageFaceMatch getMatchImageFaceMatch() {
            return matchImageFaceMatch;
        }

        public void setMatchImageFaceMatch(MatchImageFaceMatch matchImageFaceMatch) {
            this.matchImageFaceMatch = matchImageFaceMatch;
        }

        public VideoForensics getVideoForensics() {
            return videoForensics;
        }

        public void setVideoForensics(VideoForensics videoForensics) {
            this.videoForensics = videoForensics;
        }

        public String getOtp() {
            return otp;
        }

        public void setOtp(String otp) {
            this.otp = otp;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getFaceFound() {
            return faceFound;
        }

        public void setFaceFound(String faceFound) {
            this.faceFound = faceFound;
        }

        public String getIsAudioProcessed() {
            return isAudioProcessed;
        }

        public void setIsAudioProcessed(String isAudioProcessed) {
            this.isAudioProcessed = isAudioProcessed;
        }

        public String getIsVideoProcessed() {
            return isVideoProcessed;
        }

        public void setIsVideoProcessed(String isVideoProcessed) {
            this.isVideoProcessed = isVideoProcessed;
        }
    }

    public static class AudioMatch {
        @SerializedName("matchAudioScore")
        private String matchAudioScore;

        public String getMatchAudioScore() {
            return matchAudioScore;
        }

        public void setMatchAudioScore(String matchAudioScore) {
            this.matchAudioScore = matchAudioScore;
        }
    }

    public static class MatchImageFaceMatch {

    }

    public static class VideoForensics {
        @SerializedName("staticRisk")
        private String staticRisk;

        @SerializedName("prerecordedRisk")
        private String prerecordedRisk;

        @SerializedName("videoLandMarks")
        private String videoLandMarks;

        @SerializedName("faceLandMarks")
        private List<String> faceLandMarks;

        @SerializedName("liveliness")
        private String liveliness;

        public String getStaticRisk() {
            return staticRisk;
        }

        public void setStaticRisk(String staticRisk) {
            this.staticRisk = staticRisk;
        }

        public String getPrerecordedRisk() {
            return prerecordedRisk;
        }

        public void setPrerecordedRisk(String prerecordedRisk) {
            this.prerecordedRisk = prerecordedRisk;
        }

        public String getVideoLandMarks() {
            return videoLandMarks;
        }

        public void setVideoLandMarks(String videoLandMarks) {
            this.videoLandMarks = videoLandMarks;
        }

        public List<String> getFaceLandMarks() {
            return faceLandMarks;
        }

        public void setFaceLandMarks(List<String> faceLandMarks) {
            this.faceLandMarks = faceLandMarks;
        }

        public String getLiveliness() {
            return liveliness;
        }

        public void setLiveliness(String liveliness) {
            this.liveliness = liveliness;
        }
    }
}
