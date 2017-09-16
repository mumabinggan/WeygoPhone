package com.weygo.weygophone.pages.classifyDetail.fragment;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGOptionPickerView;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.classifyDetail.adapter.WGClassifyDetailContentAdapter;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyDetail;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyFilterCondition;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyKeyword;
import com.weygo.weygophone.pages.classifyDetail.model.request.WGClassifyDetailRequest;
import com.weygo.weygophone.pages.classifyDetail.model.response.WGClassifyDetailResponse;
import com.weygo.weygophone.pages.classifyDetail.widget.WGClassifyDetailFilterView;
import com.weygo.weygophone.pages.classifyFilter.WGClassifyDetailFilterActivity;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.goodDetail.model.response.WGAddGoodToCartResponse;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.classify.model.request.WGClassifyRequest;
import com.weygo.weygophone.pages.tabs.classify.model.response.WGClassifyResponse;
import com.weygo.weygophone.pages.tabs.home.adapter.WGHomeFragmentAdapter;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

import static android.app.Activity.RESULT_OK;

/**
 * Created by muma on 2017/8/2.
 */

public class WGClassifyDetailContentFragment extends WGFragment {

    protected TwinklingRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;

    WGClassifyDetailFilterView mFilterView;

    WGClassifyFilterCondition mFilterCondition;

    WGClassifyDetailContentAdapter mAdapter;

    public interface RefreshListener {
        void onRefresh();
        void onRequestCompletion(WGClassifyDetail mData);
        void onAddShopCart(WGHomeFloorContentGoodItem item, Point fromPoint);
    }

    RefreshListener mRefreshListener;
    public void setListener(RefreshListener listener) {
        mRefreshListener = listener;
    }


    WGClassifyDetail mData;
    public void setData(WGClassifyDetail data) {
        mData = data;
    }

    long mClassifyId;
    public void setClassifyId(long classifyId) {
        mClassifyId = classifyId;
    }

