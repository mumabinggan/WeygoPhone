package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDetail;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementTips;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderDeliverTipsView extends JHLinearLayout {

    TextView mDeliverTipsTV;

    public WGCommitOrderDeliverTipsView(Context context) {
        super(context);
    }

    public WGCommitOrderDeliverTipsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderDeliverTipsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDeliverTipsTV = (TextView) findViewById(R.id.deliverTipsTV);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGSettlementTips) {
            WGSettlementTips item = (WGSettlementTips) object;
            mDeliverTipsTV.setText(item.orderChangeTip);
        }
    }
}
