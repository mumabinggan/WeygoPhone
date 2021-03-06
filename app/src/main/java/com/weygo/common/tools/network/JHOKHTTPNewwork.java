package com.weygo.common.tools.network;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.StringCodec;
import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.weygo.common.base.JHRequest;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.common.WGConstants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by muma on 2016/11/30.
 */

public class JHOKHTTPNewwork implements JHBaseNetworkInterface {

    private OkHttpClient mOkHttpClient;//okHttpClient 实例

    private Handler okHttpHandler;//全局处理子线程和M主线程通信

    private Context mContext;

    public JHOKHTTPNewwork(Context context) {
        mContext = context;
        ClearableCookieJar cookieJar =
                new JHPersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(30, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS)//设置写入超时时间
                .cookieJar(cookieJar)
                .build();
        //初始化Handler
        okHttpHandler = new Handler();
    }

    @Override
    public Call getAsync(JHRequest originRequest, final Class clazz, final JHResponseCallBack callBack) {
        StringBuffer paramsString = new StringBuffer();
        String jsonString = JSON.toJSONString(originRequest);
        Map<String, Object> paramsMap = JSON.parseObject(
                jsonString,new TypeReference<Map<String, Object>>(){} );
        for (Map.Entry<String, Object> m :paramsMap.entrySet()) {
            paramsString.append("&");
            paramsString.append(m.getKey());
            paramsString.append(m.getValue() + "");
        }
        //补全请求地址
        String requestUrl = String.format("%s%s", originRequest.url(), paramsString.toString());
//        Log.e("-requestUrl-", requestUrl);
//        List<Cookie> cookies = mOkHttpClient.cookieJar().loadForRequest(request.url());
        final Request request = new Request.Builder()
                .get()
                .tag(originRequest)
                .url(requestUrl)
//                .addHeader("Cookie", cookieStringValue())
                .build();
        //RequestBody.create(MEDIA_TYPE_JSON, params);
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failCallBack(call, callBack, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                successCallBack(call, response, clazz, callBack);
            }
        });
        return call;
    }

    @Override
    public Call postAsync(JHRequest originRequest, final Class clazz, final JHResponseCallBack callBack) {
        StringBuffer paramsString = new StringBuffer();
        String jsonString = JSON.toJSONString(originRequest);
        Map<String, String> paramsMap = JSON.parseObject(
                jsonString,new TypeReference<Map<String, String>>(){} );
        int pos = 0;
        for (String key : paramsMap.keySet()) {
            if (pos > 0) {
                paramsString.append("&");
            }
            paramsString.append(String.format("%s=%s", key, paramsMap.get(key)));
//            try {
//                //paramsString.append(String.format("%s=%s", key, paramsMap.get(key)));
//                //paramsString.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
            pos++;
        }
//        Log.e("---paramsString----", paramsString.toString());
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        Log.e("==originUrl==", originRequest.url());
        //补全请求地址
        RequestBody body = RequestBody.create(mediaType, paramsString.toString());
        final Request request = new Request.Builder()
                .post(body)
                .tag(originRequest)
                .url(originRequest.url())
//                .header("Cookie", cookieStringValue())
//                .header("Cookie", cookieStringValue())
//                                .addHeader("Cookie", "store=mobilecn")

//                .addHeader("Cookie", cookieStringValue())
//                .addHeader("User-Agent", "zhengy")
//                .removeHeader("User-Agent").addHeader("User-Agent",getUserAgent())
                .build();
        //RequestBody.create(MEDIA_TYPE_JSON, params);
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failCallBack(call, callBack, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                successCallBack(call, response, clazz, callBack);
            }
        });
        return call;
    }

    private  static String getUserAgent(){
        String userAgent = "";
        StringBuffer sb = new StringBuffer();
        userAgent = System.getProperty("http.agent");//Dalvik/2.1.0 (Linux; U; Android 6.0.1; vivo X9L Build/MMB29M)

        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }

        Log.e("User-Agent","User-Agent: "+ sb.toString());
        return sb.toString();
    }

    void failCallBack(Call call, final JHResponseCallBack callBack, IOException e) {
        Log.e("failCallBack", e.toString());
        System.out.println(e);
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onFailure(new JHRequestError());
                }
            }
        });
    }

    void successCallBack(Call call, Response response, Class clazz, final JHResponseCallBack callBack) {
        if (response.code() == 200) {
            String resultString = null;
            try {
                resultString = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (JHStringUtils.isNullOrEmpty(resultString)) {
                callBack.onSuccess(null);
                return;
            }
            final JHResponse resultResponse = (JHResponse) JSON.parseObject(resultString, clazz);
            if (resultResponse.reLogin()) {
                Intent intent = new Intent(WGConstants.WGReLoginAction);
                mContext.sendBroadcast(intent);
                return;
            }
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onSuccess(resultResponse);
                    }
                }
            });
        }
        else {
            callBack.onFailure(new JHRequestError());
        }
    }
}
