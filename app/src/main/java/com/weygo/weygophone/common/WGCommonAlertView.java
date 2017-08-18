package com.weygo.weygophone.common;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommonAlertView extends AlertDialog.Builder {
    public WGCommonAlertView(Context context) {
        super(context);
    }

    public WGCommonAlertView(Context context, int themeResId) {
        super(context, themeResId);
    }

    public WGCommonAlertView setCustomMessage(String message) {
        this.setMessage(message);
        return this;
    }

    public WGCommonAlertView setCustomMessage(int resId) {
        this.setMessage(resId);
        return this;
    }

    public WGCommonAlertView setCustomPositiveButton(String btnTitle, DialogInterface.OnClickListener listener) {
        this.setPositiveButton(btnTitle, listener);
        return this;
    }

    public WGCommonAlertView setCustomPositiveButton(int resId, DialogInterface.OnClickListener listener) {
        this.setPositiveButton(resId, listener);
        return this;
    }

    public WGCommonAlertView setCustomNegativeButton(String btnTitle, DialogInterface.OnClickListener listener) {
        this.setNegativeButton(btnTitle, listener);
        return this;
    }

    public WGCommonAlertView setCustomNegativeButton(int resId, DialogInterface.OnClickListener listener) {
        this.setNegativeButton(resId, listener);
        return this;
    }

    public WGCommonAlertView setCustomCancelEnable(boolean enable) {
        setCancelable(true);
        return this;
    }

    public WGCommonAlertView showAlert() {
        AlertDialog dialog = create();
        dialog.show();
        return this;
    }
}
