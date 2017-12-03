package com.weygo.weygophone.pages.order.detail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.common.tools.JHResourceUtils;
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
            String nameTitle = JHResourceUtils.getInstance()
                    .getString(R.string.OrderDetail_Deliver_Name);
            mDeliverNameTV.setText(String.format(nameTitle,deliver.userName));
            String addressTitle = JHResourceUtils.getInstance()
                    .getString(R.string.OrderDetail_Deliver_Address);
            mDeliverAddressTV.setText(String.format(addressTitle, deliver.userAddress));
            String phoneTitle = JHResourceUtils.getInstance()
                    .getString(R.string.OrderDetail_Deliver_Phone);
            mDeliverPhoneTV.setText(String.format(phoneTitle, deliver.phone));
            String timeTitle = JHResourceUtils.getInstance()
                    .getString(R.string.OrderDetail_Deliver_Time);
            mDeliverTimeTV.setText(String.format(timeTitle, deliver.deliverTime));
        }
    }
}
