package com.weygo.weygophone.pages.order.detail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDeliver;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDetail;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatusItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.widget.WGOrderNumberView;

import java.util.List;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderDetailUserInfoView extends LinearLayout {

    TextView mDeliverNameTV;

    TextView mDeliverAddressTV;

    TextView mDeliverPhoneTV;

    TextView mDeliverTimeTV;

    public WGOrderDetailUserInfoView(Context context) {
        this(context, null);
    }

    public WGOrderDetailUserInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderDetailUserInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDeliverNameTV = (TextView) findViewById(R.id.nameTV);
        mDeliverAddressTV = (TextView) findViewById(R.id.addressTV);
        mDeliverPhoneTV = (TextView) findViewById(R.id.phoneTV);
        mDeliverTimeTV = (TextView) findViewById(R.id.timeTV);
    }

    public void showWithData(WGOrderDeliver deliver) {
        if (deliver != null) {
            mDeliverNameTV.setText(deliver.userName);
            mDeliverAddressTV.setText(deliver.userAddress);
            mDeliverPhoneTV.setText(deliver.phone);
            mDeliverTimeTV.setText(deliver.deliverTime);
        }
    }
}
