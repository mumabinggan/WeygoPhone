package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementTips;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderLookMoreTipsView extends JHLinearLayout {

    TextView mTipsTV;

    TextView mMoreTV;

    WGSettlementTips mData;

    public interface OnItemListener {
        void onMore(WGSettlementTips tips);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGCommitOrderLookMoreTipsView(Context context) {
        super(context);
    }

    public WGCommitOrderLookMoreTipsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderLookMoreTipsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTipsTV = (TextView) findViewById(R.id.tipsTV);
        mMoreTV = (TextView) findViewById(R.id.moreTV);
        mMoreTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onMore(mData);
                }
            }
        });
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGSettlementTips) {
            mData = (WGSettlementTips) object;
            mTipsTV.setText(mData.orderPriceTip);
        }
    }
}
