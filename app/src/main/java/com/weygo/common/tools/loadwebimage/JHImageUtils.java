package com.weygo.common.tools.loadwebimage;

import android.content.Context;

/**
 * Created by muma on 2016/11/29.
 */

public class JHImageUtils extends Object {

    public JHBaseImageLoaderInterface imageLoader = null;

    /**
     * 私有的构造器
     */
    public JHImageUtils() {
        imageLoader = new JHAndroidUniversalImageLoader();
    }

    /**
     * 内部类实现单例模式
     * 延迟加载，减少内存开销
     *
     */
    private static class JHImageToolHolder {
        private static JHImageUtils instance = new JHImageUtils();
    }

    public static JHImageUtils getInstance() {
        return JHImageToolHolder.instance;
    }

    public void loadImage(Context context, JHImageLoaderEntity imageLoaderEntity) {
        imageLoader.loadImage(context, imageLoaderEntity);
    }
}
