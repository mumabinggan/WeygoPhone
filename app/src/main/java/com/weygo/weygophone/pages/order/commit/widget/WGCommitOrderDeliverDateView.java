package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.widget.WGCellStyle4View;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDeliverTime;
import com.weygo.weygophone.pages.receipt.model.WGReceipt;

/**
 * Created by muma on 2017/8/20.
 */

public class WGCommitOrderDeliverDateView extends JHLinearLayout {

    WGCellStyle4View mView;

    public WGCommitOrderDeliverDateView(Context context) {
        super(context);
    }

    public WGCommitOrderDeliverDateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderDeliverDateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mView = (WGCellStyle4View) findViewById(R.id.view);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGCommitOrderDeliverTime) {
            WGCommitOrderDeliverTime item = (WGCommitOrderDeliverTime) object;
            mView.setTitle(R.string.CommitOrder_Deliver_Date);
            mView.setDetailTitle(item.currentDate);
        }

    }

}
