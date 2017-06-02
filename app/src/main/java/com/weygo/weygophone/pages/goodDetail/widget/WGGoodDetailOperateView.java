package com.weygo.weygophone.pages.goodDetail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.weygo.common.base.JHInterface;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/31.
 */

public class WGGoodDetailOperateView extends RelativeLayout {

    WGGoodCountView mCountView;

    Button mAddShopCartBtn;

    ImageView mCollectionIV;

    OnGoodOperateListener mListener;

    interface OnGoodOperateListener extends JHInterface {
        void onAddShopCart();
        void onCollection();
    }

    public WGGoodDetailOperateView(Context context) {
        this(context, null);
    }

    public WGGoodDetailOperateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGGoodDetailOperateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCountView = (WGGoodCountView) findViewById(R.id.goodCountView);

        mAddShopCartBtn = (Button) findViewById(R.id.addShopCartBtn);
        mAddShopCartBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddShopCartBtn();
            }
        });

        mCollectionIV = (ImageView) findViewById(R.id.collectionIV);
        mCollectionIV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCollectionBtn();
            }
        });
    }

    void handleAddShopCartBtn() {
        if (mListener != null) {
            mListener.onAddShopCart();
        }
    }

    void handleCollectionBtn() {
        if (mListener != null) {
            mListener.onCollection();
        }
    }

    public void setListener(OnGoodOperateListener listener) {
        mListener = listener;
    }
}
