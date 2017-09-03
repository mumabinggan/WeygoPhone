package com.weygo.weygophone.pages.order.list.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.list.adapter.WGOrderListAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListRebuyView extends JHLinearLayout {

    Button mRebuyBtn;
    WGOrderListItem mData;

    public interface OnItemListener {
        void onRebuy(View view, WGOrderListItem item);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }
    public WGOrderListRebuyView(Context context) {
        this(context, null);
    }

    public WGOrderListRebuyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderListRebuyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRebuyBtn = (Button) findViewById(R.id.rebuyBtn);
        mRebuyBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRebuy(v, mData);
                }
            }
        });
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        mData = (WGOrderListItem)object;
    }
}
