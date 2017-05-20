package com.weygo.weygophone.logic;

import android.content.Context;
import android.util.Log;

import com.weygo.common.tools.JHLanguageUtils;
import com.weygo.common.tools.JHLocalSettingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Created by muma on 2016/12/14.
 */

public class WGChangeAppLanguageLogic {

    static boolean canChangeLanguage = true;

    static String kUserSetLanguageKey = "Weygo.App.Language";

    public static final int WGAppLanguageItalin = 1;
    public static final int WGAppLanguageChiness = 2;

    static Locale mCurrentAppLocale;

    static List mSupportLanguageList = Arrays.asList(WGAppLanguageItalin, WGAppLanguageChiness);

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
        Log.e("-------------", mCurrentAppLocale + "");
        if (mCurrentAppLocale == null) {
            Locale locale = context.getResources().getConfiguration().locale;
            String language = locale.getLanguage();
            if (!language.endsWith("zh") && !language.endsWith("it")) {
                mCurrentAppLocale = Locale.ITALIAN;
                JHLanguageUtils.setAppLanguage(context, mCurrentAppLocale);
            }
        }
        else {
            if (hasChangeAppLocale(context)) {
                JHLanguageUtils.setAppLanguage(context, mCurrentAppLocale);
            }
        }
    }

    public static void changeAppLanguage(Context context, int languageId, WGChangeLanguageCallBack callBack) {
        Locale locale;
        if (languageId == WGAppLanguageChiness) {
            locale = Locale.CHINESE;
        }
        else {
            locale = Locale.ITALIAN;
        }
        changeAppLanguage(context, locale, callBack);
    }

    static void changeAppLanguage(Context context, Locale locale, WGChangeLanguageCallBack callBack) {
        if (!canChangeLanguage) {
            return;
        }
        if (locale.equals(mCurrentAppLocale)) {
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
