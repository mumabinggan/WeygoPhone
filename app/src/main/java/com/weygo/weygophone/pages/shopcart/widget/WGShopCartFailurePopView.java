package com.weygo.weygophone.pages.shopcart.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.widget.JHPopView;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/8/18.
 */

public class WGShopCartFailurePopView extends JHPopView {

    TextView mTipsTV;

    TextView mRemoveTV;

    TextView mChangeTV;

    public interface OnItemListener {
        void onRemove();
        void onChange();
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGShopCartFailurePopView(Context context) {
        super(context);
    }

    public WGShopCartFailurePopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGShopCartFailurePopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTipsTV = (TextView) findViewById(R.id.failureTips);
        mRemoveTV = (TextView) findViewById(R.id.goodRemoveTV);
        mRemoveTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                if (mListener != null) {
                    mListener.onRemove();
                }
            }
        });
        mChangeTV = (TextView) findViewById(R.id.goodChangeTV);
        mChangeTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                if (mListener != null) {
                    mListener.onChange();
                }
            }
        });
    }

    public void setFailureTips(String tips) {
        mTipsTV.setText(tips);
    }
}
