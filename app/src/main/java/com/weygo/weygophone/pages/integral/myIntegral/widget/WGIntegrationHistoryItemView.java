package com.weygo.weygophone.pages.integral.myIntegral.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.integral.myIntegral.model.WGIntegrationHistoryItem;

/**
 * Created by muma on 2017/5/21.
 */

public class WGIntegrationHistoryItemView extends RelativeLayout {

    TextView mTitleTextView;

    TextView mScoreTextView;

    TextView mTimeTextView;

    public WGIntegrationHistoryItemView(Context context) {
        this(context, null);
    }

    public WGIntegrationHistoryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGIntegrationHistoryItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitleTextView = (TextView) findViewById(R.id.titleTextView);
        mScoreTextView = (TextView) findViewById(R.id.scoreTextView);
        mTimeTextView = (TextView) findViewById(R.id.timeTextView);
    }

    public void showWithData(WGIntegrationHistoryItem item) {
        mTimeTextView.setText(item.time);
        mTitleTextView.setText(item.des);
        mScoreTextView.setText(JHResourceUtils.getInstance().getString(R.string.MyIntegral_Number) +
                item.integration);
    }
}
