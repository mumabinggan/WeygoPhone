package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.asm.Label;
import com.weygo.common.base.JHInterface;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by muma on 2016/12/17.
 */

public class WGTabNavigationBar extends RelativeLayout implements View.OnClickListener {

    OnClickHomeNavigationBarListener mListener;

    TextView mTitleLabel;

    ImageView mLeftImageView;

    public void setLeftImage(int resId) {
        if (mLeftImageView == null) {
            mLeftImageView = (ImageView) findViewById(R.id.navigationBar_simpleIntro);
        }
        mLeftImageView.setImageResource(resId);
    }

    public interface OnClickHomeNavigationBarListener extends JHInterface {
        void onClickBriefIntro(View var1);
        void onClickTitle(View var1);
        void onClickSearch(View var1);
        void onClickCart(View var1);
    }

    public WGTabNavigationBar(Context context) {
        super(context);
    }

    public WGTabNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGTabNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mTitleLabel = (TextView) findViewById(R.id.navigationBar_title);

        List<Integer> itemIdArray = Arrays.asList(R.id.navigationBar_simpleIntro,
                R.id.navigationBar_search, R.id.navigationBar_cart,
                R.id.navigationBar_title);
        for (Integer id : itemIdArray) {
            View item = findViewById(id);
            item.setOnClickListener(this);
        }
    }

    public View getShopCartView() {
        return findViewById(R.id.navigationBar_cart);
    }

    public int[] getShopCartViewPoint() {
        View view = findViewById(R.id.navigationBar_cart);
        int[] endPoint = new int[2];
        view.getLocationInWindow(endPoint);
        endPoint[0] = endPoint[0] - view.getWidth()/2;
        endPoint[1] = endPoint[1] - view.getHeight();
        return endPoint;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            if (view.getId() == R.id.navigationBar_simpleIntro) {
                mListener.onClickBriefIntro(view);
            }
            if (view.getId() == R.id.navigationBar_search) {
                mListener.onClickSearch(view);
            }
            if (view.getId() == R.id.navigationBar_cart) {
                mListener.onClickCart(view);
            }
            if (view.getId() == R.id.navigationBar_title) {
                mListener.onClickTitle(view);
            }
        }
    }

    public void setOnClickListener(OnClickHomeNavigationBarListener listener) {
        mListener = listener;
    }

    public void setTitle(int resId) {
        mTitleLabel.setText(resId);
    }

    public void setTitle(String title) {
        mTitleLabel.setText(title);
    }

    public void setSearchHidden(boolean hidden) {
        View view = findViewById(R.id.navigationBar_search);
        if (hidden) {
            view.setVisibility(INVISIBLE);
        }
        else {
            view.setVisibility(VISIBLE);
        }

    }

    public void setTitleDrawableRight(boolean show) {
        TextView titleTV = (TextView)findViewById(R.id.navigationBar_title);
        if (!show) {
            titleTV.setCompoundDrawables(null, null, null, null);
        }
        else {
            Drawable drawable = getResources().getDrawable(R.drawable.home_navigationbar_jiantou);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            titleTV.setCompoundDrawables(null, null, drawable, null);
        }
    }
}
