package com.weygo.common.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by muma on 2017/5/31.
 */

public class JHLinearLayout extends LinearLayout {
    public JHLinearLayout(Context context) {
        super(context);
    }

    public JHLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public JHLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void showWithData(Object object) {

    }

    public void showWithArray(List list) {

    }
}
