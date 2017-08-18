package com.weygo.weygophone.pages.shopcart.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGInterface;
import com.weygo.weygophone.pages.collection.widget.WGGoodListView;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

/**
 * Created by muma on 2017/8/18.
 */

public class WGShopCartItemView extends JHRelativeLayout {

    public interface GoodListItemOnListener extends WGInterface {
        void onTouchShopCart();
    }

    WGGoodListView.GoodListItemOnListener mListener;

    ImageView mImageView;

    TextView mNameTextView;

    TextView mBriefTextView;

    TextView mCurrentPriceTextView;

    TextView mReducePriceTextView;

    TextView mPriceTextView;

    public WGShopCartItemView(Context context) {
        super(context);
    }

    public WGShopCartItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGShopCartItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNameTextView = (TextView) findViewById(R.id.nameTextView);
        mBriefTextView = (TextView) findViewById(R.id.briefTextView);
        mCurrentPriceTextView = (TextView) findViewById(R.id.currentPriceTextView);
        mReducePriceTextView = (TextView) findViewById(R.id.reducePriceTextView);
        mReducePriceTextView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG);
        mPriceTextView = (TextView) findViewById(R.id.priceTextView);
    }

    public void setInnerTouchListener(WGGoodListView.GoodListItemOnListener listener) {
        mListener = listener;
    }

    @Override
    public void showWithData(Object object) {
        if (object instanceof WGHomeFloorContentGoodItem) {
            WGHomeFloorContentGoodItem item = (WGHomeFloorContentGoodItem) object;
            JHImageUtils.getInstance().loadImage(item.pictureURL,
                    R.drawable.common_image_loading_small, mImageView);
            mNameTextView.setText(item.name);
            mBriefTextView.setText(item.briefDescription);
            mCurrentPriceTextView.setText(item.currentPrice);
            mReducePriceTextView.setText(item.reducePrice);
            mReducePriceTextView.setVisibility(JHStringUtils.isNullOrEmpty(item.reducePrice) ? INVISIBLE : VISIBLE);
            mPriceTextView.setText(" " + item.price + " ");
            mPriceTextView.setVisibility(JHStringUtils.isNullOrEmpty(item.price) ? INVISIBLE : VISIBLE);
        }
    }
}
