package com.weygo.common.tools;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHObject;

/**
 * Created by muma on 2016/12/7.
 */

public class JHLocalSettingUtils {

    static String localSettingName = "wegyo";

    private static SharedPreferences getSharePreferences(Context context) {
        return context.getSharedPreferences(localSettingName, Context.MODE_PRIVATE);
    }

    public static void setLocalSetting(Context context, JHObject object, String key) {
        SharedPreferences.Editor editor = getSharePreferences(context).edit();
        String value  = JSON.toJSONString(object);
        editor.putString(key, value);
        if (!editor.commit()) {
            JHWarningUtils.showToast(context, "Save error");
        }
    }

    public static void removeLocalSetting(Context context, String key) {
        SharedPreferences.Editor editor = getSharePreferences(context).edit();
        editor.remove(key);
        editor.commit();
    }

    public static JHObject getLocalSetting(Context context, String key, Class clazz) {
        SharedPreferences sharedPreferences = getSharePreferences(context);
        String value = sharedPreferences.getString(key, "");
        return (JHObject) JSON.parseObject(value, clazz);
    }
}
