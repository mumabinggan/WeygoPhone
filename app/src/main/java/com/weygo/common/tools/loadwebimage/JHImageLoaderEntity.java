package com.weygo.common.tools.loadwebimage;

import android.widget.ImageView;

import com.weygo.common.base.JHObject;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2016/11/29.
 */

public class JHImageLoaderEntity extends JHObject {
    private ImageView imageView;
    private String imageUrl;
    private int placeHolder;
    private int emptyImage;
    private int failImage;
    JHImageLoaderListener listener;

    public JHImageLoaderEntity(Builder builder) {
        imageUrl = builder.imageUrl;
        imageView = builder.imageView;
        placeHolder = builder.placeHolder;
        emptyImage = builder.emptyImage;
        failImage = builder.failImage;
        listener = builder.listener;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getEmptyImage() {
        return emptyImage;
    }

    public int getFailImage() {
        return failImage;
    }

    public static class Builder {
        private ImageView imageView = null;
        private String imageUrl = null;
        private int placeHolder = 0;
        private int failImage = 0;
        private int emptyImage = 0;
        private JHImageLoaderListener listener = null;

        public Builder() {
            this.imageUrl = null;
            this.imageView = null;
            this.placeHolder = R.mipmap.ic_launcher;
            this.failImage = 0;
            this.emptyImage = 0;
            this.listener = null;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder failImage(int failImage) {
            this.failImage = failImage;
            return this;
        }

        public Builder emptyImage(int emptyImage) {
            this.emptyImage = emptyImage;
            return this;
        }

        public Builder listener(JHImageLoaderListener listener) {
            this.listener = listener;
            return this;
        }

        public JHImageLoaderEntity build() {
            return new JHImageLoaderEntity(this);
        }
    }

}
