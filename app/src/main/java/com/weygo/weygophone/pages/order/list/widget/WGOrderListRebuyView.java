package com.weygo.weygophone.pages.order.list.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListRebuyView extends LinearLayout {

    Button mRebuyBtn;

    public WGOrderListRebuyView(Context context) {
        this(context, null);
    }

    public WGOrderListRebuyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderListRebuyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRebuyBtn = (Button) findViewById(R.id.rebuyBtn);
    }
}
