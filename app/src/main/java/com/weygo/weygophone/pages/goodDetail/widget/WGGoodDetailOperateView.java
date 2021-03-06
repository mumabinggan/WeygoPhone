package com.weygo.weygophone.pages.goodDetail.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.weygo.common.base.JHInterface;
import com.weygo.common.tools.JHColorUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;

/**
 * Created by muma on 2017/5/31.
 */

public class WGGoodDetailOperateView extends RelativeLayout {

    WGGoodCountView mCountView;

    public Button mAddShopCartBtn;

    ImageView mCollectionIV;

    OnGoodOperateListener mListener;

    public interface OnGoodOperateListener extends JHInterface {
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

    public void showWithData(WGGoodDetail goodDetail) {
        if (goodDetail != null)   {
            if (goodDetail.hasFavorited > 0) {
                mCollectionIV.setImageResource(R.drawable.gooddetail_collection_selected);
            }
            else {
                mCollectionIV.setImageResource(R.drawable.gooddetail_collection_normal);
            }
            mCountView.setCount(1);
            int textId = 0;
            int resId = 0;
            if (goodDetail.hasInvalidGood()) {
                resId = R.drawable.wggooddetail_add_unable_bg;
                textId = R.string.GoodDetail_Without;
            }
            else {
                resId = R.drawable.wggooddetail_add_bg;
                textId = R.string.GoodDetail_Add;
            }
            mAddShopCartBtn.setBackgroundResource(resId);
            mAddShopCartBtn.setText(textId);
        }
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

    public long getGoodCount() {
        return mCountView.getCount();
    }
}
