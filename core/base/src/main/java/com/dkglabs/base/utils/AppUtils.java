package com.dkglabs.base.utils;


import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.view.View;

import com.dkglabs.base.R;
import com.dkglabs.base.manager.LogsManager;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppUtils {
    /**
     * Check Collection for Empty
     *
     * @param collection
     * @return
     */
    public static boolean isCollectionEmpty(Collection<? extends Object> collection) {
        if (collection == null || collection.isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean isContainsData(String stringRes) {
        boolean isContianData = true;

        if (stringRes == null || stringRes.trim().length() == 0) {
            isContianData = false;
        }

        return isContianData;
    }

    public static String getCurrentDate(String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(true);
            return sdf.format(new Date());
        } catch (IllegalArgumentException ex) {
            SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.SERVER_FORMAT);
            sdf.setLenient(true);
            return sdf.format(new Date());
        }
    }

    public static Boolean isContainsData(Collection<? extends Object> collection) {
        boolean isContainData = true;
        boolean isCollectionEmpty = isCollectionEmpty(collection);

        if (isCollectionEmpty) {
            isContainData = false;
        }

        return isContainData;

    }

    /**
     * Is String Empty
     *
     * @param stringRes
     * @return
     */
    public static boolean isStringEmpty(String stringRes) {
        if (stringRes == null || stringRes.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    public static boolean isCollectionContainData(Collection<? extends Object> collection) {
        boolean isCollectionContainData = true;

        if (collection == null || collection.isEmpty()) {
            isCollectionContainData = false;
        }

        return isCollectionContainData;
    }

    public static int getInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double getDouble(String text) {
        try {
            return Double.parseDouble(text);
        } catch (Exception e) {
            return 0;
        }
    }

    public static File getFileFromBitmap(Context context, Bitmap bitmap) throws Exception {
        File file = new File(context.getExternalCacheDir(), "tempImage");
        file.createNewFile();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static Long getLong(String text) {
        try {
            return Long.parseLong(text);
        } catch (Exception e) {
            return 0l;
        }
    }

    public static String get2Decimal(String val) {
        if (val == null) {
            return "";
        }
        String[] arr = val.split("\\.");
        if (arr.length > 0) {
            return arr[0];
        }
        return val;
    }

    public static String getInMin(String val) {
        if (val == null) {
            return "0";
        }
        Double anInt = getDouble(val);
        return ((int) (anInt / 60)) + "";
    }

    public static String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            iStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            data = sb.toString();
            br.close();

        } catch (Exception e) {
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    public static Float getFloat(String value) {
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {
            return 0f;
        }
    }

    public static Date getCurrentDateDefault(String format, String date, Date defaultDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            sdf.setLenient(true);
            return sdf.parse(date);
        } catch (Exception e) {
            LogsManager.printLog("Exception in sdf ", e.getMessage());
        }
        return defaultDate;
    }

    public static boolean isEmiDueDatePass(String format, String data) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        Date date = null;
        try {
            date = sdf.parse(data);
        } catch (ParseException e) {
            return false;
        }
        if (System.currentTimeMillis() < date.getTime()) {
            return false;
        }

        return true;
    }

    public static boolean validateEmail(String email) {
        String regex = "^[A-Za-z0-9_.-]+@[A-Za-z0-9_.-]+[A-Za-z0-9]+[\\\\.][A-Za-z]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();

    }

    public static boolean validatePAN(String pan) {
        String regex = "[A-Z]{5}[0-9]{4}[A-Z]{1}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pan);
        return matcher.matches();

    }

    public static boolean validateDoB(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        try {
            Date d1 = sdf.parse(date);
            return true;
        } catch (Exception e) {
            LogsManager.printLog(e);
        }
        return false;
    }

    public static List reverseList(List list) {
        if (list != null && list.size() != 0)
            Collections.reverse(list);
        return list;
    }

    public static String getCurrentTimeMillis() {
        return String.valueOf(System.currentTimeMillis());
    }

    public static long getUTCMillisecond(String time, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(time);
            long utcMillis = date.getTime();
            return utcMillis;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return MaterialDatePicker.todayInUtcMilliseconds();
    }

    public static String getFormatDate(Date time, String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat.format(time);
    }

    public static String getFormatDefaultDate(Date time, String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(s);
        return simpleDateFormat.format(time);
    }

    public static boolean isInternetAvailable(Application application, View view) {
        final ConnectivityManager connMgr = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        if (view != null) {
            UIUtils.showSnackbar(view, application.getString(R.string.no_internet_message));
        }
        return false;
    }


    public static boolean isInternetAvailable(Application application, View view, String action, View.OnClickListener listener) {
        final ConnectivityManager connMgr = (ConnectivityManager) application.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        if (view != null) {
            UIUtils.showSnackbar(view, application.getString(R.string.no_internet_message), action, listener);
        }
        return false;
    }

    public static Double getConvertedInterest(int value) {
        return 0.5 * value;
    }

    public static Double getConvertedAmount(int value) {
        return 10000.0 * value;
    }

    public static Double getPower(double i, int n) {
        if (n == 0)
            return 1.0;
        return i * getPower(i, n - 1);
    }

    public static Uri getImageUri(Context context, Bitmap imageBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), imageBitmap, UUID.randomUUID().toString(), null);
        return Uri.parse(path);
    }

    public static String getBase64(String uriPath) {
        Uri uri = Uri.parse(uriPath);
        File file = new File(uri.getPath());
        String pathName = file.getPath();
        Bitmap bm = BitmapFactory.decodeFile(pathName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); // bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.NO_WRAP);
        return encodedImage;
    }

    public static File getFile(Context context, Uri uri) {
        File destinationFilename;
        try {
            destinationFilename = new File(context.getFilesDir().getPath() + File.separatorChar + queryName(context, uri));
        } catch (AssertionError e) {
            destinationFilename = new File(uri.getPath());
        }


        try (InputStream ins = context.getContentResolver().openInputStream(uri)) {
            createFileFromStream(ins, destinationFilename);
        } catch (Exception ex) {
            LogsManager.printLog("Save File", ex.getMessage());
            ex.printStackTrace();
        }
        return destinationFilename;
    }

    private static void createFileFromStream(InputStream ins, File destination) {
        try (OutputStream os = new FileOutputStream(destination)) {
            byte[] buffer = new byte[4096];
            int length;
            while ((length = ins.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
        } catch (Exception ex) {
            LogsManager.printLog("Save File", ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static String queryName(Context context, Uri uri) {
        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }


    public static String parseOneTimeCode(String message) {
        Pattern pattern = Pattern.compile("\\d{6}");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }
}
