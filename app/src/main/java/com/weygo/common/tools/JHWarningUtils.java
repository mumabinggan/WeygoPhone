package com.weygo.common.tools;

import android.content.Context;
import android.widget.Toast;

import com.weygo.common.base.JHObject;
import com.weygo.weygophone.WGApplication;

/**
 * Created by muma on 2016/11/29.
 */

public class JHWarningUtils extends JHObject {

    public static void showToast(Context ctx, String message, boolean shortTime) {
        Toast.makeText(ctx, message, shortTime ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context ctx, String message) {
        showToast(ctx, message, false);
    }

    public static void showToast(String message) {
        showToast(WGApplication.getContext(), message);
    }

}
