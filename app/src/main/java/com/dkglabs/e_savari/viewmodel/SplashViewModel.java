package com.dkglabs.e_savari.viewmodel;

/**
 * Created by Himanshu Srivastava on 25,May,2023
 * Project e_savari
 */
public class SplashViewModel {

    private String version;

    public SplashViewModel(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
