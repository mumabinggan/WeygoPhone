package com.weygo.weygophone.pages.order.list.widget;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderItemPriceView extends RelativeLayout {

    TextView mDeliverPriceTextView;

    TextView mTotalPriceTextView;

    TextView mTotalCountTextView;

    public WGOrderItemPriceView(Context context) {
        this(context, null);
    }

    public WGOrderItemPriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderItemPriceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDeliverPriceTextView = (TextView) findViewById(R.id.deliverPriceTV);
        mTotalPriceTextView = (TextView) findViewById(R.id.totalPriceTV);
        mTotalCountTextView = (TextView) findViewById(R.id.totalCountTV);
    }

    public void showWithData(WGOrderListItem item) {
        if (item != null) {
            mDeliverPriceTextView.setText(item.deliverPrice);
            String totalString = JHResourceUtils.getInstance().getString(R.string.OrderList_Total);
            mTotalPriceTextView.setText(String.format(totalString, item.totalPrice));
            String totalCountString = JHResourceUtils.getInstance().getString(R.string.OrderList_How_Many_Good);
            if (item.goods != null) {
                mTotalCountTextView.setText(String.format(totalCountString, item.getGoodsCount()));
            }
        }
    }
}
