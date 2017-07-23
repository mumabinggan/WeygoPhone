package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.common.widget.WGCommonHorizontalListView;

import java.util.List;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentFloorGoodsColumnView extends WGCommonHorizontalListView {

    public WGHomeContentFloorGoodsColumnView(Context context) {
        super(context);
    }

    public WGHomeContentFloorGoodsColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentFloorGoodsColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected int itemResId() {
        return R.layout.wgorderlist_good_item;
    }
}
