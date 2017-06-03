package com.weygo.common.tools;

import android.content.Context;

/**
 * Created by muma on 2017/5/10.
 */

public class JHResourceUtils {

    private Context mContext;
    private static final JHResourceUtils ourInstance = new JHResourceUtils();

    public static JHResourceUtils getInstance(Context context) {
        ourInstance.mContext = context;
        return ourInstance;
    }

    public static JHResourceUtils getInstance() {
        return ourInstance;
    }

    private JHResourceUtils() {
    }

    public String getString(int resId) {
        return mContext.getString(resId);
    }

    public float getDimension(int resId) {
        return mContext.getResources().getDimension(resId);
    }

    public int getColor(int resId) {
        return mContext.getResources().getColor(resId);
    }
}
