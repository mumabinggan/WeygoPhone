package com.weygo.weygophone.pages.order.detail.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.weygo.common.base.JHInterface;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/23.
 */

public class WGOrderDetailGoodsMoreView extends LinearLayout {

    Button mMoreBtn;

    OnGoodsMore mOnMore;

    public interface OnGoodsMore extends JHInterface {
        void onTouchMore();
    }

    public WGOrderDetailGoodsMoreView(Context context) {
        this(context, null);
    }

    public WGOrderDetailGoodsMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderDetailGoodsMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMoreBtn = (Button) findViewById(R.id.moreBtn);
        mMoreBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnMore!= null) {
                    mOnMore.onTouchMore();
                }
            }
        });
    }

    public void setOnShowGoodMore(OnGoodsMore onMore) {
        mOnMore = onMore;
    }

    public void setVisibility(boolean visibility) {
        RecyclerView.LayoutParams param = (RecyclerView.LayoutParams)getLayoutParams();
        if (visibility) {
            param.width = RelativeLayout.LayoutParams.MATCH_PARENT;
            setVisibility(View.VISIBLE);
        }
        else {
            param.height = 0;
            param.width = 0;
            setVisibility(View.GONE);
        }
    }
}
