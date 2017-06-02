package com.weygo.weygophone.pages.goodDetail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;

/**
 * Created by muma on 2017/5/31.
 */

public class WGGoodDetailCommodityDescriptionView extends LinearLayout {

    TextView mDescriptionTV;

    public WGGoodDetailCommodityDescriptionView(Context context) {
        this(context, null);
    }

    public WGGoodDetailCommodityDescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGGoodDetailCommodityDescriptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDescriptionTV = (TextView) findViewById(R.id.commodityDescriptionTV);
    }

    public void showWithData(WGGoodDetail goodDetail) {
        mDescriptionTV.setText(goodDetail.productInfo);
    }
}
