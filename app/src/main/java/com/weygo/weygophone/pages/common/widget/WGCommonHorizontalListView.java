package com.weygo.weygophone.pages.common.widget;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.common.adapter.JHCommonHorizontalListAdapter;
import com.weygo.weygophone.pages.order.list.adapter.WGOrderListGoodsAdapter;

import java.util.List;

/**
 * Created by muma on 2017/6/4.
 */

public class WGCommonHorizontalListView extends LinearLayout {

    RecyclerView mRecyclerView;
    JHCommonHorizontalListAdapter mAdapter;
    List mArray;

    public WGCommonHorizontalListView(Context context) {
        this(context, null);
    }

    public WGCommonHorizontalListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommonHorizontalListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        mAdapter = new JHCommonHorizontalListAdapter(getContext(), mArray, itemResId());
        mRecyclerView.setAdapter(mAdapter);
    }

    protected int itemResId() {
        return 0;
    }

    public void showWithArray(List array) {
        if (array.equals(mArray)) {
            return;
        }
        mArray = array;
        mAdapter.setData(mArray);
    }
}
