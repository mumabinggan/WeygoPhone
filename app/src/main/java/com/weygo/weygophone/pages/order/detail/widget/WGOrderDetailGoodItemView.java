package com.weygo.weygophone.pages.order.detail.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.tools.JHTextViewUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.collection.widget.WGGoodListView;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDeliver;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDetail;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorBaseContentItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderDetailGoodItemView extends RelativeLayout {

    WGGoodListView mGoodItemView;

    TextView mTotalPriceTitelTV;
    TextView mTotalPriceTV;

    TextView mPriceTV;

    TextView mReducePriceTV;

    public interface OnItemListener {
        void onPurchase(WGHomeFloorContentGoodItem item, View view, Point fromPoint);
        //void onGoodItem(WGHomeFloorContentGoodItem item);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

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
        mTotalPriceTitelTV = (TextView) findViewById(R.id.totalPriceTitleTV);
        mTotalPriceTV = (TextView) findViewById(R.id.totalPriceTV);
        mPriceTV = (TextView) findViewById(R.id.priceTV);
        mReducePriceTV = (TextView) findViewById(R.id.reducePriceTV);
        mGoodItemView.setListener(new WGGoodListView.GoodListItemOnListener() {
            @Override
            public void onTouchShopCart(WGHomeFloorContentGoodItem item, View view, Point point) {
                if (mListener != null) {
                    mListener.onPurchase(item, view, point);
                }
            }

            @Override
            public void onTouchItem(WGHomeFloorContentGoodItem item) {

            }
        });
    }

    public void showWithData(WGOrderGoodItem orderGoodItem) {
        if (orderGoodItem != null) {
            mGoodItemView.showWithData(orderGoodItem);
            mTotalPriceTV.setText(orderGoodItem.orderCurrentPrice);
            mPriceTV.setText(orderGoodItem.orderPrice);
            mReducePriceTV.setText(orderGoodItem.orderReducePrice);
            JHTextViewUtils.addMiddleLine(mReducePriceTV);
        }
    }

    public void setVisibility(boolean visibility) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)getLayoutParams();
        if (visibility) {
            param.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
            param.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            setVisibility(View.VISIBLE);
        }
        else {
            param.height = 0;
            param.width = 0;
            setVisibility(View.GONE);
        }
    }
}
