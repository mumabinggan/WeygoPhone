package com.weygo.weygophone.pages.search.widget;

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

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.widget.JHNavigationBar;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;

/**
 * Created by muma on 2017/8/23.
 */

public class WGSearchNavigationView extends JHRelativeLayout {

    ImageView mLeftIV;

    EditText mInputET;

    ImageView mVistaIV;

    WGShopCartView mShopCartView;

    public interface OnItemListener {
        void onEmpty(String searchName);
        void onVista();
        void onLeft();
        void onSearch(String searchName);
        void onShopCart();
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGSearchNavigationView(Context context) {
        super(context);
    }

    public WGSearchNavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGSearchNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mInputET = (EditText) findViewById(R.id.inputET);
        mInputET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (JHStringUtils.isNullOrEmpty(s.toString())) {
                    if (mListener != null) {
                        mListener.onEmpty(null);
                    }
                }
            }
        });
        mInputET.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_SEND ||(event!=null&&event.getKeyCode()== KeyEvent.KEYCODE_ENTER)) {
                    if (mListener != null) {
                        mListener.onSearch(mInputET.getText().toString());
                    }
                    return true;
                }
                else {
                    return false;
                }
            }
        });
        mVistaIV = (ImageView) findViewById(R.id.vistaIV);
        mVistaIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onVista();
                }
            }
        });
        mLeftIV = (ImageView) findViewById(R.id.titlebar_left);
        mLeftIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onLeft();
                }
            }
        });
        mShopCartView = (WGShopCartView) findViewById(R.id.shopCartView);
        mShopCartView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onShopCart();
                }
            }
        });
        mShopCartView.handelShopCart();
    }

    public int[] getShopCartViewPoint() {
        View view = findViewById(R.id.shopCartView);
        int[] endPoint = new int[2];
        view.getLocationInWindow(endPoint);
        endPoint[0] = endPoint[0] - view.getWidth()/2;
        endPoint[1] = endPoint[1] - view.getHeight();
        return endPoint;
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

    public void setInputText(String title) {
        mInputET.setText(title);
    }

    public void setVistaHidden(boolean hidden) {
        if (hidden) {
            mVistaIV.setVisibility(INVISIBLE);
        }
        else {
            mVistaIV.setVisibility(VISIBLE);
        }
    }

    public void setShopCartHidden(boolean hidden) {
        if (hidden) {
            mShopCartView.setVisibility(INVISIBLE);
        }
        else {
            mShopCartView.setVisibility(VISIBLE);
        }
    }

    public void setVistaResId(int resId) {
        mVistaIV.setImageResource(resId);
    }
}
