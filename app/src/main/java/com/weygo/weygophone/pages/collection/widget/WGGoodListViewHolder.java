package com.weygo.weygophone.pages.collection.widget;

import android.content.Context;
import android.view.View;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.weygophone.pages.collection.widget.WGGoodListView;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

/**
 * Created by muma on 2017/5/19.
 */

public class WGGoodListViewHolder extends JHBaseViewHolder {

    WGGoodListView mItemView;

    public WGGoodListViewHolder(View view) {
        super(view);
        mItemView = (WGGoodListView) view;
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGHomeFloorContentGoodItem) {
            WGHomeFloorContentGoodItem item = (WGHomeFloorContentGoodItem) object;
            mItemView.showWithData(item);
        }
    }
}
