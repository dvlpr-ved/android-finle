package com.dkglabs.base.manager;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;

import java.util.Locale;

/**
 * Created by Himanshu Srivastava on 07-Dec-22.
 */
public class LanguageManager extends BasePersistentManager {
    public static final String ENGLISH = "en";
    public static final String HINDI = "hi";

    public static String getUserLanguage() {
        return (String) readFromPreference("user_language", String.class, ENGLISH);
    }

    public static void setUserLanguage(String language) {
        writeToPreference("user_language", language);
    }

    public static Boolean getUserLanguageChanged() {
        return (Boolean) readFromPreference("user_language_changed", Boolean.class, false);
    }

    public static void setUserLanguageChanged(Boolean languageChanged) {
        writeToPreference("user_language_changed", languageChanged);
    }

    public static Context setLocale(Context context) {
        return setNewLocale(context, getUserLanguage());
    }


    // the method is used to set the language at runtime
    public static Context setNewLocale(Context context, String language) {
        setUserLanguage(language);
        return updateResourcesLegacy(context, language);
    }


    public static Context updateResourcesLegacy(Context context, String lang) {

        LocaleListCompat appLocale = LocaleListCompat.forLanguageTags(lang);
        AppCompatDelegate.setApplicationLocales(appLocale);

        /*
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);

        Resources resources = context.getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);
        context = context.createConfigurationContext(configuration);
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        */

        return context;
    }
}
