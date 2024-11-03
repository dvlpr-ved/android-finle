package com.dkglabs.base.interfaces;

import android.app.Dialog;

/**
 * Created by Himanshu Srivastava on 9/28/2022.
 */
public interface DialogActionInterface {

    public static final int POSITIVE_TAG = 1;
    public static final int NEGATIVE_TAG = 0;
    public static final int NEUTRAL_TAG = -1;

    public void onDialogActionListener(Dialog dialog, int tag);
}
