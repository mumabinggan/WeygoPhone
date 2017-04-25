package com.weygo.weygophone.common;

import com.weygo.common.tools.JHActivityCollector;
import com.weygo.weygophone.WGMainActivity;

/**
 * Created by muma on 2017/4/24.
 */

public class WGApplicationPageUtils {
    private static final WGApplicationPageUtils ourInstance = new WGApplicationPageUtils();

    public static WGApplicationPageUtils getInstance() {
        return ourInstance;
    }

    private WGApplicationPageUtils() {
    }

    public WGMainActivity mainActivity() {
        return (WGMainActivity) JHActivityCollector.firstActivity();
    }

    public void switchTab(int tabIndex) {
        WGMainActivity mainActivity = this.mainActivity();
        mainActivity.setSelectedTab(tabIndex);
    }
}
