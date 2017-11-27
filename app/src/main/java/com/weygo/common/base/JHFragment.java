package com.weygo.common.base;


import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.weygo.common.tools.JHDialogUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.network.JHNetworkUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.WGLoadingView;

import java.util.List;

/**
 * Created by muma on 2016/12/7.
 */

public class JHFragment extends Fragment {

    //Dialog
    Dialog mShowDialog;

    View mLoadingContentView;

    View mParentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        initData();
        mParentView = inflater.inflate(fragmentResId(), container, false);
        initSubView(mParentView);
        loadRequest();
        return mParentView;
    }

    public int fragmentResId() {
        return 0;
    }

    public void initData() {

    }

    public void initSubView(View view) {

    }

    public void loadRequest() {

    }

    public void showWarning(int resId) {
        showWarning(JHResourceUtils.getInstance().getString(resId));
    }

    public void showWarning(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
    }

    public void showLoading() {
        mLoadingContentView = mParentView.findViewById(R.id.contentView);
        if (mLoadingContentView != null) {
            WGLoadingView.show(mLoadingContentView);
        }
    }

    public void hideLoading() {
        mLoadingContentView = mParentView.findViewById(R.id.contentView);
        if (mLoadingContentView != null) {
            WGLoadingView.hidden(mLoadingContentView);
        }
    }

    //Request
    public void getAsyn(JHRequest request, Class clazz, final JHResponseCallBack callBack) {
        final boolean showLoading = request.showsLoadingView;
        if (showLoading) {
            this.showLoading();
            //mShowDialog = JHDialogUtils.showLoadingDialog(getContext());
        }
        JHNetworkUtils.getInstance().getAsyn(request, clazz, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                if (showLoading) {
                    hideLoading();
                    //JHDialogUtils.hideLoadingDialog(mShowDialog);
                }
                if (callBack != null) {
                    callBack.onSuccess(result);
                }
            }

            @Override
            public void onFailure(JHRequestError error) {
                if (showLoading) {
                    hideLoading();
                    //JHDialogUtils.hideLoadingDialog(mShowDialog);
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
            this.showLoading();
            //mShowDialog = JHDialogUtils.showLoadingDialog(getContext());
        }
        JHNetworkUtils.getInstance().postAsyn(request, clazz, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("--onSuccess--", "success");
                Log.e("onSuccess", JSON.toJSONString(result));
                if (showLoading) {
                    hideLoading();
                    //JHDialogUtils.hideLoadingDialog(mShowDialog);
                }
                if (callBack != null) {
                    callBack.onSuccess(result);
                }
            }

            @Override
            public void onFailure(JHRequestError error) {
                if (showLoading) {
                    hideLoading();
                    //JHDialogUtils.hideLoadingDialog(mShowDialog);
                }
                if (callBack != null) {
                    callBack.onFailure(error);
                }
            }
        });
    }
}
