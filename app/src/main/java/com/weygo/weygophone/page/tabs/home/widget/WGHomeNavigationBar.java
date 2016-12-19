package com.weygo.weygophone.page.tabs.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by muma on 2016/12/17.
 */

public class WGHomeNavigationBar extends RelativeLayout implements View.OnClickListener {

    OnClickHomeNavigationBarListener listener;

    public interface OnClickHomeNavigationBarListener {
        void onClickBriefIntro(View var1);
        void onClickSearch(View var1);
        void onClickCart(View var1);
    }

    public WGHomeNavigationBar(Context context) {
        super(context);
    }

    public WGHomeNavigationBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeNavigationBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View view) {

    }
}
