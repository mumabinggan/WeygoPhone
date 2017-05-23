package com.weygo.weygophone.pages.order.detail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.collection.widget.WGGoodListView;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDeliver;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDetail;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderDetailGoodItemView extends RelativeLayout {

    WGGoodListView mGoodItemView;

    TextView mTotalPriceTV;

    TextView mPriceTV;

    TextView mReducePriceTV;

    public WGOrderDetailGoodItemView(Context context) {
        this(context, null);
    }

    public WGOrderDetailGoodItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderDetailGoodItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mGoodItemView = (WGGoodListView) findViewById(R.id.goodItemView);
        mTotalPriceTV = (TextView) findViewById(R.id.totalPriceTV);
        mPriceTV = (TextView) findViewById(R.id.priceTV);
        mReducePriceTV = (TextView) findViewById(R.id.reducePriceTV);
    }

    public void showWithData(WGOrderGoodItem orderGoodItem) {
        if (orderGoodItem != null) {
            mGoodItemView.showWithData(orderGoodItem);
            mTotalPriceTV.setText(orderGoodItem.orderCurrentPrice);
            mPriceTV.setText(orderGoodItem.orderPrice);
            mReducePriceTV.setText(orderGoodItem.orderReducePrice);
        }
    }
}
