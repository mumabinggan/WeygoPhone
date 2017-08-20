package com.weygo.weygophone.pages.order.list.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.collection.adapter.WGGoodListAdapter;
import com.weygo.weygophone.pages.order.list.adapter.WGOrderListGoodsAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListGoodsView extends JHRelativeLayout {

    RecyclerView mRecyclerView;
    WGOrderListGoodsAdapter mAdapter;
    List mArray;

    public WGOrderListGoodsView(Context context) {
        this(context, null);
    }

    public WGOrderListGoodsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderListGoodsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new WGOrderListGoodsAdapter(getContext(), mArray);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void showWithArray(List array) {
        if (array.equals(mArray)) {
            return;
        }
        mArray = array;
        mAdapter.setData(mArray);
    }
}
