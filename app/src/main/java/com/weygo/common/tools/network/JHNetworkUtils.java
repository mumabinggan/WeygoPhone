package com.weygo.common.tools.network;

import com.weygo.common.base.JHObject;
import com.weygo.common.base.JHRequest;

import okhttp3.Call;

/**
 * Created by muma on 2016/11/30.
 */

public class JHNetworkUtils extends JHObject {

    private static final JHNetworkUtils ourInstance = new JHNetworkUtils();

    public static JHNetworkUtils getInstance() {
        return ourInstance;
    }

    JHBaseNetworkInterface network = null;

    private JHNetworkUtils() {
        super();
        network = new JHOKHTTPNewwork();
    }

    public Call getAsyn(JHRequest request, Class clazz, JHResponseCallBack callBack) {
        return network.getAsync(request, clazz, callBack);
    }

    public Call postAsyn(JHRequest request, Class clazz, JHResponseCallBack callBack) {
        return network.postAsync(request, clazz, callBack);
    }


}
