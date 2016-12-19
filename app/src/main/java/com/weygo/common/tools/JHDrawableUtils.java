package com.weygo.common.tools;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by muma on 2016/12/18.
 */

public class JHDrawableUtils {
    public static Drawable getDrawable(Context context, int resId) {
        return context.getResources().getDrawable(resId);
    }
}
