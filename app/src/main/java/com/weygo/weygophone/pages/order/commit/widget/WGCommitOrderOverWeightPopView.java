package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.widget.JHPopView;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightDetail;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderOverWeightPopView extends JHPopView {

    TextView mTitleTV;

    TextView mMaxRiseTV;

    TextView mCurrentRiseTV;

    ScrollView mScrollView;

    LinearLayout mLayout;

    TextView mDeleteAllTV;

    TextView mConfirmTV;

    public interface OnItemListener {
        void onDeleteAll();
        void onConfirm(List<WGOverHeightDetail> overWeightList);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    List<WGOverHeightDetail> mData;

    public WGCommitOrderOverWeightPopView(Context context) {
        super(context);
    }

    public WGCommitOrderOverWeightPopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderOverWeightPopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initConfig(Context context) {
        super.initConfig(context);
        mCanClose = false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitleTV = (TextView) findViewById(R.id.overWeightTitleTV);
        mMaxRiseTV = (TextView) findViewById(R.id.maxRiseTV);
        mCurrentRiseTV = (TextView) findViewById(R.id.currentRiseTV);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mLayout = (LinearLayout) findViewById(R.id.contentLayout);
        mDeleteAllTV = (TextView) findViewById(R.id.deleteAllTV);
        mDeleteAllTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPopupWindow.dismiss();
                if (mListener != null) {
                    mListener.onDeleteAll();
                }
            }
        });
        mConfirmTV = (TextView) findViewById(R.id.confirmTV);
        mConfirmTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPopupWindow.dismiss();
                if (mListener != null) {
                    mListener.onConfirm(mData);
                }
            }
        });
    }

    public void setGoods(List<WGOverHeightDetail> overWeightList) {
        mData = overWeightList;
        WGOverHeightDetail overWeightItem = mData.get(0);
        mTitleTV.setText(overWeightItem.title);
        mMaxRiseTV.setText(JHResourceUtils.getInstance()
                .getString(R.string.CommitOrder_OverHeight_MaxRise)+
                overWeightItem.maxRise+
                JHResourceUtils.getInstance().getString(R.string.CommitOrder_OverHeight_Rise));
        mCurrentRiseTV.setText(" " + JHResourceUtils.getInstance()
                .getString(R.string.CommitOrder_OverHeight_CurrentRise)+
                overWeightItem.currentRise()+
                JHResourceUtils.getInstance().getString(R.string.CommitOrder_OverHeight_Rise) + " ");
        if (Float.parseFloat(overWeightItem.maxRise) > overWeightItem.currentRise()) {
            mCurrentRiseTV.setBackgroundResource(R.drawable.commitorder_currentrise_less_max_bg);
        }
        else {
            mCurrentRiseTV.setBackgroundResource(R.drawable.commitorder_currentrise_more_max_bg);
        }
        mLayout.removeAllViews();
        for (WGOverHeightGoodItem item : overWeightItem.goods) {
            WGCommitOrderOverWeightItemView view = (WGCommitOrderOverWeightItemView) LayoutInflater.from(mContext)
                    .inflate(R.layout.commitorder_overheight_good_item, null);
            view.setListener(new WGCommitOrderOverWeightItemView.OnItemListener() {
                @Override
                public void onAdd(WGOverHeightGoodItem item) {
                    showWithData(mData);
                }

                @Override
                public void onSub(WGOverHeightGoodItem item) {
                    showWithData(mData);
                }
            });
            view.setGravity(Gravity.CENTER);
            view.showWithData(item);
            mLayout.addView(view);
        }
    }
}
