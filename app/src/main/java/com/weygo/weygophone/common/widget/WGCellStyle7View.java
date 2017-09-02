package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/29.
 */

public class WGCellStyle7View extends JHRelativeLayout {

    ImageView mImageView;

    TextView mTextView;

    public WGCellStyle7View(Context context) {
        super(context);
    }

    public WGCellStyle7View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCellStyle7View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    public void setTitle(int resId) {
        mTextView.setText(resId);
    }

    public void setTitle(String title) {
        mTextView.setText(title);
    }

    public void setImage(int resId) {
        mImageView.setImageResource(resId);
    }
}
