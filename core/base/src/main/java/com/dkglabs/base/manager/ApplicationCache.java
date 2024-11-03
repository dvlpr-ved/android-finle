package com.dkglabs.base.manager;

import android.app.Application;

/**
 * Created by Himanshu Srivastava on 3/24/2023.
 */
public class ApplicationCache {
    /**
     * Singleton Class
     */
    private static ApplicationCache applicationCache = null;
    //Application Context
    private Application appContext;

    //Constructor
    private ApplicationCache() {

    }

    //Get Instance
    public static synchronized ApplicationCache getInstance() {
        if (applicationCache == null) {
            applicationCache = new ApplicationCache();
        }

        return applicationCache;
    }

    public Application getAppContext() {
        return appContext;
    }

    public void setAppContext(Application appContext) {
        this.appContext = appContext;
    }


}
