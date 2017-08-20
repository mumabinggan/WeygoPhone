package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementConsumePrice;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderConsumptionView extends JHLinearLayout {

    TextView mTotalValueTV;

    TextView mDeliverValueTV;

    TextView mCouponValueTV;

    TextView mIntegralValueTV;

    public WGCommitOrderConsumptionView(Context context) {
        super(context);
    }

    public WGCommitOrderConsumptionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderConsumptionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTotalValueTV = (TextView) findViewById(R.id.totalValueTV);
        mDeliverValueTV = (TextView) findViewById(R.id.deliverValueTV);
        mCouponValueTV = (TextView) findViewById(R.id.couponValueTV);
        mIntegralValueTV = (TextView) findViewById(R.id.integralValueTV);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGSettlementConsumePrice) {
            WGSettlementConsumePrice item = (WGSettlementConsumePrice) object;
            mTotalValueTV.setText(item.totalPrice);
            mDeliverValueTV.setText(item.deliverPrice);
            mCouponValueTV.setText(item.couponPrice);
            mIntegralValueTV.setText(item.integralPrice);
        }
    }
}
