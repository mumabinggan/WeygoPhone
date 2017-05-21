package com.weygo.weygophone.pages.order.list.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.list.adapter.WGOrderListGoodsAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListGoodItemView extends RelativeLayout {

    ImageView mImageView;

    TextView mCountTextView;

    TextView mNameTextView;

    TextView mCurrentPriceTextView;

    TextView mPriceTextView;

    public WGOrderListGoodItemView(Context context) {
        this(context, null);
    }

    public WGOrderListGoodItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderListGoodItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mCountTextView = (TextView) findViewById(R.id.goodCountTextView);
        mNameTextView = (TextView) findViewById(R.id.nameTextView);
        mCurrentPriceTextView = (TextView) findViewById(R.id.currentPriceTextView);
        mPriceTextView = (TextView) findViewById(R.id.priceTextView);
        mPriceTextView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
    }

    public void showWithData(WGOrderGoodItem data) {
        if (data instanceof WGOrderGoodItem) {
            WGOrderGoodItem item = (WGOrderGoodItem) data;
            JHImageUtils.getInstance().loadImage(item.pictureURL,
                    R.drawable.common_image_loading_small, mImageView);
            mNameTextView.setText(item.name);
            mCurrentPriceTextView.setText(item.currentPrice);
            mPriceTextView.setText(item.price);
            mCountTextView.setText("x" + item.goodCount);
            mCountTextView.setVisibility(item.goodCount > 0 ? VISIBLE : INVISIBLE);
        }
    }
}
