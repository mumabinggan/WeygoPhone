package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentClassifyItem;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentFloorClassifyColumnItemView extends JHRelativeLayout {

    ImageView mImageView;

    TextView mNameTV;

    public WGHomeContentFloorClassifyColumnItemView(Context context) {
        super(context);
    }

    public WGHomeContentFloorClassifyColumnItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentFloorClassifyColumnItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNameTV = (TextView) findViewById(R.id.nameTV);
    }

    public void showWithData(Object data) {
        if (data instanceof WGHomeFloorContentClassifyItem) {
            WGHomeFloorContentClassifyItem item = (WGHomeFloorContentClassifyItem) data;
            JHImageUtils.getInstance().loadImage(item.pictureURL, R.drawable.common_image_loading_small, mImageView);
            mNameTV.setText(item.name);
        }
    }
}
