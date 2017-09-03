package com.weygo.weygophone;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.weygo.common.tools.JHDeviceUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.logic.WGChangeAppLanguageLogic;
import com.zopim.android.sdk.api.ZopimChat;

import java.util.HashMap;
import java.util.Map;

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

        //init Config
        initConfig();

        //initUtils
        initRequireUtils();
    }

    public void initConfig() {
        ZopimChat.init("3posW2IznifTuXsiwtKNJ2u6Su8uXDiG");
    }

    public static Context getContext() {
        return context;
    }

    private void initRequireUtils() {
        JHDeviceUtils.getInstance(context);
        JHResourceUtils.getInstance(context);
        JHImageUtils.getInstance(context);
        WGApplicationRequestUtils.getInstance(context);
        WGApplicationUserUtils.getInstance(context);
        WGApplicationGlobalUtils.getInstance(context);
    }
}
