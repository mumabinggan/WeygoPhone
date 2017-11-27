package com.weygo.weygophone.common;

import android.view.View;

import com.dyhdyh.widget.loading.bar.LoadingBar;
import com.weygo.weygophone.common.widget.WGLoadingFactory;

/**
 * Created by muma on 2017/11/27.
 */

public class WGLoadingView {
    static public void show(View parent) {
        WGLoadingFactory factory = new WGLoadingFactory();
        LoadingBar.make(parent, factory).show();
    }

    static public void hidden(View parent) {
        LoadingBar.cancel(parent);
    }
}
