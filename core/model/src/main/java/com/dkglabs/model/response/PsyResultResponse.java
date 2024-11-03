package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Himanshu Srivastava on 06,July,2023
 * Project e_savari
 */
public class PsyResultResponse implements Serializable {
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("testResultFlag")
    private String testResultFlag;
    @JsonProperty("testStatusFlag")
    private String testStatusFlag;
    @JsonProperty("totalCorrect")
    private Integer totalCorrect;
    @JsonProperty("totalInCorrect")
    private Integer totalInCorrect;
    @JsonProperty("totalQuestion")
    private Integer totalQuestion;

    public PsyResultResponse() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTestResultFlag() {
        return testResultFlag == null ? "N" : testResultFlag;
    }

    public void setTestResultFlag(String testResultFlag) {
        this.testResultFlag = testResultFlag;
    }

    public String getTestStatusFlag() {
        return testStatusFlag == null ? "N" : testStatusFlag;
    }

    public void setTestStatusFlag(String testStatusFlag) {
        this.testStatusFlag = testStatusFlag;
    }

    public Integer getTotalCorrect() {
        return totalCorrect;
    }

    public void setTotalCorrect(Integer totalCorrect) {
        this.totalCorrect = totalCorrect;
    }

    public Integer getTotalInCorrect() {
        return totalInCorrect;
    }

    public void setTotalInCorrect(Integer totalInCorrect) {
        this.totalInCorrect = totalInCorrect;
    }

    public Integer getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(Integer totalQuestion) {
        this.totalQuestion = totalQuestion;
    }
}
