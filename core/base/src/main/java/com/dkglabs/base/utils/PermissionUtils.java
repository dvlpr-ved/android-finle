package com.dkglabs.base.utils;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionUtils {

    public static boolean askPermission(Activity activity, String permission, int code) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(activity, new String[]{permission}, code);
            } else {
                activity.onRequestPermissionsResult(code, new String[]{permission}, new int[]{PackageManager.PERMISSION_GRANTED});
            }
            return true;
        }
        return false;
    }

    public static boolean requestLocationPermission(Activity activity, int code) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            boolean foreground = ActivityCompat.checkSelfPermission(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (foreground) {
                boolean background = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;
                if (background) {
                    activity.onRequestPermissionsResult(code, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, new int[]{PackageManager.PERMISSION_GRANTED, PackageManager.PERMISSION_GRANTED});
                } else {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, code);
                }
            } else {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION}, code);
            }

            return true;
        }
        return false;
    }

    public static void showPermissionDialog(Activity activity, String msg, String
            permission, DialogInterface.OnClickListener listener) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                UIUtils.showPermissionRationale(activity, msg, listener);
            } else {
                listener.onClick(null, DialogInterface.BUTTON_POSITIVE);
            }
        } else {
            listener.onClick(null, DialogInterface.BUTTON_POSITIVE);
        }
    }

    public static boolean hasPermission(Activity activity, String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean hasPermissions(Activity activity, String[] permissions) {
        for (String permission : permissions)
            if (!hasPermission(activity, permission))
                return false;
        return true;
    }
}
