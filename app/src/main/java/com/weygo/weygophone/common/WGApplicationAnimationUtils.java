package com.weygo.weygophone.common;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;

import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGObject;

/**
 * Created by muma on 2017/8/30.
 */

public class WGApplicationAnimationUtils extends WGObject {

    public static void add(Context context, ViewGroup viewGroup,
                           final View beginView, String imageUrl,
                           int resId, final View endView,
                           int[] distancePostion) {

        //贝塞尔起始数据点
        int[] startPosition = new int[2];
//        //贝塞尔结束数据点
        int[] endPosition = new int[2];
//        //控制点
//        int[] recyclerPosition = new int[2];
//
        beginView.getLocationInWindow(startPosition);
        endView.getLocationInWindow(endPosition);
//        mRecyclerView.getLocationInWindow(recyclerPosition);

        PointF startF = new PointF();
        PointF endF = new PointF();
        PointF controllF = new PointF();

        startF.x = startPosition[0];
        startF.y = startPosition[1] - distancePostion[1] - beginView.getHeight()/2;
        endF.x = endPosition[0];
        endF.y = endPosition[1] - distancePostion[1] - beginView.getHeight()/2;
        controllF.x = startF.x;
        controllF.y = endF.y;

        final ImageView imageView = new ImageView(context);
        viewGroup.addView(imageView);
        if (JHStringUtils.isNullOrEmpty(imageUrl)) {
            imageView.setImageResource(resId);
        }
        else {
            JHImageUtils.getInstance().loadImage(imageUrl,
                    R.drawable.common_image_loading_small, imageView);
        }
        imageView.getLayoutParams().width = 50;
        imageView.getLayoutParams().height = 50;
        imageView.setVisibility(View.VISIBLE);
        imageView.setX(startF.x);
        imageView.setY(startF.y);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
                Log.i("wangjtiao", "viewF:" + beginView.getX() + "," + beginView.getY());
            }
        });


        ObjectAnimator objectAnimatorX = new ObjectAnimator().ofFloat(imageView, "scaleX", 1.0f, 0f);
        ObjectAnimator objectAnimatorY = new ObjectAnimator().ofFloat(imageView, "scaleY", 1.0f, 0f);
        objectAnimatorX.setInterpolator(new AccelerateInterpolator());
        objectAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimatorX).with(objectAnimatorY).with(valueAnimator);
        set.setDuration(800);
        set.start();
    }
}
