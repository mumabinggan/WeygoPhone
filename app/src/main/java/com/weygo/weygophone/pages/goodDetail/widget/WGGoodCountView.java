package com.weygo.weygophone.pages.goodDetail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.common.base.JHInterface;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/30.
 */

public class WGGoodCountView extends LinearLayout {

    long mCount = 1;

    TextView mCountView;

    ImageView mAddIV;

    ImageView mSubIV;

    OnGoodCountChangeListener mListener;

    interface OnGoodCountChangeListener extends JHInterface {
        void onAddListener();
        void onSubListener();
    }

    public WGGoodCountView(Context context) {
        this(context, null);
    }

    public WGGoodCountView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGGoodCountView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCountView = (TextView) findViewById(R.id.countTV);
        mAddIV = (ImageView) findViewById(R.id.addImageView);
        mAddIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddCount();
            }
        });
        mSubIV = (ImageView) findViewById(R.id.subImageView);
        mSubIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubCount();
            }
        });
    }

    void handleAddCount() {
        if (mListener != null) {
            mListener.onAddListener();
        }
        else {
            mCount++;
            setCount(mCount);
        }
    }

    void handleSubCount() {
        if (mListener != null) {
            mListener.onSubListener();
        }
        else {
            if (mCount > 1) {
                mCount--;
                setCount(mCount);
            }
        }
    }

    public void setCount(long count) {
        mCount = count;
        mCountView.setText(mCount + "");
    }

    public void setListener(OnGoodCountChangeListener listener) {
        mListener = listener;
    }
}
