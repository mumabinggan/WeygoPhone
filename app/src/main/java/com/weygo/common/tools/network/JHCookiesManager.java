package com.weygo.common.tools.network;

import android.util.Log;

import com.weygo.weygophone.WGApplication;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by muma on 2017/11/30.
 */

public class JHCookiesManager implements CookieJar {

    private final JHPersistentCookieStore cookieStore = new JHPersistentCookieStore(WGApplication.getContext());

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
