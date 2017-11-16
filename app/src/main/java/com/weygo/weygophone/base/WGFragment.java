package com.weygo.weygophone.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHFragment;

/**
 * Created by muma on 2016/12/14.
 */

public class WGFragment extends JHFragment {

    protected void finishRefresh(TwinklingRefreshLayout refreshView, boolean refresh, boolean pulling) {
        if (refresh) {
            refreshView.finishRefreshing();
        }
        else {
            refreshView.finishLoadmore();
        }
    }
}
