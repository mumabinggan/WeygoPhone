package com.weygo.weygophone.pages.order.detail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDetail;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatus;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatusItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.widget.WGOrderNumberView;

import java.util.List;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderDetailDeliverStatusView extends RelativeLayout {

    WGOrderNumberView mOrderNumberTV;

    TextView mConfirmTimeTV;

    TextView mConfirmDesTV;

    TextView mDeliverTimeTV;

    TextView mDeliverDesTV;

    TextView mCompletionTimeTV;

    TextView mCompletionDesTV;

    public WGOrderDetailDeliverStatusView(Context context) {
        this(context, null);
    }

    public WGOrderDetailDeliverStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderDetailDeliverStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mOrderNumberTV = (WGOrderNumberView) findViewById(R.id.orderNumberView);
        mConfirmTimeTV = (TextView) findViewById(R.id.confirmTimeTV);
        mConfirmDesTV = (TextView) findViewById(R.id.confirmDesTV);
        mDeliverTimeTV = (TextView) findViewById(R.id.deliverTimeTV);
        mDeliverDesTV = (TextView) findViewById(R.id.deliverDesTV);
        mCompletionTimeTV = (TextView) findViewById(R.id.completionTimeTV);
        mCompletionDesTV = (TextView) findViewById(R.id.completionDesTV);
    }

    public void showWithData(WGOrderDetail orderDetail) {
        if (orderDetail != null) {
            WGOrderListItem item = new WGOrderListItem();
            item.sn = orderDetail.sn;
            item.status = orderDetail.status.curstatus();
            mOrderNumberTV.showWithData(item);

            List<WGOrderStatusItem> statusArray = orderDetail.status.status;
            if (statusArray.size() > 2) {
                mConfirmTimeTV.setText(statusArray.get(0).time);
                mConfirmDesTV.setText(statusArray.get(0).statusText);
                mDeliverTimeTV.setText(statusArray.get(1).time);
                mDeliverDesTV.setText(statusArray.get(1).statusText);
                mCompletionTimeTV.setText(statusArray.get(2).time);
                mCompletionDesTV.setText(statusArray.get(2).statusText);
            }
        }
    }
}
