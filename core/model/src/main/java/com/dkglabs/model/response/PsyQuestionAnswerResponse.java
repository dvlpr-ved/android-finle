package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Himanshu Srivastava on 01,June,2023
 * Project e_savari
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PsyQuestionAnswerResponse {
    @JsonProperty("questionNumber")
    private String questionNumber;
    @JsonProperty("questionCode")
    private String questionCode;
    @JsonProperty("question")
    private String question;
    @JsonProperty("optionA")
    private String optionA;
    @JsonProperty("optionB")
    private String optionB;
    @JsonProperty("optionC")
    private String optionC;
    @JsonProperty("optionD")
    private String optionD;

    private String selectedOption = "";

    public PsyQuestionAnswerResponse() {
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionCode() {
        return questionCode;
    }

    public void setQuestionCode(String questionCode) {
        this.questionCode = questionCode;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }
}
