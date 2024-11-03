package com.dkglabs.base.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;

import com.dkglabs.base.interfaces.DialogActionInterface;
import com.dkglabs.base.R;
import com.dkglabs.base.utils.UIUtils;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

/**
 * Created by Himanshu Srivastava on 9/28/2022.
 */
public class CustomAlertDialog extends Dialog {


    private String positive;
    private String negative;
    private String neutral;

    private String title;
    private CharSequence message;

    private MaterialButton buttonPositive;
    private MaterialButton buttonNeutral;
    private MaterialButton buttonNegative;

    private DialogActionInterface listener;
    private Dialog dialog;

    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            listener.onDialogActionListener(dialog, Integer.parseInt(view.getTag().toString()));
        }
    };

    public CustomAlertDialog(Context context,
                             String title,
                             CharSequence message,
                             String positive,
                             String negative,
                             String neutral,
                             DialogActionInterface listener) {
        super(context);
        this.title = title;
        this.message = message;
        this.positive = positive;
        this.negative = negative;
        this.neutral = neutral;
        this.listener = listener;
    }

    public CustomAlertDialog(Context context,
                             String title,
                             CharSequence message,
                             String positive,
                             String negative,
                             DialogActionInterface listener) {
        super(context);
        this.title = title;
        this.message = message;
        this.positive = positive;
        this.negative = negative;
        this.listener = listener;
    }

    public CustomAlertDialog(Context context,
                             String title,
                             CharSequence message,
                             String positive,
                             DialogActionInterface listener) {
        super(context);
        this.title = title;
        this.message = message;
        this.positive = positive;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_alert_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initializeView();
    }

    private void initializeView() {
        buttonNeutral = findViewById(R.id.buttonNeutral);
        buttonNegative = findViewById(R.id.buttonNegative);
        buttonPositive = findViewById(R.id.buttonPositive);
        MaterialTextView textTitle = findViewById(R.id.textTitle);
        textTitle.setText(title);
        MaterialTextView textMessage = findViewById(R.id.textMessage);
        textMessage.setText(message);
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public void showPositiveButton() {
        buttonPositive.setText(positive);
        buttonPositive.setOnClickListener(mOnClickListener);
        UIUtils.showView(buttonPositive);
    }

    public void showNegativeButton() {
        buttonNegative.setText(negative);
        buttonNegative.setOnClickListener(mOnClickListener);
        UIUtils.showView(buttonNegative);
    }

    public void showNeutralButton() {
        buttonNeutral.setText(neutral);
        buttonNeutral.setOnClickListener(mOnClickListener);
        UIUtils.showView(buttonNeutral);
    }
}
