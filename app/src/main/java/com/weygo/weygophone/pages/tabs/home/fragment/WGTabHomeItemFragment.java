package com.weygo.weygophone.pages.tabs.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.pages.collection.adapter.WGGoodListAdapter;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.tabs.home.adapter.WGHomeFragmentAdapter;
import com.weygo.weygophone.pages.tabs.home.model.WGHome;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

/**
 * Created by muma on 2017/6/7.
 */

public class WGTabHomeItemFragment extends WGFragment {

    protected TwinklingRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected WGHomeFragmentAdapter mAdapter;

    protected WGHome mData;

    public interface HomeItemOnItemClickListener {
        void onRefresh();
    }

    HomeItemOnItemClickListener mListener;
    public void setListener(HomeItemOnItemClickListener listener) {
        mListener = listener;
    }

    //@Override
    public int fragmentResId() {
        return R.layout.wghome_content_fragment;
    }

    //@Override
    public void initData() {
      //  super.initData();
    }

    //@Override
    public void initSubView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(getContext(),
                JHDividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WGHomeFragmentAdapter(getContext(), null);
        mRecyclerView.setAdapter(mAdapter);
//        mAdapter.setOnItemClickListener(new WGHomeFragmentAdapter);
//
//        mAdapter.setOnItemClickListener(new WGGoodListAdapter.WGGoodListOnItemClickListener() {
//            @Override
//            public void onTouchGoodItem(View view, WGHomeFloorContentGoodItem item) {
//                handleTouchGoodItem(view, item);
//            }
//            @Override
//            public void onTouchAddCart(View view, WGHomeFloorContentGoodItem item) {
//                handleTouchAddCart(view, item);
//            }
//            @Override
//            public void onItemClick(View view, int position) {
//            }
//
//            @Override
//            public void onLongTouchGoodItem(View view, WGHomeFloorContentGoodItem item) {
//                handleLongTouchGoodItem(view, item);
//            }
//        });

        mRefreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                handleOnRefresh();
            }
        });
    }

    public void stopRefreshing() {
        mRefreshLayout.finishRefreshing();
    }

    public void refresh(WGHome data) {
        mAdapter.setHome(data);
    }

    public void handleOnRefresh() {
        if (mListener != null) {
            mListener.onRefresh();
        }
    }
}
