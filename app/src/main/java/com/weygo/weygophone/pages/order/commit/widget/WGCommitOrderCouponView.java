package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderCouponView extends JHRelativeLayout {

    TextView mNameTV;

    TextView mTitleTV;

    TextView mTipsTV;

    public WGCommitOrderCouponView(Context context) {
        super(context);
    }

    public WGCommitOrderCouponView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderCouponView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameTV = (TextView) findViewById(R.id.nameTV);
        mTipsTV = (TextView) findViewById(R.id.tipsTV);
        mTitleTV = (TextView) findViewById(R.id.titleTV);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object == null) {
            mNameTV.setVisibility(INVISIBLE);
            mTitleTV.setVisibility(VISIBLE);
            mTipsTV.setVisibility(VISIBLE);
        }
        else {
            mNameTV.setVisibility(VISIBLE);
            mTitleTV.setVisibility(INVISIBLE);
            mTipsTV.setVisibility(INVISIBLE);
            if (object instanceof WGCoupon) {
                WGCoupon item = (WGCoupon) object;
                mNameTV.setText(JHStringUtils.isNullOrEmpty(item.name) ? item.couponCode : item.name);
            }
        }
    }
}
