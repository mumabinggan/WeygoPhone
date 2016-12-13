package com.weygo.common.tools.network;

import android.os.Handler;
import android.util.Log;

import com.weygo.common.base.JHRequest;
import com.weygo.common.base.JHResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by muma on 2016/11/30.
 */

public class JHOKHTTPNewwork implements JHBaseNetworkInterface {

    private OkHttpClient mOkHttpClient;//okHttpClient 实例

    private Handler okHttpHandler;//全局处理子线程和M主线程通信

    public JHOKHTTPNewwork() {
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();
        //初始化Handler
        okHttpHandler = new Handler();
    }

    @Override
    public Call getAsync(JHRequest originRequest, Class clazz, final JHResponseCallBack callBack) {
//        final Request request = new Request.Builder()
//                .get()
//                .tag(this)
//                .url("http://apis.baidu.com/apistore/weatherservice/citylist")
//                .build();
        final Request request = new Request.Builder()
                .get()
                .tag(originRequest)
                .url(originRequest.url())
                .build();
        final Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                failCallBack(call, callBack, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                successCallBack(call, response, callBack);
            }
        });
        return call;
    }

    void failCallBack(Call call, final JHResponseCallBack callBack, IOException e) {
        Log.e("failCallBack", "dasdfasdf");
        okHttpHandler.post(new Runnable() {
            @Override
            public void run() {
                if (callBack != null) {
                    callBack.onFailure(new JHRequestError());
                }
            }
        });
    }

    void successCallBack(Call call, Response response, final JHResponseCallBack callBack) {
        if (response.code() == 200) {
            try {
                Log.e("haha", response.body().string());
            } catch (IOException e) {
                //e.printStackTrace();
            }
            okHttpHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (callBack != null) {
                        callBack.onSuccess(new JHResponse());
                    }
                }
            });
        }
        else {
            Log.e("fail", "dasdfasdf");
            callBack.onFailure(new JHRequestError());
        }
    }
}
