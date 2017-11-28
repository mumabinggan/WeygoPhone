package com.weygo.weygophone.pages.classifyDetail.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHColorUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/8/8.
 */

public class WGClassifyDetailFilterItemView extends JHRelativeLayout {

    ImageView mImageView;
    public void setImageView(int resId) {
        mImageView.setImageResource(resId);
    }

    TextView mTextView;
    public void setTextView(int resId) {
        mTextView.setText(resId);
    }

    public void setTextView(String string) {
        mTextView.setText(string);
    }

    public WGClassifyDetailFilterItemView(Context context) {
        super(context);
    }

    public WGClassifyDetailFilterItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGClassifyDetailFilterItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    public void setViewSelected(boolean selected) {
        int resId = selected ? R.color.WGAppBlueButtonColor : R.color.WGAppBaseTitleAAColor;
        mTextView.setTextColor(JHResourceUtils.getInstance().getColor(resId));
    }
}
