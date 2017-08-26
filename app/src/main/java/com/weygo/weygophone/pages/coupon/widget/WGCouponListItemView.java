package com.weygo.weygophone.pages.coupon.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;

/**
 * Created by muma on 2017/8/23.
 */

public class WGCouponListItemView extends JHRelativeLayout {

    TextView mNameTV;

    TextView mPriceTV;

    TextView mCountTV;

    TextView mDesTV;

    TextView mTimeTV;

    WGCoupon mData;

    public interface OnItemListener {
        void onTouch();
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGCouponListItemView(Context context) {
        super(context);
    }

    public WGCouponListItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCouponListItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameTV = (TextView) findViewById(R.id.nameTV);
        mPriceTV = (TextView) findViewById(R.id.priceTV);
        mCountTV = (TextView) findViewById(R.id.countTV);
        mDesTV = (TextView) findViewById(R.id.desTV);
        mTimeTV = (TextView) findViewById(R.id.timeTV);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleTouch();
            }
        });
    }

    void handleTouch() {
        if (mListener != null) {
            mListener.onTouch();
        }
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        mData = (WGCoupon) object;
        mNameTV.setText(mData.name);
        mPriceTV.setText(mData.price);
        mCountTV.setText(mData.residueCount + "/" + mData.totalCount);
        mDesTV.setText(mData.briefDescription);
        mDesTV.setText(mData.couponCode + " " + mData.time);
    }
}
