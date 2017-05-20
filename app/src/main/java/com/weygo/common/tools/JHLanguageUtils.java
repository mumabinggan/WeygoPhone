package com.weygo.common.tools;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

/**
 * Created by muma on 2016/12/14.
 */

public class JHLanguageUtils {
    static public void setAppLanguage(Context context, Locale locale) {
        Log.e("setAppLanguage", Locale.getDefault().getLanguage() + ":" + Locale.getDefault().getLanguage());
        //Locale newLocale = new Locale("zh");
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.locale = locale;
        resources.updateConfiguration(config, dm);
    }
}
