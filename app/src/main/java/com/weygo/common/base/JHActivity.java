package com.weygo.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.weygo.common.tools.JHActivityCollector;
import com.weygo.common.tools.JHDialogUtils;
import com.weygo.common.tools.JHStatusBarUtils;
import com.weygo.common.tools.network.JHNetworkUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2016/12/7.
 */

public class JHActivity extends FragmentActivity {

    Dialog mShowDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set screen orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //set statusbar color
        JHStatusBarUtils.setWindowStatusBarColor(this, R.color.navigationBar_background);
        //add activity to Collector
        if (useActivityCollector()) {
            JHActivityCollector.addActivity(this);
        }

        initData();
        initSubView();
    }

    private void initData() {

    }

    private void initSubView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //remove activity to Collector
        if (useActivityCollector()) {
            JHActivityCollector.removeActivity(this);
        }
    }

    public boolean useActivityCollector() {
        return true;
    }

    //Request
    public void getAsyn(JHRequest request, Class clazz, final JHResponseCallBack callBack) {
        final boolean showLoading = request.showsLoadingView;
        if (showLoading) {
            mShowDialog = JHDialogUtils.showLoadingDialog(this);
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
            mShowDialog = JHDialogUtils.showLoadingDialog(this);
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
