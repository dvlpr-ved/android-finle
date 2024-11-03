package com.dkglabs.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Himanshu Srivastava on 01,June,2023
 * Project e_savari
 */

public class QuestionAnswerListResponse {

    @JsonProperty("questionAnswerList")
    private List<PsyQuestionAnswerResponse> questionAnswerList;

    public List<PsyQuestionAnswerResponse> getQuestionAnswerList() {
        return questionAnswerList;
    }

    public void setQuestionAnswerList(List<PsyQuestionAnswerResponse> questionAnswerList) {
        this.questionAnswerList = questionAnswerList;
    }
}
