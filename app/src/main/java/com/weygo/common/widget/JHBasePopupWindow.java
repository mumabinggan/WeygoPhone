package com.weygo.common.widget;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by muma on 2017/8/17.
 */

public class JHBasePopupWindow extends PopupWindow {
    public JHBasePopupWindow(Context context) {
        super(context);
    }

    public JHBasePopupWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public JHBasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public JHBasePopupWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public JHBasePopupWindow() {
    }

    public JHBasePopupWindow(View contentView) {
        super(contentView);
    }

    public JHBasePopupWindow(int width, int height) {
        super(width, height);
    }

    public JHBasePopupWindow(View contentView, int width, int height) {
        super(contentView, width, height);
        initConfig();
    }

    public JHBasePopupWindow(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    void initConfig() {
        //window.setAnimationStyle(R.style.popup_window_anim);
        // TODO: 2016/5/17 设置背景颜色
        this.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#80000000")));
        // TODO: 2016/5/17 设置可以获取焦点
        this.setFocusable(true);
        // TODO: 2016/5/17 设置可以触摸弹出框以外的区域
        this.setOutsideTouchable(true);
        // TODO：更新popupwindow的状态
        this.update();
    }
}
