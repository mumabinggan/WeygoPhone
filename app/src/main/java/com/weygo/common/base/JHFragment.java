package com.weygo.common.base;


import android.app.Dialog;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.weygo.common.tools.JHDialogUtils;
import com.weygo.common.tools.network.JHNetworkUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;

/**
 * Created by muma on 2016/12/7.
 */

public class JHFragment extends Fragment {

    Dialog mShowDialog;

    //Request
    public void getAsyn(JHRequest request, Class clazz, final JHResponseCallBack callBack) {
        final boolean showLoading = request.showsLoadingView;
        if (showLoading) {
            mShowDialog = JHDialogUtils.showLoadingDialog(getContext());
        }
        JHNetworkUtils.getInstance().getAsyn(request, clazz, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                if (showLoading) {
                    JHDialogUtils.hideLoadingDialog(mShowDialog);
                }
                if (callBack != null) {
                    callBack.onSuccess(result);
                }
            }

            @Override
            public void onFailure(JHRequestError error) {
                if (showLoading) {
                    JHDialogUtils.hideLoadingDialog(mShowDialog);
                }
                if (callBack != null) {
                    callBack.onFailure(error);
                }
            }
        });
    }

    public void postAsyn(JHRequest request, Class clazz, final JHResponseCallBack callBack) {
        final boolean showLoading = request.showsLoadingView;
        if (showLoading) {
            mShowDialog = JHDialogUtils.showLoadingDialog(getContext());
        }
        JHNetworkUtils.getInstance().postAsyn(request, clazz, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                if (showLoading) {
                    JHDialogUtils.hideLoadingDialog(mShowDialog);
                }
                if (callBack != null) {
                    callBack.onSuccess(result);
                }
            }

            @Override
            public void onFailure(JHRequestError error) {
                if (showLoading) {
                    JHDialogUtils.hideLoadingDialog(mShowDialog);
                }
                if (callBack != null) {
                    callBack.onFailure(error);
                }
            }
        });
    }

}
