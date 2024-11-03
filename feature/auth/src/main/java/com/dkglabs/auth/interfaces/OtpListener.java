package com.dkglabs.auth.interfaces;

import android.content.Intent;

/**
 * Created by Himanshu Srivastava on 15,September,2023
 * Project e_savari
 */
public interface OtpListener {
    void onOtpSuccess(Intent intent);

    void onTimeout();
}
