package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.weygo.common.widget.JHPopView;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.shopcart.widget.WGShopCartGiftItemView;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderExpirePopView extends JHPopView {

    RelativeLayout mRelativeLayout;

    ScrollView mScrollView;

    LinearLayout mLayout;

    TextView mOkTV;

    TextView mNoTV;

    public interface OnItemListener {
        void onOk();
        void onNo();
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGCommitOrderExpirePopView(Context context) {
        super(context);
    }

    public WGCommitOrderExpirePopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderExpirePopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        mRelativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mLayout = (LinearLayout) findViewById(R.id.contentLayout);
        mOkTV = (TextView) findViewById(R.id.okTV);
        mOkTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPopupWindow.dismiss();
                if (mListener != null) {
                    mListener.onOk();
                }
            }
        });
        mNoTV = (TextView) findViewById(R.id.noTV);
        mNoTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPopupWindow.dismiss();
                if (mListener != null) {
                    mListener.onNo();
                }
            }
        });
    }

    public void setGoods(List<WGHomeFloorContentGoodItem> goods) {
        for (WGHomeFloorContentGoodItem item : goods) {
            WGCommitOrderExpireItemView view = (WGCommitOrderExpireItemView) LayoutInflater.from(mContext)
                    .inflate(R.layout.commitorder_expire_good_item, null);
            view.setGravity(Gravity.CENTER);
            view.showWithData(item);
            mLayout.addView(view);
        }
    }

    public void setOnlyDelete() {
        mNoTV.setVisibility(INVISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)mRelativeLayout.getLayoutParams();
        //params.height =
    }
}
