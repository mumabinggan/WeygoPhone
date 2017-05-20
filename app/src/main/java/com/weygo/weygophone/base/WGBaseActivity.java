package com.weygo.weygophone.base;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHActivity;
import com.weygo.common.base.recyclerview.LoadingFooter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.logic.WGChangeAppLanguageLogic;
import com.weygo.weygophone.logic.WGChangeLanguageCallBack;

import java.util.Locale;

/**
 * Created by muma on 2016/12/14.
 */

public class WGBaseActivity extends JHActivity {

    final String WGNotificationTypeKey = "com.weygo";

    ImageView mReturnImageView;

    TextView mTitleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //initState();
//        SystemBarTintManager tintManager = new SystemBarTintManager(this);
//        // enable status bar tint
//        tintManager.setStatusBarTintEnabled(true);
//        // enable navigation bar tint
//        tintManager.setNavigationBarTintEnabled(true);

        //change app language
        WGChangeAppLanguageLogic.resetAppLanguage(this, new WGChangeLanguageCallBack() {
            @Override
            public void onCompletion(Locale currentLocal, boolean changed) {

            }
        });
    }

    private void initState() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mReturnImageView = (ImageView) findViewById(R.id.returnImageView);
        if (mReturnImageView != null) {
            mReturnImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleReturn();
                }
            });
        }

        mTitleTextView = (TextView) findViewById(R.id.titleTextView);
    }

    public void handleReturn() {
        finish();
    }

    @Override
    public boolean useActivityCollector() {
        return false;
    }

    //子类重写
    public void handleImmediatelyLoadBroadcast(Context context, Intent intent) {
        super.handleImmediatelyLoadBroadcast(context, intent);
        int notification = intent.getIntExtra(WGNotificationTypeKey, -1);
        didReceivedNotification(notification);
    }

    public void sendNotification(int notification) {
        Intent intent = new Intent(super.mRefreshAction);
        intent.putExtra(WGNotificationTypeKey, notification);
        sendBroadcast(intent);
    }
}
