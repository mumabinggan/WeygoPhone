package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.widget.JHPopView;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;

import org.w3c.dom.Text;

/**
 * Created by muma on 2017/8/17.
 */

public class WGAddGoodView extends JHLinearLayout {

    ImageView subIV;

    TextView mCountTV;

    ImageView addIV;

    int mCount;
    public void setCount(int count) {
        mCount = count;
        mCountTV.setText("" + mCount);
    }

    int mFromType;
    public void setFromType(int fromType) {
        mFromType = fromType;
    }

    interface OnItemListener {
        void onAdd(int count);
        void onSub(int count);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGAddGoodView(Context context) {
        super(context);
    }

    public WGAddGoodView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGAddGoodView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCount = 0;
        subIV = (ImageView) findViewById(R.id.subBtn);
        subIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSubBtn();
            }
        });
        mCountTV = (TextView) findViewById(R.id.countLabel);
        mCountTV.setText("" + mCount);
        addIV = (ImageView) findViewById(R.id.addBtn);
        addIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddBtn();
            }
        });

    }

    void handleSubBtn() {
        if (mFromType == WGConstants.WGGoodAddViewFromGoodDetail) {
            if (mCount != 0) {
                mCount--;
            }
            mCountTV.setText(mCount+"");
        }
        if (mListener != null) {
            mListener.onSub(mCount);
        }
    }

    void handleAddBtn() {
        if (mFromType == WGConstants.WGGoodAddViewFromGoodDetail) {
            mCountTV.setText(++mCount + "");
        }
        if (mListener != null) {
            mListener.onAdd(mCount);
        }
    }
}
