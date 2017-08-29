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

    public static void setLocalSetting(Context context, String key, Object object) {
        SharedPreferences.Editor editor = getSharePreferences(context).edit();
        String value = JSON.toJSONString(object);
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

    public static Object getLocalSetting(Context context, String key, Class clazz) {
        SharedPreferences sharedPreferences = getSharePreferences(context);
        String value = sharedPreferences.getString(key, null);
        return JSON.parseObject(value, clazz);
    }

    public static void setBooleanLocalSetting(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getSharePreferences(context).edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static boolean getBooleanLocalSetting(Context context, String key) {
        SharedPreferences sharedPreferences = getSharePreferences(context);
        return sharedPreferences.getBoolean(key, false);
    }

    public static void setStringLocalSetting(Context context, String key, String value) {
        SharedPreferences.Editor editor = getSharePreferences(context).edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getStringLocalSetting(Context context, String key) {
        SharedPreferences sharedPreferences = getSharePreferences(context);
        return sharedPreferences.getString(key, "");
    }

    public static void setIntLocalSetting(Context context, String key, int value) {
        SharedPreferences.Editor editor = getSharePreferences(context).edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getIntLocalSetting(Context context, String key) {
        SharedPreferences sharedPreferences = getSharePreferences(context);
        return sharedPreferences.getInt(key, -1);
    }
}
