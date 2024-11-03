package com.dkglabs.base.manager;

import android.util.Log;

import com.dkglabs.base.utils.AppUtils;
import com.google.firebase.crashlytics.FirebaseCrashlytics;


public class LogsManager {
    private static String DEFAULT_TAG = "DEFAULT_TAG";
    private static String EXCEPTION_TAG = "EXCEPTION_TAG";
    private static int LOG_DEBUG = 0;
    private static int LOG_ERROR = 1;
    private static int LOG_INFO = 2;


    public static void printLog(String tag, Exception exception) {
        FirebaseCrashlytics.getInstance().recordException(exception);
        printLog(tag, "Exception caught :: " + exception);
        StackTraceElement[] stackTraceElements = exception.getStackTrace();
        for (StackTraceElement traceElement : stackTraceElements) {
            printLog(tag, "Exception caught :: " + traceElement);
        }

    }

    public static void printLog(Exception exception) {
        printLog(EXCEPTION_TAG, exception);
    }

    public static void printLog(String tag, String logMsg) {
        tag = (AppUtils.isContainsData(tag)) ? tag : DEFAULT_TAG;
        logMsg = (AppUtils.isContainsData(logMsg)) ? logMsg : "log msg is null";
        Log.d(tag, logMsg);
        try {
            FirebaseCrashlytics.getInstance().log(logMsg);
        } catch (Exception e) {
            //do nothing
            FirebaseCrashlytics.getInstance().recordException(e);
        }
    }

    public static void printLog(String logMsg) {
        printLog(DEFAULT_TAG, logMsg);
    }
}
