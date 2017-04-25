package com.weygo.common.tools.network;

import com.weygo.common.base.JHInterface;
import com.weygo.common.base.JHRequest;

import okhttp3.Call;

/**
 * Created by muma on 2016/11/30.
 */

public interface JHBaseNetworkInterface extends JHInterface {

//    public void getSync(JHRequest request, Class clazz, JHResponse response, final JHRequestCallBack<T> callBack);

    public Call getAsync(JHRequest request, final Class clazz, final JHResponseCallBack callBack);

    public Call postAsync(JHRequest request, final Class clazz, final JHResponseCallBack callBack);

//    public void postSync(JHRequest request, Class clazz, JHResponse response, final JHRequestCallBack<T> callBack);
//
//    public void postAsync(JHRequest request, Class clazz, JHResponse response, final JHRequestCallBack<T> callBack);
}
