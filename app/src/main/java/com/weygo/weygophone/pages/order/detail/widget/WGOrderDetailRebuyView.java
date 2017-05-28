package com.weygo.weygophone.pages.order.detail.widget;

import android.content.Context;
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

public class WGOrderDetailRebuyView extends RelativeLayout {

    Button mMoreBtn;

    public OnRebuyListener mOnRebuyListener;

    public interface OnRebuyListener extends JHInterface {
        void onTouchRebuy();
    }

    public WGOrderDetailRebuyView(Context context) {
        this(context, null);
    }

    public WGOrderDetailRebuyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderDetailRebuyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMoreBtn = (Button) findViewById(R.id.moreBtn);
        mMoreBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnRebuyListener!= null) {
                    mOnRebuyListener.onTouchRebuy();
                }
            }
        });
    }
}
