package com.weygo.weygophone.pages.order.detail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.detail.model.WGOrderFax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderDetailPayView extends LinearLayout {

    TextView mPayTV1;
    TextView mPayTV2;
    TextView mPayTV3;
    TextView mPayTV4;
    TextView mPayTV5;
    TextView mPayTV6;
    TextView mPayTV7;
    List mTVList;

    public WGOrderDetailPayView(Context context) {
        this(context, null);
    }

    public WGOrderDetailPayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderDetailPayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPayTV1 = (TextView) findViewById(R.id.pay1TV);
        mPayTV2 = (TextView) findViewById(R.id.pay2TV);
        mPayTV3 = (TextView) findViewById(R.id.pay3TV);
        mPayTV4 = (TextView) findViewById(R.id.pay4TV);
        mPayTV5 = (TextView) findViewById(R.id.pay5TV);
        mPayTV6 = (TextView) findViewById(R.id.pay6TV);
        mPayTV7 = (TextView) findViewById(R.id.pay7TV);
        mTVList = Arrays.asList(mPayTV1, mPayTV2, mPayTV3, mPayTV4, mPayTV5, mPayTV6, mPayTV7);
    }

    public void showWithData(List payList) {
        if (payList != null) {
            for (int num = 0; num < payList.size(); ++num) {
                String item = (String)payList.get(num);
                TextView textView = (TextView)mTVList.get(num);
                textView.setText(item);
            }
        }
    }
}
