package com.weygo.common.base;

import android.app.Fragment;

import com.weygo.common.tools.network.JHNetworkUtils;
import com.weygo.common.tools.network.JHResponseCallBack;

/**
 * Created by muma on 2016/12/7.
 */

public class JHFragment extends Fragment {

    public void getAsyn(JHRequest request, Class clazz, JHResponseCallBack callBack) {
        JHNetworkUtils.getInstance().getAsyn(request, clazz, callBack);
    }

    public void postAsyn(JHRequest request, Class clazz, JHResponseCallBack callBack) {
        JHNetworkUtils.getInstance().postAsyn(request, clazz, callBack);
    }

}
