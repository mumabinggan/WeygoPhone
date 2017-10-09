package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.widget.WGAddGoodView;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderOverWeightItemView extends JHRelativeLayout {

    public interface OnItemListener {
        void onAdd(WGOverHeightGoodItem item);
        void onSub(WGOverHeightGoodItem item);
    }

    ImageView mImageView;

    TextView mNameTV;

    TextView mBriefTV;

    WGAddGoodView mAddView;

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    WGOverHeightGoodItem mData;

    public WGCommitOrderOverWeightItemView(Context context) {
        super(context);
    }

    public WGCommitOrderOverWeightItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderOverWeightItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mImageView = (ImageView) findViewById(R.id.imageView);
        mNameTV = (TextView) findViewById(R.id.nameTextView);
        mBriefTV = (TextView) findViewById(R.id.briefTextView);
        mAddView = (WGAddGoodView) findViewById(R.id.addView);
        mAddView.setListener(new WGAddGoodView.OnItemListener() {
            @Override
            public void onAdd(int count) {
                if (mListener != null) {
                    mData.goodCount++;
                    mListener.onAdd(mData);
                }
            }

            @Override
            public void onSub(int count) {
                if (mListener != null) {
                    mData.goodCount--;
                    mListener.onSub(mData);
                }
            }
        });
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGOverHeightGoodItem) {
            mData = (WGOverHeightGoodItem) object;
            JHImageUtils.getInstance().loadImage(mData.pictureURL, R.drawable.common_image_loading_small, mImageView);
            mNameTV.setText(mData.name);
            mBriefTV.setText(mData.briefDescription);
            mAddView.setCount(mData.goodCount);

        }
    }
}
