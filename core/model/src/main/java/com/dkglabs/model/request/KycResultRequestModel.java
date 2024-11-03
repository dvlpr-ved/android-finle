package com.dkglabs.model.request;

import com.google.gson.annotations.SerializedName;

public class KycResultRequestModel {
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
}
