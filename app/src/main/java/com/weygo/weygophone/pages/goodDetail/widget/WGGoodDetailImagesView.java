package com.weygo.weygophone.pages.goodDetail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.widget.WGCountDownTimeView;
import com.weygo.weygophone.common.widget.WGRotationImagesView;
import com.weygo.weygophone.pages.classifyDetail.model.WGHomeRotation;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;
import com.jude.rollviewpager.OnItemClickListener;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodsView;


import java.util.List;

/**
 * Created by muma on 2017/5/31.
 */

public class WGGoodDetailImagesView extends JHRelativeLayout {

    List mlist;

    WGCountDownTimeView mTimeView;

    public interface OnItemListener {
        void onItemClick(int index);
    }
    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    WGRotationImagesView mScrollImageView;

    public WGGoodDetailImagesView(Context context) {
        this(context, null);
    }

    public WGGoodDetailImagesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGGoodDetailImagesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mScrollImageView = (WGRotationImagesView) findViewById(R.id.scrollImageView);
        mScrollImageView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mListener != null) {
                    mListener.onItemClick(position);
                }
            }
        });
        mTimeView = (WGCountDownTimeView) findViewById(R.id.countdownTimeView);
    }

    public void showWithArray(List list) {
        if (mlist == null) {
            mlist = list;
            mScrollImageView.setData(list);
            return;
        }
        if (!mlist.equals(list)) {
            mScrollImageView.setData(list);
        }
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        WGHomeRotation data = (WGHomeRotation)object;

        if (data != null) {
            if (mlist == null) {
                mlist = data.mList;
                mScrollImageView.setData(mlist);
                mTimeView.showWithData(data.time);
                return;
            }
            if (!mlist.equals(data.mList)) {
                mlist = data.mList;
                mScrollImageView.setData(mlist);
                mTimeView.showWithData(data.time);
            }
        }
        else {
            mTimeView.setVisibility(INVISIBLE);
        }
    }
}
