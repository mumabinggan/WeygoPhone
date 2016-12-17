package com.weygo.weygophone.logic;

import android.content.Context;
import android.util.Log;

import com.weygo.common.tools.JHLanguageUtils;
import com.weygo.common.tools.JHLocalSettingUtils;

import java.util.Locale;

/**
 * Created by muma on 2016/12/14.
 */

public class WGChangeAppLanguageLogic {

    static boolean canChangeLanguage = true;

    static String kUserSetLanguageKey = "Weygo.App.Language";

    static Locale mCurrentAppLocale;

    public static void resetAppLanguage(Context context, WGChangeLanguageCallBack callBack) {
        if (!canChangeLanguage) {
            return;
        }
        boolean changed = false;
        if (hasChangeAppLocale(context)) {
            changed = true;
            mCurrentAppLocale = (Locale) JHLocalSettingUtils.getLocalSetting(context, kUserSetLanguageKey, Locale.class);
        }
        if (callBack != null) {
            callBack.onCompletion(mCurrentAppLocale, changed);
        }
    }

    public static void initAppLanguage(Context context) {
        if (!canChangeLanguage) {
            return;
        }
        mCurrentAppLocale = (Locale) JHLocalSettingUtils.getLocalSetting(context, kUserSetLanguageKey, Locale.class);
        if (mCurrentAppLocale == null) {
            mCurrentAppLocale = Locale.ITALY;
            JHLocalSettingUtils.setLocalSetting(context, kUserSetLanguageKey, mCurrentAppLocale);
        }
        if (hasChangeAppLocale(context)) {
            JHLanguageUtils.setAppLanguage(context, mCurrentAppLocale);
        }
    }

    public static void changeAppLanguage(Context context, Locale locale, WGChangeLanguageCallBack callBack) {
        if (!canChangeLanguage) {
            return;
        }
        JHLocalSettingUtils.setLocalSetting(context, kUserSetLanguageKey, locale);
        mCurrentAppLocale = locale;
        if (callBack != null) {
            callBack.onCompletion(mCurrentAppLocale, true);
        }
    }

    public static boolean hasChangeAppLocale(Context context) {
        Locale currentLocale = context.getResources().getConfiguration().locale;
        return !(currentLocale.equals(mCurrentAppLocale));
    }
}
