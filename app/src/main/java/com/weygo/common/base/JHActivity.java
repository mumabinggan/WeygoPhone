package com.weygo.common.base;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.weygo.common.tools.JHActivityCollector;
import com.weygo.common.tools.JHDialogUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStatusBarUtils;
import com.weygo.common.tools.network.JHNetworkUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.WGLoadingView;
import com.weygo.weygophone.common.widget.WGLoadingFactory;
import com.weygo.weygophone.pages.login.WGLoginActivity;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2016/12/7.
 */

public class JHActivity extends FragmentActivity {

    protected Dialog mShowDialog;

    public final String mRefreshAction = "com.muma.broadcast";
    final String JHRefreshNotificationKey = "refreshNotificationKey";
    public final String mBroadcastLoadTypeKey = "com.muma";
    public final int JHBroadcastLoadTypeLazy = 1;
    public final int JHBroadcastLoadTypeImmediately = 2;
    public final String mBroadcastDataKey = "com.muma.data";

    JHBroadcastReceiver mRefreshReceiver;

    List mRefreshNotificationList;

    public View mLoadingContentView;

    Handler mHandler;

    class JHBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive (Context context, Intent intent) {
            handleBroadcastLoadType(context, intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();

        initContentView();

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
        initBroadcast();
    }

    private void initBroadcast() {
        if (mRefreshReceiver == null) {
            mRefreshReceiver = new JHBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(mRefreshAction);
            registerReceiver(mRefreshReceiver, filter);
        }
    }

    public void initContentView() {

    }

    public void initData() {
        mRefreshNotificationList = new ArrayList();
    }

    public void initSubView() {

    }

    Runnable showLoadingRunnable = new Runnable(){
        @Override
        public void run() {
            mLoadingContentView = findViewById(R.id.contentView);
            if (mLoadingContentView != null) {
                WGLoadingView.show(mLoadingContentView);
            }
        }
    };

    Runnable hiddenLoadingRunnable = new Runnable(){
        @Override
        public void run() {
            mLoadingContentView = findViewById(R.id.contentView);
            if (mLoadingContentView != null) {
                WGLoadingView.hidden(mLoadingContentView);
            }
        }
    };

    public void showWarning(int resId) {
        showWarning(JHResourceUtils.getInstance().getString(resId));
    }

