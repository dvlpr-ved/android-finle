package com.dkglabs.model.viewmodel;

/**
 * Created by Himanshu Srivastava on 21,July,2023
 * Project e_savari
 */
public class AppViewModel {
    private boolean isLogoutPressed = false;
    private boolean terms = false;
    private boolean privacy = false;
    private boolean languageChanged = false;
    private boolean isLanguagePressed = false;
    private boolean isTermsAgree = false;
    private String changeProfile = "";
    private boolean showAllProducts = false;

    public AppViewModel() {
    }

    public boolean isLogoutPressed() {
        return isLogoutPressed;
    }

    public void setLogoutPressed(boolean logoutPressed) {
        isLogoutPressed = logoutPressed;
    }

    public boolean isTerms() {
        return terms;
    }

    public void setTerms(boolean terms) {
        this.terms = terms;
    }

    public boolean isPrivacy() {
        return privacy;
    }

    public void setPrivacy(boolean privacy) {
        this.privacy = privacy;
    }

    public boolean isLanguageChanged() {
        return languageChanged;
    }

    public void setLanguageChanged(boolean languageChanged) {
        this.languageChanged = languageChanged;
    }

    public boolean isLanguagePressed() {
        return isLanguagePressed;
    }

    public void setLanguagePressed(boolean languagePressed) {
        isLanguagePressed = languagePressed;
    }

    public boolean isTermsAgree() {
        return isTermsAgree;
    }

    public void setTermsAgree(boolean termsAgree) {
        isTermsAgree = termsAgree;
    }

    public String getChangeProfile() {
        return changeProfile;
    }

    public void setChangeProfile(String changeProfile) {
        this.changeProfile = changeProfile;
    }

    public boolean isShowAllProducts() {
        return showAllProducts;
    }

    public void setShowAllProducts(boolean showAllProducts) {
        this.showAllProducts = showAllProducts;
    }
}
