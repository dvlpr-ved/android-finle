package com.dkglabs.base.manager;


import android.content.Context;
import android.content.SharedPreferences;

public class BasePersistentManager {


    private final static String SHARED_PREF_NAME = "E_SAVARI";

    private static SharedPreferences sharedPreferences = CachingManager.getAppContext().getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);


    protected static void writeToPreference(String key, Object value) {

        //Get SharedPreferences Editor
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Write content to File
        if (value instanceof String) //String value
        {
            editor.putString(key, ((String) value).trim());
        } else if (value instanceof Boolean) //Boolean value
        {
            editor.putBoolean(key, ((Boolean) value));
        } else if (value instanceof Float) //Float value
        {
            editor.putFloat(key, ((Float) value));
        } else if (value instanceof Integer) //Integer value
        {
            editor.putInt(key, ((Integer) value));
        } else if (value instanceof Long) //Long value
        {
            editor.putLong(key, ((Long) value));
        } else if (value instanceof Double) {
            editor.putLong(key, Double.doubleToRawLongBits((Double) value));
        }

        //Commit Preferences Changes
        editor.commit();
    }


    protected static Object readFromPreference(String key, Class<? extends Object> classType, Object defaultValue) {
        Object object;

        //Get Content from File
        if (classType.equals(String.class)) //String value
        {
            object = sharedPreferences.getString(key, (String) defaultValue);
        } else if (classType.equals(Boolean.class)) //Boolean value
        {
            object = sharedPreferences.getBoolean(key, (Boolean) defaultValue);
        } else if (classType.equals(Integer.class)) //Integer value
        {
            object = sharedPreferences.getInt(key, (Integer) defaultValue);
        } else if (classType.equals(Float.class)) //Float value
        {
            object = sharedPreferences.getFloat(key, (Float) defaultValue);
        } else if (classType.equals(Long.class)) //Long value
        {
            object = sharedPreferences.getLong(key, (Long) defaultValue);
        } else if (classType.equals(Double.class)) //Long value
        {
            object = Double.longBitsToDouble(sharedPreferences.getLong(key, (Long) defaultValue));
        } else {
            object = null;
        }

        return object;
    }

    public static void removeKey(String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }


    public static boolean clearAllPreferences() {
        //Get App Context
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Clear content from File
        editor.clear();

        return (editor.commit());
    }
}
