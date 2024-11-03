package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Himanshu Srivastava on 17,June,2023
 * Project e_savari
 */
public class PsyAnswerListRequest {
    @JsonProperty("psyDetailDtoList")
    private List<PsyAnswerRequest> psyAnswerRequestList;
    @JsonProperty("userId")
    private String userId;

    public PsyAnswerListRequest() {
    }

    public List<PsyAnswerRequest> getPsyAnswerRequestList() {
        return psyAnswerRequestList;
    }

    public void setPsyAnswerRequestList(List<PsyAnswerRequest> psyAnswerRequestList) {
        this.psyAnswerRequestList = psyAnswerRequestList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
