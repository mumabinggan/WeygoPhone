package com.weygo.common.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/18.
 */

public class JHDeviceUtils {
    private static final JHDeviceUtils ourInstance = new JHDeviceUtils();

    Context mContext;

    public static JHDeviceUtils getInstance() {
        return ourInstance;
    }

    public static JHDeviceUtils getInstance(Context context) {
        ourInstance.mContext = context;
        return ourInstance;
    }

    private JHDeviceUtils() {
    }

    public String getVersion() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            return info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
