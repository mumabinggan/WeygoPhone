package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeTitleItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentFloorImageTitleView extends JHRelativeLayout {

    ImageView mImageView;

    TextView mNameTV;

    TextView mBreifDesTV;

    TextView mMoreTV;

    public WGHomeContentFloorImageTitleView(Context context) {
        super(context);
    }

    public WGHomeContentFloorImageTitleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentFloorImageTitleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNameTV = (TextView) findViewById(R.id.nameTV);
        mBreifDesTV = (TextView) findViewById(R.id.breifDesTV);
        mMoreTV = (TextView) findViewById(R.id.moreTV);
    }

    public void showWithData(Object data) {
        if (data instanceof WGHomeFloorItem) {
            WGHomeFloorItem item = (WGHomeFloorItem) data;
            JHImageUtils.getInstance().loadImage(item.pictureURL, R.drawable.common_image_loading_small, mImageView);
            mNameTV.setText(item.pictureName);
            mBreifDesTV.setText(item.pictureBriefDescription);
            if (JHStringUtils.isNullOrEmpty(item.pictureBtnName)) {
                String moreString = "  " + item.pictureBtnName + "  ";
                mMoreTV.setText(moreString);
            }
        }
    }
}
