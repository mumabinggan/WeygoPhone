package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.common.widget.WGCommonHorizontalListView;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentFloorCountryColumnView extends WGCommonHorizontalListView {

    public WGHomeContentFloorCountryColumnView(Context context) {
        super(context);
    }

    public WGHomeContentFloorCountryColumnView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentFloorCountryColumnView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected int itemResId() {
        return R.layout.wghome_content_floor_country_column_item;
    }
}
