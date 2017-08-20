package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/29.
 */

public class WGCellStyle6View extends JHLinearLayout {

    TextView mTextView;

    public WGCellStyle6View(Context context) {
        super(context);
    }

    public WGCellStyle6View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCellStyle6View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = (TextView) findViewById(R.id.textView);
    }

    public void setTitle(int resId) {
        mTextView.setText(resId);
    }

    public void setTitleGravity(int gravity) {
        mTextView.setGravity(gravity);
    }

    public void showWithData(Object object) {

    }
}
