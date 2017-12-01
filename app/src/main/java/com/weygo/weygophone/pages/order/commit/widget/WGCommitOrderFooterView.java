package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDetail;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderFooterView extends JHRelativeLayout {

    TextView mTotalPriceTV;

    TextView mReducePriceTV;

    Button mBtn;

    RelativeLayout vv;

    public interface OnItemListener {
        void onCommit();
    }
    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGCommitOrderFooterView(Context context) {
        super(context);
    }

    public WGCommitOrderFooterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderFooterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTotalPriceTV = (TextView) findViewById(R.id.totalPriceTV);
        mReducePriceTV = (TextView) findViewById(R.id.reducePriceTV);
        mBtn = (Button) findViewById(R.id.commitOrderBtn);
        mBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("---------", "====");
                handleCommitBtn();
            }
        });
    }

    void handleCommitBtn() {
        if (mListener != null) {
            mListener.onCommit();
        }
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGCommitOrderDetail) {
            WGCommitOrderDetail item = (WGCommitOrderDetail) object;
            mTotalPriceTV.setText(JHResourceUtils.getInstance().
                    getString(R.string.CommitOrder_Total_Pay) +
                    item.consumePrice.currentTotalPrice);
            if (JHStringUtils.isNullOrEmpty(item.consumePrice.reducePrice)) {
                mReducePriceTV.setVisibility(INVISIBLE);
            }
            else {
                mReducePriceTV.setText(" " + item.consumePrice.reducePrice + " ");
                mReducePriceTV.setVisibility(VISIBLE);
            }
        }
        if (object == null) {
            mBtn.setEnabled(false);
        }
        else {
            WGCommitOrderDetail item = (WGCommitOrderDetail) object;
            if (JHStringUtils.isNullOrEmpty(item.minPriceTips)) {
                mBtn.setEnabled(true);
            }
            else {
                mBtn.setEnabled(false);
            }
        }
    }
}
