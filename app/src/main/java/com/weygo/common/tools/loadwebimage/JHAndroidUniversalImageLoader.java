package com.weygo.common.tools.loadwebimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

import static com.weygo.common.tools.loadwebimage.JHImageLoaderListener.JHLoadWebImageFailReson.JHLoadWebImageFailReson_Default;

/**
 * Created by muma on 2016/11/29.
 */

public class JHAndroidUniversalImageLoader implements JHBaseImageLoaderInterface {

    //Android Universal Image Loader init class
    ImageLoaderConfiguration.Builder config = null;

    @Override
    public void loadImage(Context ctx, final JHImageLoaderEntity img) {

        this.initImageLoaderConfig(ctx);

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(img.getPlaceHolder())         //加载开始默认的图片
                .showImageForEmptyUri(img.getEmptyImage())     //url爲空會显示该图片，自己放在drawable里面的
                .showImageOnFail(img.getFailImage())                //加载图片出现问题，会显示该图片
                .cacheInMemory(true)                                               //缓存用
                .cacheOnDisk(false)                                                    //缓存用
                .displayer(new RoundedBitmapDisplayer(0))       //图片圆角显示，值为整数
                .build();
        ImageLoadingListener listener = null;
        if (img.listener != null) {
            listener = new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    if (img.listener != null) {
                        img.listener.onStarted(imageUri, view);
                    }
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    if (img.listener != null) {
                        img.listener.onFailed(imageUri, view, JHLoadWebImageFailReson_Default);
                    }
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (img.listener != null) {
                        img.listener.onCompletion(imageUri, view, loadedImage);
                    }
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    if (img.listener != null) {
                        img.listener.onCancelled(imageUri, view);
                    }
                }
            };
        }
        ImageLoader.getInstance().displayImage(img.getImageUrl(), img.getImageView(), options, listener);
    }

    void initImageLoaderConfig(Context ctx) {
        if (config == null) {
            File cacheDir = StorageUtils.getOwnCacheDirectory(ctx, "Imageloader/Cache");
            config = new ImageLoaderConfiguration.Builder(ctx);
            config.threadPriority(Thread.NORM_PRIORITY - 2);
            config.denyCacheImageMultipleSizesInMemory();
            config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
            config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
            config.diskCache(new UnlimitedDiskCache(cacheDir)); //自定义缓存路径
            config.tasksProcessingOrder(QueueProcessingType.LIFO);
            config.writeDebugLogs(); // Remove for release app

            // Initialize ImageLoader with configuration.
            ImageLoader.getInstance().init(config.build());
        }
    }
}
