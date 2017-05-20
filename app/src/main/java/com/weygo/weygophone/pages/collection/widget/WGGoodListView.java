package com.weygo.weygophone.pages.collection.widget;

import android.content.Context;
import android.graphics.Paint;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGInterface;
import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorBaseContentItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import org.w3c.dom.Text;

/**
 * Created by muma on 2017/5/19.
 */

public class WGGoodListView extends RelativeLayout {

    public interface GoodListItemOnListener extends WGInterface {
        void onTouchShopCart();
    }

    GoodListItemOnListener mListener;

    ImageView mImageView;

    TextView mCountTextView;

    TextView mNameTextView;

    TextView mBriefTextView;

    TextView mChineseTextView;

    TextView mCurrentPriceTextView;

    TextView mReducePriceTextView;

    TextView mPriceTextView;

    ImageView mAddCartImageView;

    View mAddCartView;

    public WGGoodListView(Context context) {
        super(context);
    }

    public WGGoodListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGGoodListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mCountTextView = (TextView) findViewById(R.id.goodCountTextView);
        mNameTextView = (TextView) findViewById(R.id.nameTextView);
        mBriefTextView = (TextView) findViewById(R.id.briefTextView);
        mChineseTextView = (TextView) findViewById(R.id.chineseTextView);
        mCurrentPriceTextView = (TextView) findViewById(R.id.currentPriceTextView);
        mReducePriceTextView = (TextView) findViewById(R.id.reducePriceTextView);
        mReducePriceTextView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
        mPriceTextView = (TextView) findViewById(R.id.priceTextView);

        mAddCartImageView = (ImageView) findViewById(R.id.shopCartBtn);
        mAddCartView = findViewById(R.id.shopCartView);
        final OnClickListener cartListener = new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onTouchShopCart();
                }
            }
        };
        mAddCartView.setOnClickListener(cartListener);
        mAddCartImageView.setOnClickListener(cartListener);
    }

    public void setInnerTouchListener(GoodListItemOnListener listener) {
        mListener = listener;
    }

    public void showWithData(WGObject data) {
        if (data instanceof WGHomeFloorContentGoodItem) {
            WGHomeFloorContentGoodItem item = (WGHomeFloorContentGoodItem) data;
            JHImageUtils.getInstance().loadImage(item.pictureURL,
                    R.drawable.common_image_loading_small, mImageView);
            mNameTextView.setText(item.name);
            mBriefTextView.setText(item.briefDescription);
            mChineseTextView.setText(item.chineseName);
            mCurrentPriceTextView.setText(item.currentPrice);
            mReducePriceTextView.setText(item.reducePrice);
            mReducePriceTextView.setVisibility(JHStringUtils.isNullOrEmpty(item.reducePrice) ? INVISIBLE : VISIBLE);
            mPriceTextView.setText(item.price);
            mReducePriceTextView.setVisibility(JHStringUtils.isNullOrEmpty(item.price) ? INVISIBLE : VISIBLE);
            if (data instanceof WGOrderGoodItem) {
                WGOrderGoodItem orderItem = (WGOrderGoodItem) data;
                mCountTextView.setText("x" + orderItem.goodCount);
                mCountTextView.setVisibility(orderItem.goodCount > 0 ? VISIBLE : INVISIBLE);
            }
        }
    }
}
