package com.weygo.weygophone.pages.order.list.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderNumberView extends RelativeLayout {

    TextView mOrderNumberTV;

    TextView mOrderStateTV;

    public WGOrderNumberView(Context context) {
        this(context, null);
    }

    public WGOrderNumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderNumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mOrderNumberTV = (TextView) findViewById(R.id.textView);
        mOrderStateTV = (TextView) findViewById(R.id.detailTextView);
    }

    public void showWithData(WGOrderListItem item) {
        if (item != null) {
            String orderNumberString = JHResourceUtils.getInstance().getString(R.string.OrderList_OrderNumber);
            Log.e("----------", orderNumberString);
            mOrderNumberTV.setText(String.format(orderNumberString, item.sn));
            mOrderStateTV.setText(item.status);
        }
    }
}
