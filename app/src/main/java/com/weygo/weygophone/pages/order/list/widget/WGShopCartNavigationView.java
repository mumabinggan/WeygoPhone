package com.weygo.weygophone.pages.order.list.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.widget.JHNavigationBar;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.search.widget.WGShopCartView;

/**
 * Created by muma on 2017/8/23.
 */

public class WGShopCartNavigationView extends JHRelativeLayout {

    ImageView mLeftIV;
    TextView mTitleTextView;
    WGShopCartView mShopCartView;

    public interface OnItemListener {
        void onLeft();
        void onRight();
    }
    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public void setTitle(String title) {
        mTitleTextView.setText(title);
    }

    public void setTitle(int resId) {
        mTitleTextView.setText(resId);
    }

    public WGShopCartNavigationView(Context context) {
        super(context);
    }

    public WGShopCartNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGShopCartNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mLeftIV = (ImageView) findViewById(R.id.titlebar_left);
        mLeftIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onLeft();
                }
            }
        });
        mTitleTextView = (TextView) findViewById(R.id.titlebar_title);
        mShopCartView = (WGShopCartView) findViewById(R.id.shopCartView);
        mShopCartView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRight();
                }
            }
        });
        mShopCartView.handelShopCart();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
    }

    public View getShopCartView() {
        return mShopCartView;
    }

    public int[] getShopCartViewPoint() {
        int[] endPoint = new int[2];
        mShopCartView.getLocationInWindow(endPoint);
        endPoint[0] = endPoint[0] - mShopCartView.getWidth()/2;
        endPoint[1] = endPoint[1] - mShopCartView.getHeight()/2;
        return endPoint;
    }

    public void setShopCartHidden(boolean hidden) {
        if (hidden) {
            mShopCartView.setVisibility(INVISIBLE);
        }
        else {
            mShopCartView.setVisibility(VISIBLE);
        }
    }
}
