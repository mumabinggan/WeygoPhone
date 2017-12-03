package com.weygo.common.base;

import android.content.Context;
import android.content.Intent;

/**
 * Created by muma on 2017/12/3.
 */

public class JHNotification {

    private static final JHNotification ourInstance = new JHNotification();

    Context mContext;

    public static JHNotification getInstance(Context context) {
        ourInstance.mContext = context;
        return ourInstance;
    }

    public void addNotification() {

    }

    public void postNotification(int type, Object data) {
        Intent intent = new Intent("dd");
        intent.putExtra("Name", "hellogv");
        intent.putExtra("Blog", "http://blog.csdn.net/hellogv");
        ourInstance.mContext.sendBroadcast(intent);
    }
}
