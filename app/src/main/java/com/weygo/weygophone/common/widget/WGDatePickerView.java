package com.weygo.weygophone.common.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import java.util.Calendar;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * Created by muma on 2017/5/29.
 */

public class WGDatePickerView extends DatePicker {
    protected static final int DURATION = 100;//动画延时，单位为毫秒
    Activity mActivity = null;

    public WGDatePickerView(Activity activity) {
        super(activity);
        mActivity = activity;
        init();
    }

    public WGDatePickerView(Activity activity, int mode) {
        super(activity, mode);
        mActivity = activity;
        init();
    }

    void init() {
        setCanceledOnTouchOutside(true);
        setUseWeight(true);
        setTopPadding(ConvertUtils.toPx(mActivity, 20));
        Calendar c = Calendar.getInstance();
        setRangeStart(1917, 1, 1);
        setRangeEnd(c.get(Calendar.YEAR), (c.get(Calendar.MONTH) + 1), c.get(Calendar.DAY_OF_MONTH));
        setSelectedItem(1975, 1, 1);
        setLabel("", "", "");
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
        animatorSet.setDuration(DURATION);
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
        animatorSet.setDuration(DURATION);
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
