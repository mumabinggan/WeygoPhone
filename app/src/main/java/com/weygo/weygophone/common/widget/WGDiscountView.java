package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/9/16.
 */

public class WGDiscountView extends JHRelativeLayout {

    TextView mDiscountTV;

    public WGDiscountView(Context context) {
        super(context);
    }

    public WGDiscountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGDiscountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDiscountTV = (TextView) findViewById(R.id.discountTV);
        mDiscountTV.setRotation(-45);
    }

    public void setDiscountText(String discount) {
        mDiscountTV.setText(discount);
        if (JHStringUtils.isNullOrEmpty(discount)) {
            setVisibility(INVISIBLE);
        }
        else {
            setVisibility(VISIBLE);
        }
    }
}
