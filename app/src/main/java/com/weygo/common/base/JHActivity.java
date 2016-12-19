package com.weygo.common.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.weygo.common.tools.JHActivityCollector;
import com.weygo.common.tools.JHStatusBarUtils;
import com.weygo.common.widget.JHNavigationBar;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2016/12/7.
 */

public class JHActivity extends Activity {

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
}
