package com.weygo.weygophone;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.weygo.common.tools.JHLanguageUtils;
import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.weygophone.logic.WGChangeAppLanguageLogic;

import java.util.Locale;

/**
 * Created by muma on 2016/11/29.
 */

public class WGApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        WGChangeAppLanguageLogic.initAppLanguage(context);
        //JHLanguageUtils.setAppLanguage(context, Locale.SIMPLIFIED_CHINESE);
    }

    public static Context getContext() {
        return context;
    }
}
