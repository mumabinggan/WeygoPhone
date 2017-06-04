package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeTitleItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentTopicItemView extends JHRelativeLayout {

    WGHomeTitleItem mData;

    ImageView mImageView;

    TextView mNameTV;

    public WGHomeContentTopicItemView(Context context) {
        super(context);
    }

    public WGHomeContentTopicItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentTopicItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNameTV = (TextView) findViewById(R.id.nameTV);
    }

    public void showWithData(Object data) {
        if (data instanceof WGTopicItem) {
            WGTopicItem item = (WGTopicItem) data;
            JHImageUtils.getInstance().loadImage(item.pictureURL, R.drawable.common_image_loading_small, mImageView);
            mNameTV.setText(item.name);
        }
    }
}
