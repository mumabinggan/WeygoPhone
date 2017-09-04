package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.weygo.common.base.JHInterface;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/6/5.
 */

public class WGHomeContentFloorGoodsGridCellView extends JHLinearLayout {

    WGHomeContentFloorGoodsGridItemView mLeftItemView;

    WGHomeContentFloorGoodsGridItemView mRightItemView;

    List mData;

    OnPurchaseListener mListener;
    public void setListener(OnPurchaseListener listener) {
        mListener = listener;
    }

    public interface OnPurchaseListener extends JHInterface {
        void onPurchase(WGHomeFloorContentGoodItem item, View view, Point point);
        void onGoodDetail(WGHomeFloorContentGoodItem item);
    }

    public WGHomeContentFloorGoodsGridCellView(Context context) {
        super(context);
    }

    public WGHomeContentFloorGoodsGridCellView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentFloorGoodsGridCellView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void handlePurchase(WGHomeFloorContentGoodItem item, View view, Point point) {
        if (mListener != null) {
            mListener.onPurchase(item, view, point);
        }
    }

    void handleGoodDetail(WGHomeFloorContentGoodItem item) {
        if (mListener != null) {
            mListener.onGoodDetail(item);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftItemView = (WGHomeContentFloorGoodsGridItemView) findViewById(R.id.itemViewLeft);
        mLeftItemView.setListener(new WGHomeContentFloorGoodsGridItemView.OnPurchaseListener() {
            @Override
            public void onPurchase(WGHomeFloorContentGoodItem item, View view, Point point) {
                handlePurchase(item, view, point);
            }
        });
        mLeftItemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGoodDetail((WGHomeFloorContentGoodItem)mData.get(0));
            }
        });
        mRightItemView = (WGHomeContentFloorGoodsGridItemView) findViewById(R.id.itemViewRight);
        mRightItemView.setListener(new WGHomeContentFloorGoodsGridItemView.OnPurchaseListener() {
            @Override
            public void onPurchase(WGHomeFloorContentGoodItem item, View view, Point point) {
                handlePurchase(item, view, point);
            }
        });
        mRightItemView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleGoodDetail((WGHomeFloorContentGoodItem)mData.get(1));
            }
        });
    }

    @Override
    public void showWithArray(List list) {
        super.showWithArray(list);
        mData = list;
        int count = list.size();
        for (int num = 0; num < 2; ++num) {
            if (num == 0) {
                if (count > num) {
                    mLeftItemView.showWithData(list.get(num));
                    mLeftItemView.setVisibility(VISIBLE);
                }
                else {
                    mLeftItemView.setVisibility(GONE);
                }
            }
            if (num == 1) {
                if (count > num) {
                    mRightItemView.showWithData(list.get(num));
                    mRightItemView.setVisibility(VISIBLE);
                }
                else {
                    mRightItemView.setVisibility(GONE);
                }
            }
        }
    }
}
