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

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentFloorImageTitleView extends JHRelativeLayout {

    ImageView mImageView;

    TextView mNameTV;

    TextView mBreifDesTV;

    TextView mMoreTV;

    WGHomeFloorItem mData;

    public interface OnItemListener {
        void onItemClick(Object object);
    }
    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

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
        if (data instanceof WGHomeFloorItem) {
            mData = (WGHomeFloorItem) data;
            JHImageUtils.getInstance().loadImage(mData.pictureURL, R.drawable.common_image_loading_small, mImageView);
            mNameTV.setText(mData.pictureName);
            mBreifDesTV.setText(mData.pictureBriefDescription);
            if (JHStringUtils.isNullOrEmpty(mData.pictureBtnName)) {
                mMoreTV.setVisibility(INVISIBLE);
            }
            else {
                mMoreTV.setVisibility(VISIBLE);
                String moreString = "    " + mData.pictureBtnName + "    ";
                mMoreTV.setText(moreString);
            }
        }
    }
}
