package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorItem;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentFloorTitleView extends JHRelativeLayout {

    TextView mNameTV;

    TextView mBreifDesTV;

    public WGHomeContentFloorTitleView(Context context) {
        super(context);
    }

    public WGHomeContentFloorTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentFloorTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameTV = (TextView) findViewById(R.id.nameTV);
        mBreifDesTV = (TextView) findViewById(R.id.breifDesTV);
    }

    public void showWithData(Object data) {
        if (data instanceof WGHomeFloorItem) {
            WGHomeFloorItem item = (WGHomeFloorItem) data;
            mNameTV.setText(item.name);
            mBreifDesTV.setText(item.breifDescription);
            mBreifDesTV.setVisibility(JHStringUtils.isNullOrEmpty(item.breifDescription) ? GONE : VISIBLE);
        }
    }
}
