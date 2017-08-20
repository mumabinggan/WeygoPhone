package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementConsumePrice;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderRemarkView extends JHLinearLayout {

    EditText mRemarkET;

    public WGCommitOrderRemarkView(Context context) {
        super(context);
    }

    public WGCommitOrderRemarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderRemarkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRemarkET = (EditText) findViewById(R.id.remarkET);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
    }
}
