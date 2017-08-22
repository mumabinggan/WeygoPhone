package com.weygo.weygophone.pages.shopcart.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.widget.JHPopView;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.shopcart.model.WGShopCartGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by muma on 2017/8/18.
 */

public class WGShopCartGiftPopView extends JHPopView {

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

    public WGShopCartGiftPopView(Context context) {
        super(context);
    }

    public WGShopCartGiftPopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGShopCartGiftPopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mScrollView = (ScrollView) findViewById(R.id.scrollView);
        mLayout = (LinearLayout) findViewById(R.id.contentLayout);
        mOkTV = (TextView) findViewById(R.id.okTV);
        mOkTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                if (mListener != null) {
                    mListener.onOk();
                }
            }
        });
        mNoTV = (TextView) findViewById(R.id.noTV);
        mNoTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
                if (mListener != null) {
                    mListener.onNo();
                }
            }
        });
    }

    public void setGoods(List<WGShopCartGoodItem> goods) {
        for (WGShopCartGoodItem item : goods) {
            WGShopCartGiftItemView view = (WGShopCartGiftItemView) LayoutInflater.from(mContext)
                    .inflate(R.layout.shopcart_gift_item, null);
            view.setGravity(Gravity.CENTER);
            view.showWithData(item);
            mLayout.addView(view);
        }
    }
}
