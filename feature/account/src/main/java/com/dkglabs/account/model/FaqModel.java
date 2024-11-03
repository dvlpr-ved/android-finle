package com.dkglabs.account.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Himanshu Srivastava on 29,April,2023
 * Project e_savari_customer
 */
public class FaqModel {
    private String faqQuestion;
    private String faqAnswer;
    private Drawable faqIcon;

    public FaqModel() {
    }

    public FaqModel(String faqQuestion, String faqAnswer, Drawable faqIcon) {
        this.faqQuestion = faqQuestion;
        this.faqAnswer = faqAnswer;
        this.faqIcon = faqIcon;
    }

    public String getFaqQuestion() {
        return faqQuestion;
    }

    public void setFaqQuestion(String faqQuestion) {
        this.faqQuestion = faqQuestion;
    }

    public String getFaqAnswer() {
        return faqAnswer;
    }

    public void setFaqAnswer(String faqAnswer) {
        this.faqAnswer = faqAnswer;
    }

    public Drawable getFaqIcon() {
        return faqIcon;
    }

    public void setFaqIcon(Drawable faqIcon) {
        this.faqIcon = faqIcon;
    }
}
