package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHInterface;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.widget.WGDiscountView;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentFloorGoodsGridItemView extends JHRelativeLayout {

    WGHomeFloorContentGoodItem mData;

    ImageView mImageView;

    TextView mNameTextView;

    TextView mCurrentPriceTextView;

    TextView mPriceTextView;

    TextView mSpecificationTV;

    TextView mPurchaseTV;

    TextView mUnPurchaseTV;

    WGDiscountView mDiscountView;

    OnPurchaseListener mListener;
    public void setListener(OnPurchaseListener listener) {
        mListener = listener;
    }

    public interface OnPurchaseListener extends JHInterface {
        void onPurchase(WGHomeFloorContentGoodItem item, View view, Point point);
    }

    public WGHomeContentFloorGoodsGridItemView(Context context) {
        super(context);
    }

    public WGHomeContentFloorGoodsGridItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentFloorGoodsGridItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    void handlePurchase(View view) {
        if (mListener != null) {
            mListener.onPurchase(mData, view, null);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNameTextView = (TextView) findViewById(R.id.nameTextView);
        mCurrentPriceTextView = (TextView) findViewById(R.id.currentPriceTextView);
        mPriceTextView = (TextView) findViewById(R.id.priceTextView);
        mPriceTextView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        mSpecificationTV = (TextView) findViewById(R.id.specificationTV);
        mPurchaseTV = (TextView) findViewById(R.id.purchaseTV);
        mPurchaseTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePurchase(v);
            }
        });
        mUnPurchaseTV = (TextView) findViewById(R.id.unPurchaseTV);
        mDiscountView = (WGDiscountView) findViewById(R.id.discountView);
    }

    public void showWithData(Object data) {
        if (data instanceof WGHomeFloorContentGoodItem) {
            WGHomeFloorContentGoodItem item = (WGHomeFloorContentGoodItem) data;
            mData = item;
            JHImageUtils.getInstance().loadImage(item.pictureURL,
                    R.drawable.common_image_loading_small, mImageView);
            mNameTextView.setText(item.name);
            mCurrentPriceTextView.setText(item.currentPrice);
            mPriceTextView.setText(item.price);
            mSpecificationTV.setText(item.specification);
            if (item.inStock == 1) {
                mPurchaseTV.setVisibility(VISIBLE);
                mUnPurchaseTV.setVisibility(GONE);
            }
            else {
                mPurchaseTV.setVisibility(GONE);
                mUnPurchaseTV.setVisibility(VISIBLE);
            }
            mDiscountView.setDiscountText(item.discount);
        }
    }
}
