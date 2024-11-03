package com.dkglabs.apply_loan.utils;

import com.dkglabs.base.utils.AppConstants;
import com.dkglabs.base.utils.AppUtils;
import com.dkglabs.base.utils.DateUtils;

import java.util.Calendar;

public class EmiDateUtils {
    public static String getFirstEmiDate() {
        Calendar currentDate = Calendar.getInstance();
        int dayOfMonth = currentDate.get(Calendar.DAY_OF_MONTH);

        Calendar emiDate = Calendar.getInstance();

        if (dayOfMonth >= 1 && dayOfMonth <= 15) {
            emiDate.add(Calendar.MONTH, 1);
            emiDate.set(Calendar.DAY_OF_MONTH, 5);
        } else if (dayOfMonth >= 16) {
            emiDate.add(Calendar.MONTH, 1);
            emiDate.set(Calendar.DAY_OF_MONTH, 20);
        }

        int year = emiDate.get(Calendar.YEAR);
        int month = emiDate.get(Calendar.MONTH) + 1; // Calendar.MONTH is zero-based
        int day = emiDate.get(Calendar.DAY_OF_MONTH);
        return String.format("%04d-%02d-%02d", year, month, day);
    }
}
