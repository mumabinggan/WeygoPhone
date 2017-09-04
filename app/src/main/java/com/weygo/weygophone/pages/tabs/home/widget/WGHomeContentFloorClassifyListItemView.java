package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentItem;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentFloorClassifyListItemView extends JHRelativeLayout {

    ImageView mImageView;

    TextView mNameTV;

    WGHomeFloorContentClassifyItem mData;

    public interface OnItemListener {
        void onItemClick(WGHomeFloorContentClassifyItem data);
    }
    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGHomeContentFloorClassifyListItemView(Context context) {
        super(context);
    }

    public WGHomeContentFloorClassifyListItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentFloorClassifyListItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNameTV = (TextView) findViewById(R.id.nameTV);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(mData);
                }
            }
        });
    }

    public void showWithData(Object data) {
        if (data instanceof WGHomeFloorContentClassifyItem) {
            mData = (WGHomeFloorContentClassifyItem) data;
            JHImageUtils.getInstance().loadImage(mData.pictureURL, R.drawable.common_image_loading_small, mImageView);
            mNameTV.setText(mData.name);
        }
    }
}
