package com.weygo.common.tools;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/4/27.
 */

public class JHAdaptScreenUtils {
    //public static kScale = ;

    public static float scale() {
        return R.dimen.x1 / 1 * 10;
    }

    public static int devicePixelWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int devicePixelHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static int deviceDpWidth(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;//屏幕密度dpi（120 / 160 / 240）
        //屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (dm.widthPixels/density);//屏幕宽度(dp)
        return screenWidth;
    }

    public static int pxTodp(Context context, float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int deviceDpHeight(Context context) {
        return deviceDpWidth(context);
    }

    public static float width(float width) {
        return R.dimen.x1 * width;
    }

    public static float height(float height) {
        return width(height);
    }
}
