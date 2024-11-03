package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Himanshu Srivastava on 31,July,2023
 * Project e_savari
 */
public class EvScoreResponse {

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("loanId")
    private String loanId;

    @JsonProperty("evScore")
    private String evScore;

    @JsonProperty("evScoreDateTime")
    private String evScoreDateTime;

    public EvScoreResponse() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getEvScore() {
        return evScore;
    }

    public void setEvScore(String evScore) {
        this.evScore = evScore;
    }

    public String getEvScoreDateTime() {
        return evScoreDateTime;
    }

    public void setEvScoreDateTime(String evScoreDateTime) {
        this.evScoreDateTime = evScoreDateTime;
    }
}
