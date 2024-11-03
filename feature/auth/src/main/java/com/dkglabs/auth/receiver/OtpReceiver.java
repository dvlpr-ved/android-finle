package com.dkglabs.auth.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dkglabs.auth.interfaces.OtpListener;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

/**
 * Created by Himanshu Srivastava on 15,September,2023
 * Project e_savari
 */
public class OtpReceiver extends BroadcastReceiver {

    private OtpListener otpListener;

    public OtpReceiver() {

    }

    public void init(OtpListener otpListener) {
        this.otpListener = otpListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);
                switch (smsRetrieverStatus.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_SMS_MESSAGE);
                        if (otpListener != null) otpListener.onOtpSuccess(consentIntent);
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        if (otpListener != null) otpListener.onTimeout();
                        break;
                }
            }
        }
    }
}
