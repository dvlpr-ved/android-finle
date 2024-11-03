package com.dkglabs.base.manager;

import android.app.Application;
import android.content.Context;

public class CachingManager {

    //Save Application Context
    public static void saveAppContext(Application context) {
        //Get ApplicationCache
        ApplicationCache applicationCache = ApplicationCache.getInstance();

        //Save Application Context
        applicationCache.setAppContext(context);
    }

    //Get Application Context
    public static Context getAppContext() {
        //Get ApplicationCache
        ApplicationCache applicationCache = ApplicationCache.getInstance();

        //Get Application Context
        return (applicationCache.getAppContext());
    }

}
