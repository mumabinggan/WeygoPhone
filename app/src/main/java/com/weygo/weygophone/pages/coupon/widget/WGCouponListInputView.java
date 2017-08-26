package com.weygo.weygophone.pages.coupon.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;

/**
 * Created by muma on 2017/8/23.
 */

public class WGCouponListInputView extends JHLinearLayout {

    TextView mActiveTV;

    EditText mInputET;

    WGCoupon mData;

    public interface OnItemListener {
        void onActive(String code);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGCouponListInputView(Context context) {
        super(context);
    }

    public WGCouponListInputView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCouponListInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mActiveTV = (TextView) findViewById(R.id.activeTV);
        mActiveTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleActive();
            }
        });
        mInputET = (EditText) findViewById(R.id.inputET);
    }

    void handleActive() {
        if (mListener != null) {
            mListener.onActive(mInputET.getText().toString());
        }
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
    }

    public void setInput(String input, boolean enable) {
        mInputET.setText(input);
        mInputET.setEnabled(enable);
    }

    public void setActiveTitle(int resId, boolean enable, boolean selected) {
        mActiveTV.setText(resId);
        mActiveTV.setEnabled(enable);
        mActiveTV.setSelected(selected);
    }

    public boolean isSelectedActiveTV() {
        return mActiveTV.isSelected();
    }

    public String inputCode() {
        return mInputET.getText().toString();
    }
}
