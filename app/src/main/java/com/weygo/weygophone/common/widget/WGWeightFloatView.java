package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.widget.JHPopView;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementTips;

/**
 * Created by muma on 2017/11/16.
 */

public class WGWeightFloatView extends JHPopView {

    TextView mTitleTV;

    TextView mContentTV;

    Button mCloseBtn;

    public WGWeightFloatView(Context context) {
        super(context);
    }

    public WGWeightFloatView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGWeightFloatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initConfig(Context context) {
        super.initConfig(context);
        mCanClose = false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitleTV = (TextView) findViewById(R.id.titleTV);
        mContentTV = (TextView) findViewById(R.id.contentTV);
        mCloseBtn = (Button) findViewById(R.id.confirmBtn);
        mCloseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClose();
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClose();
            }
        });
        mContentTV.setMovementMethod(new ScrollingMovementMethod());
    }

    void handleClose() {
        mPopupWindow.dismiss();;
    }

    public void showWithData(WGSettlementTips tips) {
        mTitleTV.setText(JHResourceUtils.getInstance().getString(R.string.CommitOrder_OrderPrice_Tip_Title));
        mContentTV.setText(tips.orderPriceDetailTip);
    }
}
