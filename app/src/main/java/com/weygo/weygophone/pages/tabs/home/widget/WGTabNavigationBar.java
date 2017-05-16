package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.weygo.common.base.JHInterface;
import com.weygo.weygophone.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by muma on 2016/12/17.
 */

public class WGTabNavigationBar extends RelativeLayout implements View.OnClickListener {

    OnClickHomeNavigationBarListener mListener;

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

        List<Integer> itemIdArray = Arrays.asList(R.id.navigationBar_simpleIntro,
                R.id.navigationBar_search, R.id.navigationBar_cart,
                R.id.navigationBar_title);
        for (Integer id : itemIdArray) {
            View item = findViewById(id);
            item.setOnClickListener(this);
        }
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
}
