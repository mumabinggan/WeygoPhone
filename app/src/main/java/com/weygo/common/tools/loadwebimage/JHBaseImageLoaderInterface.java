package com.weygo.common.tools.loadwebimage;

import android.content.Context;
import android.widget.ImageView;

import com.weygo.common.base.JHInterface;

/**
 * Created by muma on 2016/11/29.
 */

public interface JHBaseImageLoaderInterface extends JHInterface {
    public void loadImage(Context ctx, JHImageLoaderEntity img);
    public void loadImage(Context ctx, String imageUrl, int resId, ImageView imageView);
}
