package com.weygo.weygophone;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.weygo.common.tools.JHDeviceUtils;
import com.weygo.common.tools.JHLanguageUtils;
import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.logic.WGChangeAppLanguageLogic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

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
        Log.e("application", "fssss");
        //SystemClock.sleep(8000);

        WGAPNSTest test = new WGAPNSTest();
        test.type = "1";
        Map<String ,String> map = new HashMap<String , String>();
        map.put("em_push_title", "你是谁");
        test.em_apns_ext = map;

        Log.e("-----------------", JSON.toJSONString(test));

        //initUtils
        initRequireUtils();
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