    @Override
    public int fragmentResId() {
        return R.layout.classifydetail_content_fragment;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initSubView(View view) {
        super.initSubView(view);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(getContext(),
                JHDividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WGClassifyDetailContentAdapter(getContext(), mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new WGClassifyDetailContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onSort(View view) {
                handleSort();
            }

            @Override
            public void onFilter(View view) {
                handleFilter();
            }

            @Override
            public void onGrid(View view) {
                handleVista();
            }

            @Override
            public void onGoodItem(WGHomeFloorContentGoodItem item) {
                handleGoodItem(item);
            }

            @Override
            public void onShopCart(WGHomeFloorContentGoodItem item, View view, Point point) {
                handlePurchase(item, view, point);
            }
        });

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalDy += dy;
                Log.e("---------", "dx:" + dx + "=totalDy:" + totalDy + "vs" + JHAdaptScreenUtils.pixelWidth(getActivity(), 150));
                if (totalDy >= JHAdaptScreenUtils.pixelWidth(getActivity(), 150)) {
                    mFilterView.setVisibility(View.VISIBLE);
                }
                else {
                    mFilterView.setVisibility(View.INVISIBLE);
                }
            }
        });
        mRefreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                loadClassifyDetail(true, true);
            }
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadClassifyDetail(false, true);
            }
        });

        mFilterView = (WGClassifyDetailFilterView) view.findViewById(R.id.filterView);
        mFilterView.setListener(new WGClassifyDetailFilterView.OnItemClickListener() {
            @Override
            public void onSort(View view) {
                handleSort();
            }

            @Override
            public void onFilter(View view) {
                handleFilter();
            }

            @Override
            public void onVista(View view) {
                handleVista();
            }
        });
    }

    public void stopRefreshing() {
        mRefreshLayout.finishRefreshing();
    }

    void handleOnRefresh() {

    }

    void handleGoodItem(WGHomeFloorContentGoodItem item) {
        Intent intent = new Intent(getActivity(), WGGoodDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void handlePurchase(WGHomeFloorContentGoodItem item, View view, Point fromPoint) {
        if (JHStringUtils.isNullOrEmpty(WGApplicationUserUtils.getInstance().currentPostCode())) {
            WGPostPopView popupView = (WGPostPopView) getActivity().getLayoutInflater().inflate(R.layout.common_cap_pop, null);
            popupView.setListener(new WGPostPopView.OnItemListener() {
                @Override
                public void onSuccess() {

                }
            });
            JHBasePopupWindow window = new JHBasePopupWindow(popupView,
                    JHAdaptScreenUtils.devicePixelWidth(getActivity()),
                    JHAdaptScreenUtils.devicePixelHeight(getActivity()));
            popupView.setPopupWindow(window);
            window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            return;
        }
        WGApplicationRequestUtils.getInstance().loadAddGoodToCart(item.id, 1, new WGApplicationRequestUtils.WGOnCompletionInteface() {
            @Override
            public void onSuccessCompletion(WGResponse response) {
                handleShopCartCount((WGAddGoodToCartResponse) response);
            }

            @Override
            public void onFailCompletion(WGResponse response) {

            }
        });
        if (mRefreshListener != null) {
            mRefreshListener.onAddShopCart(item, fromPoint);
        }
    }

    void handleShopCartCount(WGAddGoodToCartResponse response) {
        if (response.success()) {
            WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(response.data.goodCount);
        }
        else {
            showWarning(response.message);
        }
    }

    void handleSort() {
        WGOptionPickerView picker = new WGOptionPickerView(getActivity(), new String[]{
                JHResourceUtils.getInstance().getString(R.string.ClassifyDetail_Sort),
                JHResourceUtils.getInstance().getString(R.string.ClassifyDetail_Brand),
                JHResourceUtils.getInstance().getString(R.string.ClassifyDetail_Price_Up),
                JHResourceUtils.getInstance().getString(R.string.ClassifyDetail_Price_Down)
        });
//        WGOptionPickerView picker = new WGOptionPickerView(getActivity(), mData.mSortTitles);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                Log.e("onOptionPicked", "" + index + "item" + "-----------");
                showWarning("index=" + index + ", item=" + item);
                selectSortType(index);
            }
        });
        picker.show();
    }

    void selectSortType(int index) {
        mData.setSortType(index);
        mAdapter.setData(mData);
        mFilterView.showWithData(mData);
        loadClassifyDetail(true, false);
    }

    void handleFilter() {
        Intent intent = new Intent(getActivity(), WGClassifyDetailFilterActivity.class);
        Bundle bundle = new Bundle();
        if (mFilterCondition!= null) {
            bundle.putSerializable(WGConstants.WGIntentDataKey, mFilterCondition);
        }
        else {
            bundle.putSerializable(WGConstants.WGIntentDataKey, mClassifyId);
        }
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }

    void handleVista() {
        mData.setGrid(!mData.isGrid());
        mFilterView.showWithData(mData);
        mAdapter.setData(mData);
    }

    @Override
    public void loadRequest() {
        super.loadRequest();
        if (mData == null) {
            loadClassifyDetail(true, false);
        }
    }

    void loadClassifyDetail(final boolean refresh, final boolean pulling) {
        WGClassifyDetailRequest request = new WGClassifyDetailRequest();
        request.classifyId = mClassifyId;
        StringBuilder subClassifyIds = new StringBuilder();
        if (mFilterCondition != null) {
            for (WGClassifyKeyword item : mFilterCondition.brands) {
                if (item.isSelected) {
                    subClassifyIds.append(item.id);
                    subClassifyIds.append(",");
                }
            }
            if (subClassifyIds.length() > 0) {
                subClassifyIds.deleteCharAt(subClassifyIds.length()-1);
            }
        }
        request.subClassifyIds = subClassifyIds.toString();

        StringBuilder keywords = new StringBuilder();
        if (mFilterCondition != null) {
            for (WGClassifyItem item : mFilterCondition.classifys) {
                if (item.isSelected) {
                    subClassifyIds.append(item.id);
                    subClassifyIds.append(",");
                }
            }
            if (keywords.length() > 0) {
                keywords.deleteCharAt(keywords.length()-1);
            }
        }
        request.subClassifyIds = keywords.toString();
        request.brandIds = subClassifyIds.toString();
        Integer count = 0;
        if (!refresh) {
            if (mData != null && mData.goodArray != null) {
                count = mData.goodArray.size();
            }
        }
        request.pageId = count;
        request.pageSize = 10;
        int order = 0;
        if (mData != null) {
            order = mData.sortType();
        }
        request.order = order;
        if (pulling) {
            request.showsLoadingView = false;
        }
        this.postAsyn(request, WGClassifyDetailResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessClassifyDetail((WGClassifyDetailResponse) result, refresh, pulling);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessClassifyDetail(WGClassifyDetailResponse response, final boolean refresh, final boolean pulling) {
        Log.e("onSuccess", JSON.toJSONString(response));
        mRefreshLayout.finishRefreshing();
        if (response.success()) {
            if (refresh) {
                mData = response.data;
            }
            else {
                mData.recommendedArray = response.data.recommendedArray;
                List list = new ArrayList<>();
                if (mData != null && mData.goodArray != null &&
                        mData.goodArray.size() > 0) {
                    list.addAll(mData.goodArray);
                }
                if (response.data != null && response.data.goodArray != null &&
                        response.data.goodArray.size() > 0) {
                    list.addAll(response.data.goodArray);
                }
                mData.goodArray = list;
            }
            if (mRefreshListener != null && refresh) {
                mRefreshListener.onRequestCompletion(mData);
            }
            mAdapter.setData(mData);
        }
        else {
            showWarning(response.message);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setUserVisibleHint(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                mFilterCondition = (WGClassifyFilterCondition) bundle.getSerializable(WGConstants.WGIntentDataKey);
                loadClassifyDetail(true, false);
            }
        }
    }
}
