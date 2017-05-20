package com.weygo.weygophone.common.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by muma on 2017/5/18.
 */

public class WGOptionPickerView extends OptionPicker {

    public WGOptionPickerView(Activity activity, String[] items) {
        super(activity, items);
        initConfig();
    }

    public WGOptionPickerView(Activity activity, List<String> items) {
        super(activity, items);
        initConfig();
    }

    void initConfig() {
        setHeight(400);
        setCanceledOnTouchOutside(false);
        setDividerRatio(WheelView.DividerConfig.FILL);
        setShadowColor(Color.RED, 40);
        setSelectedIndex(1);
        setCycleDisable(true);
        setTextSize(11);
    }

    @Override
    protected void showAfter() {
        //super.showAfter();
        View view = getRootView();
        //使用透明渐变位移动画，缓解选中项显示跳动问题
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationY", 300, 0);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alpha, translation);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.start();
    }

    @Override
    public void dismiss() {
        View view = getRootView();
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(view, "alpha", 1, 0);
        ObjectAnimator translation = ObjectAnimator.ofFloat(view, "translationY", 0, 300);
        animatorSet.playTogether(alpha, translation);
        animatorSet.setDuration(200);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dismissImmediately();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }
}
