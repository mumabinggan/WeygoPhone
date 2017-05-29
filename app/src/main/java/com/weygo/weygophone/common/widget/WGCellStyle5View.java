package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/29.
 */

public class WGCellStyle5View extends RelativeLayout {

    public TextView mTextView;

    public EditText mEditText;

    public WGCellStyle5View(Context context) {
        super(context);
    }

    public WGCellStyle5View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCellStyle5View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = (TextView) findViewById(R.id.titleView);
        mEditText = (EditText) findViewById(R.id.titleValueET);
    }

    public void showWithData(Object object) {
        mEditText.setText((String)object);
    }
}
