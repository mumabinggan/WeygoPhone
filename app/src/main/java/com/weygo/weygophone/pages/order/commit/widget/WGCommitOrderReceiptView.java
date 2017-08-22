package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.widget.WGCellStyle4View;
import com.weygo.weygophone.pages.receipt.model.WGReceipt;

/**
 * Created by muma on 2017/8/20.
 */

public class WGCommitOrderReceiptView extends JHLinearLayout {

    TextView mTaxTV;

    TextView mCompanyTV;

    TextView mCapTV;

    public WGCommitOrderReceiptView(Context context) {
        super(context);
    }

    public WGCommitOrderReceiptView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderReceiptView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTaxTV = (TextView) findViewById(R.id.taxTV);
        mCompanyTV = (TextView) findViewById(R.id.companyTV);
        mCapTV = (TextView) findViewById(R.id.capTV);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGReceipt) {
            WGReceipt item = (WGReceipt) object;
            mTaxTV.setText(item.taxCode);
            mCompanyTV.setText(item.companyName);
            mCapTV.setText(item.cap);
        }
    }

}