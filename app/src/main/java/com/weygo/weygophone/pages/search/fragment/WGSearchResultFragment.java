package com.weygo.weygophone.pages.search.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGCellStyle7View;
import com.weygo.weygophone.pages.collection.adapter.WGGoodListAdapter;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.search.adapter.WGSearchResultAdapter;
import com.weygo.weygophone.pages.search.model.WGSearchData;
import com.weygo.weygophone.pages.search.model.WGSearchKeywordItem;
import com.weygo.weygophone.pages.search.model.request.WGHotSearchRequest;
import com.weygo.weygophone.pages.search.model.request.WGSearchRequest;
import com.weygo.weygophone.pages.search.model.response.WGHotSearchResponse;
import com.weygo.weygophone.pages.search.model.response.WGSearchResponse;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2016/12/19.
 */

public class WGSearchResultFragment extends WGFragment {

    protected TwinklingRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    WGSearchResultAdapter mAdapter;

    WGSearchData mData;

    String mSearchName;
    public void setSearchName(String searchName) {
        mSearchName = searchName;
    }

    boolean mIsGrid;
    public void setGrid(boolean grid) {
        mIsGrid = grid;
        if (mData != null) {
            mData.isGrid = mIsGrid;
        }
        if (mAdapter != null) {
            mAdapter.setData(mData);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp instanceof String) {
                mSearchName = (String)temp;
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int fragmentResId() {
        return R.layout.search_result_fragment;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initSubView(View view) {
        super.initSubView(view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(getActivity(),
                JHDividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WGSearchResultAdapter(getActivity(), mData);
        mAdapter.setListener(new WGSearchResultAdapter.OnItemListener() {
            @Override
            public void onGoodItem(WGHomeFloorContentGoodItem item) {
                handleGoodItem(item);
            }

            @Override
            public void onHotItem(WGSearchKeywordItem item) {
                handleHotItem(item);
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                loadSearchHouseList(true, true);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadSearchHouseList(false, true);
            }
        });
    }

    void handleHotItem(WGSearchKeywordItem item) {
        WGApplicationGlobalUtils.getInstance().addLocalSettingHistorySearch(item);
        Intent intent = new Intent(getActivity(), WGSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void handleGoodItem(WGHomeFloorContentGoodItem item) {
        Intent intent = new Intent(getActivity(), WGGoodDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void loadRequest() {
        super.loadRequest();
        loadSearchHouseList(true, false);
    }

    void loadSearchHouseList(final boolean refresh, final boolean pulling) {
        final WGSearchRequest request = new WGSearchRequest();
        request.name = mSearchName;
        int pageId = 0;
        if (refresh) {
            pageId = 0;
        }
        else {
            if (mData != null && mData.goods != null) {
                pageId = mData.goods.size();
            }
            else {
                pageId = 0;
            }
        }
        request.pageId = pageId;
        if (pulling) {
            request.showsLoadingView = false;
        }
        this.postAsyn(request, WGSearchResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleSearchResponse((WGSearchResponse)response, refresh, pulling, request.pageSize);
            }

            @Override
            public void onFailure(JHRequestError error) {
                mRefreshLayout.finishRefreshing();
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleSearchResponse(WGSearchResponse response, boolean refresh, boolean pulling, int pageSize) {
        mRefreshLayout.finishRefreshing();
        if (response.success()) {
            if (mData == null) {
                mData = response.data;
            }
            if (refresh) {
                mData = response.data;
            }
            else {
                List goods = new ArrayList();
                if (mData != null && mData.goods != null) {
                    goods.addAll(mData.goods);
                }
                if (response != null && response.data != null && response.data.goods != null) {
                    goods.addAll(response.data.goods);
                }
                mData.goods = goods;
            }
            mData.isGrid = mIsGrid;
            mData.name = mSearchName;
            mAdapter.setData(mData);
        }
        else {
            showWarning(response.message);
        }
    }
}
