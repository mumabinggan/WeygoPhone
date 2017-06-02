package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by muma on 2017/6/2.
 */

public class WGViewPager extends ViewPager {

    private int mCurrentPagePosition = 0;

    public WGViewPager(Context context) {
        super(context);
    }

    public WGViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        try {
//            View child = getChildAt(mCurrentPagePosition);
//            if (child != null) {
//                child.measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
//                int h = child.getMeasuredHeight();
//                Log.e("=======",h + "+" + mCurrentPagePosition);
//                heightMeasureSpec = MeasureSpec.makeMeasureSpec(h, MeasureSpec.EXACTLY);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }
//
//    public void reMeasureCurrentPage(int position) {
//        mCurrentPagePosition = position;
//        requestLayout();
//    }
}
