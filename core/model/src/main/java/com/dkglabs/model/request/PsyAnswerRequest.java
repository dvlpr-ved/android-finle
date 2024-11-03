package com.dkglabs.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Himanshu Srivastava on 17,June,2023
 * Project e_savari
 */
public class PsyAnswerRequest {

    @JsonProperty("questionCode")
    private String questionCode;
    @JsonProperty("questionNumber")
    private String questionNumber;
    @JsonProperty("answer")
    private String userAnswer;

    public PsyAnswerRequest() {
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }
}
