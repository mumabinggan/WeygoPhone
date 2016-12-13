package com.weygo.common.base;

import android.app.Activity;
import android.os.Bundle;

import com.weygo.common.tools.JHActivityCollector;

/**
 * Created by muma on 2016/12/7.
 */

public class JHActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
