package com.weygo.common.tools.loadwebimage;

import android.graphics.Bitmap;
import android.view.View;

import com.weygo.common.base.JHInterface;

/**
 * Created by muma on 2016/11/29.
 */

public interface JHImageLoaderListener extends JHInterface {
    public enum JHLoadWebImageFailReson {
        JHLoadWebImageFailReson_Default,
    }
    public void onStarted(String imageUri, View view);

    public void onCompletion(String imageUri, View view, Bitmap loadedImage);

    public void onCancelled(String imageUri, View view);

    public void onFailed(String imageUri, View view, JHLoadWebImageFailReson failReason);
}
