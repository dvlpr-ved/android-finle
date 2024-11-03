package com.dkglabs.base.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.dkglabs.base.activities.WebViewActivity;
import com.dkglabs.base.interfaces.DialogActionInterface;
import com.dkglabs.base.manager.LogsManager;
import com.dkglabs.base.ui.CustomAlertDialog;
import com.dkglabs.base.R;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UIUtils {

    public static void hideViewGone(View view) {
        if (view != null && view.getVisibility() != View.GONE) {
            view.setVisibility(View.GONE);
        }
    }

    public static void hideViewInvisible(View view) {
        if (view != null && view.getVisibility() != View.INVISIBLE) {
            view.setVisibility(View.INVISIBLE);
        }
    }

    public static void showView(View view) {
        if (view != null && view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
    }

    public static void hideKeyboard(Activity activity) {
        try {

            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ex) {
            LogsManager.printLog("hideKeyboard: " + ex.getMessage());
        }
    }


    public static Dialog showProgressDialog(Activity activity, Dialog pd) {
        if (pd != null && pd.isShowing()) {
            return pd;
        }
        return showProgressDialog(activity);
    }

    public static Dialog showProgressDialog(Activity activity, Dialog pd, String title) {
        if (pd != null && pd.isShowing()) {
            return pd;
        }
        return showProgressDialog(activity, title);
    }

    public static Dialog showProgressDialog(Activity activity, String title) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.layout_progressbar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        TextView tv = dialog.findViewById(R.id.textTitle);
        tv.setText(title);
        dialog.create();
        dialog.show();
        return dialog;
    }

    public static Dialog showProgressDialog(Activity activity) {
        return showProgressDialog(activity, activity.getResources().getString(R.string.progress_loading));
    }

    public static void dismissDialog(Dialog dialog) {
        if (dialog != null) {
            dialog.dismiss();
        }
    }


    public static ProgressDialog showSimpleProgressDialog(Activity activity, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(false);
        progressDialog.show();
        return progressDialog;
    }

    public static void dismissProgressDialog(ProgressDialog progressDialog) {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    public static void showToast(Context activity, String message) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show();
    }

    public static void showAlertDialog(Context context,
                                       String title,
                                       CharSequence message,
                                       String positiveButtonTag,
                                       String negativeButtonTag,
                                       DialogInterface.OnClickListener listener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
            builder.setTitle(title)
                    .setMessage(message);
            if (AppUtils.isContainsData(positiveButtonTag)) {
                builder.setPositiveButton(positiveButtonTag, listener);
            }
            if (AppUtils.isContainsData(negativeButtonTag)) {
                builder.setNegativeButton(negativeButtonTag, listener);
            }
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button bg = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            bg.setTextColor(Color.BLACK);
            Button bg1 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            bg1.setTextColor(Color.BLACK);
        } catch (Exception e) {
            //LogsManager.printLog(e);
        }
    }

    public static void showAlertDialog(Context context,
                                       String title, String message, String positiveButtonTag,
                                       String negativeButtonTag,
                                       String neutralButtonTag,
                                       DialogInterface.OnClickListener listener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(title)
                    .setMessage(message);
            if (AppUtils.isContainsData(positiveButtonTag)) {
                builder.setPositiveButton(positiveButtonTag, listener);
            }
            if (AppUtils.isContainsData(negativeButtonTag)) {
                builder.setNegativeButton(negativeButtonTag, listener);
            }
            if (AppUtils.isContainsData(neutralButtonTag)) {
                builder.setNeutralButton(neutralButtonTag, listener);
            }
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (Exception e) {
            //LogsManager.printLog(e);
        }
    }

    public static void showAlertDialog(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        showAlertDialog(context, title, message, "Okay", "", listener);
    }

    public static void showAlertDialog(Context context, String message, DialogInterface.OnClickListener listener) {
        showAlertDialog(context, "Message", message, "Okay", "", listener);
    }

    public static void showAndCloseAlertDialog(final Activity context, String message) {
        showAlertDialog(context, "Message", message, "Okay", "", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                context.finish();
            }
        });
    }

    public static void showAlertDialog(Context context, String message) {
        showAlertDialog(context, "Message", message, "Okay", "", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public static void showAlertDialog(Context context, String title, String message) {
        showAlertDialog(context, title, message, "Okay", "", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
    }

    public static Dialog showAlertDialog(Context context,
                                         String title,
                                         CharSequence message,
                                         String positiveButtonText,
                                         String negativeButtonText,
                                         String neutralButtonText,
                                         DialogActionInterface listener) {
        CustomAlertDialog dialog = new CustomAlertDialog(context, title, message, positiveButtonText, negativeButtonText, neutralButtonText, listener);

        dialog.create();
        dialog.show();
        dialog.setDialog(dialog);
        dialog.showPositiveButton();
        dialog.showNegativeButton();
        dialog.showNeutralButton();

        return dialog;

    }

    public static Dialog showAlertDialog(Context context,
                                         String title,
                                         CharSequence message,
                                         String positiveButtonText,
                                         String negativeButtonText,
                                         DialogActionInterface listener) {
        CustomAlertDialog dialog = new CustomAlertDialog(context, title, message, positiveButtonText, negativeButtonText, listener);

        dialog.create();
        dialog.show();
        dialog.setDialog(dialog);
        dialog.showPositiveButton();
        dialog.showNegativeButton();

        return dialog;

    }

    public static Dialog showAlertDialog(Context context,
                                         String title,
                                         CharSequence message,
                                         String positiveButtonText,
                                         DialogActionInterface listener) {
        CustomAlertDialog dialog = new CustomAlertDialog(context, title, message, positiveButtonText, listener);

        dialog.create();
        dialog.show();
        dialog.setDialog(dialog);
        dialog.showPositiveButton();

        return dialog;

    }

    public static void showAlertDialog(Context context, String title, String message, DialogActionInterface listener) {
        showAlertDialog(context, title, message, "Okay", listener);
    }

    public static void showAlertDialog(Context context, String message, DialogActionInterface listener) {
        showAlertDialog(context, "Message", message, "Okay", listener);
    }

    public static void openSettings(final Activity activity, String msg) {
        showAlertDialog(activity, "Permission", msg, "Settings", "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                        intent.setData(uri);
                        activity.startActivityForResult(intent, 123);
                        break;
                    default:
                        dialog.dismiss();
                        break;
                }
            }
        });
    }

    public static void callNumber(Activity activity, String mobileNumber) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL); // Action for what intent called for
        intent.setData(Uri.parse("tel: " + mobileNumber)); // Data with intent respective action on intent
        activity.startActivity(intent);
    }

    public static String getCurrentTime(String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(new Date());
    }

    public static Snackbar showSnackbar(View view, String message, String actionMsg, View.OnClickListener listener) {
        Snackbar sb = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE).setAction(actionMsg, listener);
        sb.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.md_theme_dark_outlineVariant));
        TextView tv = sb.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(view.getContext(), R.color.md_theme_light_onPrimary));
        sb.show();
        return sb;
    }

    public static Snackbar showSnackbar(View view, String message) {
        Snackbar sb = Snackbar.make(view, message, Snackbar.LENGTH_LONG);
        sb.getView().setBackgroundColor(ContextCompat.getColor(view.getContext(), R.color.md_theme_dark_outlineVariant));
        TextView tv = sb.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        tv.setTextColor(ContextCompat.getColor(view.getContext(), R.color.md_theme_light_onPrimary));
        sb.show();
        return sb;
    }

    public static void dismissSnackbar(Snackbar sb) {
        try {
            if (sb != null)
                sb.dismiss();
        } catch (Exception e) {
        }
    }


    public static void showDatePicker(Activity activity, DatePickerDialog.OnDateSetListener listener) {
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, listener, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    public static void showTimePicker(Activity activity, TimePickerDialog.OnTimeSetListener listener) {

        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, listener, mHour, mMinute, false);
        timePickerDialog.show();
    }


    public static long getCalMinDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.YEAR, 1900);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        return calendar.getTime().getTime();
    }

    public static long getDobMaxDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2014);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime().getTime();
    }

    public static long getPurchaseMaxDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        return calendar.getTime().getTime();
    }

    public static void openBrowser(Activity activity, String url, String title) {
        Intent i = new Intent(activity, WebViewActivity.class);
        i.putExtra("url", url);
        i.putExtra("title", title);
        activity.startActivity(i);
    }

    public static void disableView(View view) {
        if (view instanceof ViewGroup) {
            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++) {
                View v = ((ViewGroup) view).getChildAt(i);
                disableView(v);
            }
        } else {
            view.setEnabled(false);
        }
    }


    public static void enableView(View view) {
        if (view instanceof ViewGroup) {
            int count = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < count; i++) {
                View v = ((ViewGroup) view).getChildAt(i);
                enableView(v);
            }
        } else {
            view.setEnabled(true);
        }
    }

    public static void showGenericErrorDialog(View view, Context context) {
        showSnackbar(view, context.getString(R.string.generic_error_msg));
    }

    public static AlertDialog showPermissionRationale(Context context, String message,
                                                      DialogInterface.OnClickListener listener) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogStyle);
            builder.setTitle(R.string.text_permission)
                    .setMessage(message);
            builder.setPositiveButton(context.getString(R.string.text_accept), listener);
            builder.setNegativeButton(context.getString(R.string.text_deny), listener);
            builder.setCancelable(false);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button bg = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
            bg.setTextColor(Color.BLACK);
            Button bg1 = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            bg1.setTextColor(Color.BLACK);
            return dialog;
        } catch (Exception e) {
            //LogsManager.printLog(e);
        }
        return null;
    }

}