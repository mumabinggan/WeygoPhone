package com.weygo.common.tools.network;

import com.weygo.common.base.JHObject;
import com.weygo.common.base.JHRequest;

import okhttp3.Call;

/**
 * Created by muma on 2016/11/30.
 */

public class JHNetworkUtils extends JHObject {

    private static volatile JHNetworkUtils mInstance;//单利引用

    static JHBaseNetworkInterface network = new JHOKHTTPNewwork();

    public static Call getAsyn(JHRequest request, Class clazz, JHResponseCallBack callBack) {
        return network.getAsync(request, clazz, callBack);
    }
}
