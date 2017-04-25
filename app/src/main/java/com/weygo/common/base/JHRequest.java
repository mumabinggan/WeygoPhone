package com.weygo.common.base;

import android.app.MediaRouteActionProvider;
import android.util.Log;

import com.alibaba.fastjson.annotation.JSONField;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.RequestBody;

/**
 * Created by muma on 2016/11/29.
 */

public class JHRequest extends JHObject {

    @JSONField(serialize=false)
    public boolean showsLoadingView = true;

    @JSONField(serialize=false)
    String loadingMessage = null;

    @JSONField(serialize=false)
    //单位是s
    long timeoutInterval = 0;

    // eg. http, https, default : "http"
    public String scheme() {
        return "http";
    }

    // eg. www.example.com, default ""
    public String host() {
        return "";
    }

    // eg. 80, 443 etc. default "80"
    public String port() {
        return "80";
    };

    // eg. app/ default ""
    public String path() {
        return "";
    };

    // eg. getUser.rest etc. default : ""
    public String api() {
        return "";
    };

    // no need to override, except your url is not format with: scheme://domain/path/api
    public String url() {
        return scheme() + "://" + host() + ":" + port() + path() + api();
    };

    Set acceptContentTypes() {
        return null;
    }

    //public Map<String, String>
}
