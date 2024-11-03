package com.dkglabs.e_savari.application;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.dkglabs.base.manager.CachingManager;
import com.google.firebase.FirebaseApp;

/**
 * Created by Himanshu Srivastava on 3/30/2023.
 */
public class ESavariApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        MultiDex.install(this);
        CachingManager.saveAppContext(this);
    }
}
