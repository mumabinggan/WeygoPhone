package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.common.widget.WGCellStyle4View;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.receipt.model.WGReceipt;

/**
 * Created by muma on 2017/8/20.
 */

public class WGCommitOrderEmptyReceiptView extends JHLinearLayout {

    WGCellStyle4View mView;

    public WGCommitOrderEmptyReceiptView(Context context) {
        super(context);
    }

    public WGCommitOrderEmptyReceiptView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderEmptyReceiptView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mView.setTitle(R.string.CommitOrder_Fax);
    }

}
