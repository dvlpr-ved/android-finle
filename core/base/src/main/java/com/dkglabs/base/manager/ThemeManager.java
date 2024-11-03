package com.dkglabs.base.manager;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * Created by Himanshu Srivastava on 26,July,2023
 * Project e_savari
 */
public class ThemeManager extends BasePersistentManager {

    public static final String MODE_DEFAULT = "themeSystemDefault";
    public static final String MODE_DARK = "themeDark";
    public static final String MODE_LIGHT = "themeLight";

    public static String getTheme() {
        return (String) readFromPreference("theme", String.class, MODE_DEFAULT);
    }

    public static void setTheme(String theme) {
        writeToPreference("theme", theme);
    }

    public static void changeTheme(String theme) {
        setTheme(theme);
        switch (theme) {
            case MODE_DEFAULT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                break;
            case MODE_DARK:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case MODE_LIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
        }
    }

    public static void updateTheme() {
        changeTheme(getTheme());
    }

}
