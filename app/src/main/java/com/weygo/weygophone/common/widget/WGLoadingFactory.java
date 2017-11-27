package com.weygo.weygophone.common.widget;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dyhdyh.widget.loading.factory.LoadingFactory;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/11/27.
 */

public class WGLoadingFactory implements LoadingFactory {
    @Override
    public View onCreateView(ViewGroup parent) {
        View loadingView = LayoutInflater.from(parent.getContext()).inflate(R.layout.common_loading_dialog, parent,false);
        return loadingView;
    }
}
