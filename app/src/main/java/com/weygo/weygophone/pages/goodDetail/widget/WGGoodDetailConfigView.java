package com.weygo.weygophone.pages.goodDetail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.common.base.JHInterface;
import com.weygo.common.tools.JHTextViewUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;

import org.w3c.dom.Text;

/**
 * Created by muma on 2017/5/31.
 */

public class WGGoodDetailConfigView extends LinearLayout {

    TextView mNameTV;

    TextView mSpecificationTV;

    TextView mCurrentPriceTV;

    TextView mPriceTV;

    TextView mExpireTimeTV;

    public WGGoodDetailConfigView(Context context) {
        this(context, null);
    }

    public WGGoodDetailConfigView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGGoodDetailConfigView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameTV = (TextView) findViewById(R.id.nameTV);
        mSpecificationTV = (TextView) findViewById(R.id.specificationTV);
        mCurrentPriceTV = (TextView) findViewById(R.id.currentPriceTV);
        mPriceTV = (TextView) findViewById(R.id.priceTV);
        mExpireTimeTV = (TextView) findViewById(R.id.expireTimeTV);
    }

    public void showWithData(WGGoodDetail goodDetail) {
        mNameTV.setText(goodDetail.name);
        mSpecificationTV.setText(goodDetail.specification);
        mCurrentPriceTV.setText(goodDetail.currentPrice);
        mPriceTV.setText(goodDetail.price);
        JHTextViewUtils.addMiddleLine(mPriceTV);
        mExpireTimeTV.setText(goodDetail.expiredTime);
    }
}
