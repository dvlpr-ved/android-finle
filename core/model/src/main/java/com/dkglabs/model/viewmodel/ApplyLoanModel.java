package com.dkglabs.model.viewmodel;

/**
 * Created by Himanshu Srivastava on 09,June,2023
 * Project e_savari
 */
public class ApplyLoanModel {
    private Boolean exitApply = false;
    private Boolean continueApply = false;
    private int docAction = -1;
    private String fileMethod = "";

    private int loanState = 0;
    private int psyTestDone = 0;

    public ApplyLoanModel() {
    }

    public Boolean getExitApply() {
        return exitApply;
    }

    public void setExitApply(Boolean exitApply) {
        this.exitApply = exitApply;
    }

    public Boolean getContinueApply() {
        return continueApply;
    }

    public void setContinueApply(Boolean continueApply) {
        this.continueApply = continueApply;
    }

    public int getDocAction() {
        return docAction;
    }

    public void setDocAction(int docAction) {
        this.docAction = docAction;
    }

    public String getFileMethod() {
        return fileMethod;
    }

    public void setFileMethod(String fileMethod) {
        this.fileMethod = fileMethod;
    }

    public int getLoanState() {
        return loanState;
    }

    public void setLoanState(int loanState) {
        this.loanState = loanState;
    }

    public int getPsyTestDone() {
        return psyTestDone;
    }

    public void setPsyTestDone(int psyTestDone) {
        this.psyTestDone = psyTestDone;
    }
}
