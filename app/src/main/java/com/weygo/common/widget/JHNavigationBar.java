package com.weygo.common.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;

/**
 * Created by muma on 2016/12/16.
 */

public class JHNavigationBar extends RelativeLayout implements View.OnClickListener {

    private ImageView   mLeftImageView;
    private TextView    mTitleTextView;
    private ImageView   mRightImageView;

    OnClickLeftBarListener  leftListener;
    OnClickRightBarListener rightListener;

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setTitle(int resId) {
        mTitleTextView.setText(resId);
    }

    public void setRightBarItem(int resId) {
        mRightImageView.setImageResource(resId);
    }

    public interface OnClickLeftBarListener {
        void onClick(View var1);
    }
    public interface OnClickRightBarListener {
        void onClick(View var1);
    }

    public JHNavigationBar(Context context) {
        this(context, null);
    }

    public JHNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JHNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftImageView = (ImageView) findViewById(R.id.titlebar_left);
        mTitleTextView = (TextView) findViewById(R.id.titlebar_title);
        mRightImageView = (ImageView) findViewById(R.id.titlebar_right);
        mLeftImageView.setOnClickListener(this);
        mRightImageView.setOnClickListener(this);
        mTitleTextView.setClickable(false);
    }

    public void setLeftBarListener(OnClickLeftBarListener listener) {
        leftListener = listener;
    }

    public void setRightBarListener(OnClickRightBarListener listener) {
        rightListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.titlebar_left) {
            if (leftListener != null) {
                leftListener.onClick(mLeftImageView);
            }
        }
        if (view.getId() == R.id.titlebar_right) {
            if (rightListener != null) {
                rightListener.onClick(mRightImageView);
            }
        }
    }

    public void setLeftBtnVisible(int visible) {
        mLeftImageView.setVisibility(visible);
    }

    public void setRightBtnVisible(int visible) {
        mRightImageView.setVisibility(visible);
    }
}
