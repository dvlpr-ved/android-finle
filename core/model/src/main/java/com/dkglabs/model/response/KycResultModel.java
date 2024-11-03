package com.dkglabs.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class KycResultModel implements Parcelable {
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

    protected KycResultModel(Parcel in) {
        id = in.readString();
        patronId = in.readString();
        task = in.readString();
    }

    public static final Creator<KycResultModel> CREATOR = new Creator<KycResultModel>() {
        @Override
        public KycResultModel createFromParcel(Parcel in) {
            return new KycResultModel(in);
        }

        @Override
        public KycResultModel[] newArray(int size) {
            return new KycResultModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(patronId);
        dest.writeString(task);
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
        private List<VideoFaceMatch> videoFaceMatch;

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

        public List<VideoFaceMatch> getVideoFaceMatch() {
            return videoFaceMatch;
        }

        public void setVideoFaceMatch(List<VideoFaceMatch> videoFaceMatch) {
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

    public static class VideoFaceMatch {
        @SerializedName("videoImages")
        private List<String> videoImages;

        @SerializedName("matchStatistics")
        private MatchStatistics matchStatistics;

        @SerializedName("finalMatchImage")
        private String finalMatchImage;

        public List<String> getVideoImages() {
            return videoImages;
        }

        public void setVideoImages(List<String> videoImages) {
            this.videoImages = videoImages;
        }

        public MatchStatistics getMatchStatistics() {
            return matchStatistics;
        }

        public void setMatchStatistics(MatchStatistics matchStatistics) {
            this.matchStatistics = matchStatistics;
        }

        public String getFinalMatchImage() {
            return finalMatchImage;
        }

        public void setFinalMatchImage(String finalMatchImage) {
            this.finalMatchImage = finalMatchImage;
        }
    }

    public static class MatchStatistics {
        @SerializedName("matchPercentage")
        private String matchPercentage;

        @SerializedName("coVariance")
        private String coVariance;

        public String getMatchPercentage() {
            return matchPercentage;
        }

        public void setMatchPercentage(String matchPercentage) {
            this.matchPercentage = matchPercentage;
        }

        public String getCoVariance() {
            return coVariance;
        }

        public void setCoVariance(String coVariance) {
            this.coVariance = coVariance;
        }
    }
}
