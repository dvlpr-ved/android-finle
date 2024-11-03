package com.dkglabs.base.utils;

import com.dkglabs.base.manager.LogsManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateUtils {
/*
    public static void main(String[] args) {
        int i = 1;
        for(String monthName : getLastSixMonthList()){
            System.out.println("Month " + (i++) + ": " + monthName);
        }
    }*/


    public static String getNewDate(String initialFormat, String val, String finalFormat) {
        try {
            DateFormat utcFormat = new SimpleDateFormat(initialFormat);
            // utcFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

            Date date = utcFormat.parse(val);

            DateFormat pstFormat = new SimpleDateFormat(finalFormat);
            //pstFormat.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));
            return pstFormat.format(date);
        } catch (ParseException e) {
        }
        return val;
    }

    public static List<String> getLastSixMonthList() {
        List<String> months = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(AppConstants.EMI_MONTH_FORMAT, Locale.getDefault());

        for (int i = 0; i < 6; i++) {
            Date currentDate = calendar.getTime();
            String monthAndYear = sdf.format(currentDate);
            months.add(monthAndYear);
            calendar.add(Calendar.MONTH, -1); // Move to the previous month
        }

        return months;
    }

    public static Boolean matchMonthAndYear(String date1, String pattern1, String date2, String pattern2) {

        SimpleDateFormat sdf1 = new SimpleDateFormat(pattern1, Locale.getDefault());
        SimpleDateFormat sdf2 = new SimpleDateFormat(pattern2, Locale.getDefault());

        try {
            Date parsedDate1 = sdf1.parse(date1);
            Date parsedDate2 = sdf2.parse(date2);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(parsedDate1);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(parsedDate2);
            // Compare the two dates
            if ((calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH)) && (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR)))
                return true;

        } catch (Exception e) {
            LogsManager.printLog("Date parsing error: ", e.getMessage());
            return false;
        }

        return false;
    }
}
