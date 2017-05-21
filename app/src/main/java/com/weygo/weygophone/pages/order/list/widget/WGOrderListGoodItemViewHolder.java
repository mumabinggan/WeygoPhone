package com.weygo.weygophone.pages.order.list.widget;

import android.view.View;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.weygophone.pages.collection.widget.WGGoodListView;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListGoodItemViewHolder extends JHBaseViewHolder {

    WGOrderListGoodItemView mItemView;

    public WGOrderListGoodItemViewHolder(View view) {
        super(view);
        mItemView = (WGOrderListGoodItemView) view;
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGOrderGoodItem) {
            WGOrderGoodItem item = (WGOrderGoodItem) object;
            mItemView.showWithData(item);
        }
    }
}
