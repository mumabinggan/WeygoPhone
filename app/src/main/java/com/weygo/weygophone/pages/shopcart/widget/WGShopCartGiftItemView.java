package com.weygo.weygophone.pages.shopcart.widget;

import android.content.Context;
import android.content.IntentFilter;
import android.media.Image;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.shopcart.model.WGShopCartGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

/**
 * Created by muma on 2017/8/18.
 */

public class WGShopCartGiftItemView extends JHRelativeLayout {

    ImageView mImageView;

    TextView mTextView;

    public WGShopCartGiftItemView(Context context) {
        super(context);
    }

    public WGShopCartGiftItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGShopCartGiftItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mTextView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGHomeFloorContentGoodItem) {
            WGHomeFloorContentGoodItem item = (WGHomeFloorContentGoodItem) object;
            JHImageUtils.getInstance().loadImage(item.pictureURL, R.drawable.common_image_loading_small, mImageView);
            mTextView.setText(item.name + "\n" + item.briefDescription);
        }
    }
}