    public void showWarning(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void showLoading() {
        new Thread(){
            public void run(){
                mHandler.post(showLoadingRunnable);
            }
        }.start();
    }

    public void hideLoading() {
        new Thread(){
            public void run(){
                mHandler.post(hiddenLoadingRunnable);
            }
        }.start();
    }

    public interface OnTouchAlertListener extends JHInterface {
        void onTouchIndex(int which);
    }

    public void showAlert(Context context, int messageResId, final OnTouchAlertListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        AlertDialog alert = builder.setMessage(messageResId)
                .setNegativeButton(R.string.Collection_Delete_Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onTouchIndex(0);
                        }
                    }
                })
                .setPositiveButton(R.string.Collection_Delete_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onTouchIndex(1);
                        }
                    }
                })
                .create();
        alert.show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(mRefreshReceiver);
        if (mRefreshReceiver != null) {
            unregisterReceiver(mRefreshReceiver);
        }
        //remove activity to Collector
        if (useActivityCollector()) {
            JHActivityCollector.removeActivity(this);
        }
    }

    public void gotoHome() {
        Integer count = JHActivityCollector.getActivityList().size();
        for (int num = count - 1; num > 0; --num) {
            JHActivity activity = JHActivityCollector.getActivity(num);
            if (activity instanceof WGMainActivity) {
                return;
            }
            JHActivityCollector.removeActivity(activity);
            activity.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkRefreshNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        if (mRefreshReceiver != null) {
//            unregisterReceiver(mRefreshReceiver);
//        }
    }

    public boolean useActivityCollector() {
        return true;
    }


    public void getAsyn(JHRequest request, Class clazz, final JHResponseCallBack callBack) {
        final boolean showLoading = request.showsLoadingView;
        if (showLoading) {
            this.showLoading();
            //mShowDialog = JHDialogUtils.showLoadingDialog(this);
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

    public void postAsyn(final JHRequest request, Class clazz, final JHResponseCallBack callBack) {
        final boolean showLoading = request.showsLoadingView;
        if (showLoading) {
            this.showLoading();
            //mShowDialog = JHDialogUtils.showLoadingDialog(this);
        }
        JHNetworkUtils.getInstance().postAsyn(request, clazz, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                WGResponse response = (WGResponse)result;

                if (showLoading) {
                    hideLoading();
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

    private void handleBroadcastLoadType(Context context, Intent intent) {
        if (intent.getIntExtra(mBroadcastLoadTypeKey, -1) == JHBroadcastLoadTypeLazy) {
            //懒刷新
            _didReceivedRefreshNotification(intent.getIntExtra(JHRefreshNotificationKey, -1), intent);
        }
        else {
            //立即刷新
            handleImmediatelyLoadBroadcast(context, intent);
        }
    }

    //子类重写
    public void handleImmediatelyLoadBroadcast(Context context, Intent intent) {
        int notification = intent.getIntExtra(JHRefreshNotificationKey, -1);
        didReceivedNotification(notification, (JHObject) intent.getSerializableExtra(mBroadcastDataKey));
    }

    void _didReceivedRefreshNotification(int notification, Intent intent) {
        addRefreshNotification(notification);
    }

    void checkRefreshNotification() {
        if (mRefreshNotificationList.size() > 0) {
            for (int num = 0; num < mRefreshNotificationList.size(); ++num) {
                int notification = Integer.parseInt(mRefreshNotificationList.get(num).toString());
                didReceivedRefreshNotification(notification);
            }
            mRefreshNotificationList.clear();
        }
    }

    void addRefreshNotification(int notification) {
        if (!containsRefreshNotification(notification)) {
            mRefreshNotificationList.add(notification);
        }
    }

    public void sendRefreshNotification(int notification) {
        Intent intent = new Intent(mRefreshAction);
        intent.putExtra(JHRefreshNotificationKey, notification);
        intent.putExtra(mBroadcastLoadTypeKey, JHBroadcastLoadTypeLazy);
        sendBroadcast(intent);
    }

    public void sendRefreshNotification(int notification, JHObject data) {
        Intent intent = new Intent(mRefreshAction);
        intent.putExtra(JHRefreshNotificationKey, notification);
        intent.putExtra(mBroadcastLoadTypeKey, JHBroadcastLoadTypeLazy);
        intent.putExtra(mBroadcastDataKey, data);
        sendBroadcast(intent);
    }

    //接收到 Lazy加载后的处理
    public void didReceivedRefreshNotification(int notification) {

    }

    //接收到 Lazy加载后的处理
    public void didReceivedRefreshNotification(int notification, Intent intent) {
        didReceivedRefreshNotification(notification);
    }

    public void didReceivedNotification(int notification) {

    }

    public void didReceivedNotification(int notification, JHObject object) {
        didReceivedNotification(notification);
    }

    public boolean containsRefreshNotification(int notification) {
        return mRefreshNotificationList.contains(notification);
    }

    public void sendNotification(int notification) {
        Intent intent = new Intent(mRefreshAction);
        intent.putExtra(JHRefreshNotificationKey, notification);
        intent.putExtra(mBroadcastLoadTypeKey, JHBroadcastLoadTypeImmediately);
        sendBroadcast(intent);
    }

    public void sendNotification(int notification, JHObject data) {
        Intent intent = new Intent(mRefreshAction);
        intent.putExtra(JHRefreshNotificationKey, notification);
        intent.putExtra(mBroadcastLoadTypeKey, JHBroadcastLoadTypeImmediately);
        intent.putExtra(mBroadcastDataKey, data);
        sendBroadcast(intent);
    }
}
