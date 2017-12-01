package com.weygo.common.tools.network;

import android.util.Log;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.CookieCache;
import com.franmontiel.persistentcookiejar.persistence.CookiePersistor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

/**
 * Created by muma on 2017/12/1.
 */

public class JHPersistentCookieJar extends PersistentCookieJar {
    public JHPersistentCookieJar(CookieCache cache, CookiePersistor persistor) {
        super(cache, persistor);
    }

    @Override
    synchronized public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> list = super.loadForRequest(url);
        for (Cookie item : list) {
            Log.e("==cookie==", item.name());
            Log.e("==cookie==", item.value());
        }
        return list;
    }
}
