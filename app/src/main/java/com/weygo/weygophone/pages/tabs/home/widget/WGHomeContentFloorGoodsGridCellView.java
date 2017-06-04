package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.weygo.common.base.JHInterface;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

/**
 * Created by muma on 2017/6/5.
 */

public class WGHomeContentFloorGoodsGridCellView extends JHLinearLayout {

    WGHomeContentFloorGoodsGridItemView mLeftItemView;

    WGHomeContentFloorGoodsGridItemView mRightItemView;

    OnPurchaseListener mListener;
    public void setListener(OnPurchaseListener listener) {
        mListener = listener;
    }

    public interface OnPurchaseListener extends JHInterface {
        void onPurchase(WGHomeFloorContentGoodItem item);
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

    void handlePurchase(WGHomeFloorContentGoodItem item) {
        if (mListener != null) {
            mListener.onPurchase(item);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftItemView = (WGHomeContentFloorGoodsGridItemView) findViewById(R.id.itemViewLeft);
        mRightItemView = (WGHomeContentFloorGoodsGridItemView) findViewById(R.id.itemViewRight);
        mLeftItemView.setListener(new WGHomeContentFloorGoodsGridItemView.OnPurchaseListener() {
            @Override
            public void onPurchase(WGHomeFloorContentGoodItem item) {
                handlePurchase(item);
            }
        });
        mRightItemView.setListener(new WGHomeContentFloorGoodsGridItemView.OnPurchaseListener() {
            @Override
            public void onPurchase(WGHomeFloorContentGoodItem item) {
                handlePurchase(item);
            }
        });
    }
}
