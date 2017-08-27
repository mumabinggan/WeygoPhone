package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.widget.WGCellStyle4View;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDeliverTime;

/**
 * Created by muma on 2017/8/20.
 */

public class WGCommitOrderDeliverTimeView extends JHLinearLayout {

    WGCellStyle4View mView;

    public WGCommitOrderDeliverTimeView(Context context) {
        super(context);
    }

    public WGCommitOrderDeliverTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderDeliverTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
            mView.setTitle(R.string.CommitOrder_Deliver_Time);
            mView.setDetailTitle(item.getCurrentTime());
        }
    }
}
