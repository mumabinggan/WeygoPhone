package com.weygo.common.base;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by muma on 2017/5/21.
 */

public class JHPaddingDecoration extends RecyclerView.ItemDecoration {

    private int mDivider;

    public JHPaddingDecoration(Context context, int divider) {
        //即你要设置的分割线的宽度 --这里设为10dp
        mDivider = divider;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        outRect.left = divider;  //相当于 设置 left padding
//        outRect.top = divider;   //相当于 设置 top padding
//        outRect.right = divider; //相当于 设置 right padding
        outRect.bottom = mDivider;  //相当于 设置 bottom padding
    }
}
