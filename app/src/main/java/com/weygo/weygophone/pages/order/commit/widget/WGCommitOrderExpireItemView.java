package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import org.w3c.dom.Text;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderExpireItemView extends JHRelativeLayout {

    ImageView mImageView;

    TextView mNameTV;

    TextView mExpireTimeTV;

    public WGCommitOrderExpireItemView(Context context) {
        super(context);
    }

    public WGCommitOrderExpireItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderExpireItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNameTV = (TextView) findViewById(R.id.nameTextView);
        mExpireTimeTV = (TextView) findViewById(R.id.expireTimeTextView);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGHomeFloorContentGoodItem) {
            WGHomeFloorContentGoodItem item = (WGHomeFloorContentGoodItem) object;
            JHImageUtils.getInstance().loadImage(item.pictureURL, R.drawable.common_image_loading_small, mImageView);
            mNameTV.setText(item.name);
            mExpireTimeTV.setText(item.expiredTime);
        }
    }
}
