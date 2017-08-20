package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDetail;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderMinPriceTipsView extends JHLinearLayout {

    TextView mTipsTV;

    public WGCommitOrderMinPriceTipsView(Context context) {
        super(context);
    }

    public WGCommitOrderMinPriceTipsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderMinPriceTipsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTipsTV = (TextView) findViewById(R.id.tipsTV);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGCommitOrderDetail) {
            WGCommitOrderDetail item = (WGCommitOrderDetail)object;
            if (item != null) {
                mTipsTV.setText(item.minPriceTips);
            }
        }
    }
}
