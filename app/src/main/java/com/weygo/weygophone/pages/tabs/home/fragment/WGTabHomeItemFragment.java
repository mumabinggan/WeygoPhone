package com.weygo.weygophone.pages.tabs.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHActivity;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.classifyDetail.WGClassifyDetailActivity;
import com.weygo.weygophone.pages.collection.adapter.WGGoodListAdapter;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;
import com.weygo.weygophone.pages.goodDetail.model.response.WGAddGoodToCartResponse;
import com.weygo.weygophone.pages.register.WGRegisterActivity;
import com.weygo.weygophone.pages.specialClassify.WGSpecialClassifyActivity;
import com.weygo.weygophone.pages.specialClassifyGood.WGSpecialClassifyGoodActivity;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.adapter.WGHomeFragmentAdapter;
import com.weygo.weygophone.pages.tabs.home.model.WGHome;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorBaseContentItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;

/**
 * Created by muma on 2017/6/7.
 */

public class WGTabHomeItemFragment extends WGFragment {

    protected TwinklingRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected WGHomeFragmentAdapter mAdapter;

    protected WGHome mData;

    public interface OnListener {
        void onRefresh();
        void onSwitchTab(WGHomeFloorContentClassifyItem item);
        void onAddShopCart(WGHomeFloorContentGoodItem item, Point fromPoint);
    }

    OnListener mListener;
    public void setListener(OnListener listener) {
        mListener = listener;
    }

//    public interface ItemOnClickListener {
//
//    }
//    ItemOnClickListener mItemClickListener;
//    public void setItemClickListener(ItemOnClickListener listener) {
//        mItemClickListener = listener;
//    }

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
//        //JHDividerItemDecoration decoration = new JHDividerItemDecoration(getContext(),
//                JHDividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(getContext(),
                JHDividerItemDecoration.VERTICAL_LIST));
        //mRecyclerView.setBackgroundColor(Color.RED);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WGHomeFragmentAdapter(getContext(), null);
        mAdapter.setListener(new WGHomeFragmentAdapter.OnItemListener() {
            @Override
            public void onCarouselFigures(int index) {
                handleCarouselFigures(index);
            }

            @Override
            public void onTopicItem(WGTopicItem item) {
                handleTopicItem(item);
            }

            @Override
            public void onFloorHead(WGHomeFloorItem item) {
                handleFloorHead(item);
            }

            @Override
            public void onFloorCountryItem(WGHomeFloorBaseContentItem item) {
                handleFloorCountryItem(item);
            }

            @Override
            public void onPurchase(WGHomeFloorContentGoodItem item, View view, Point fromPoint) {
                handlePurchase(item, view, fromPoint);
            }

            @Override
            public void onGoodItem(WGHomeFloorContentGoodItem item) {
                handleGoodItem(item);
            }

            @Override
            public void onClassifyItem(WGHomeFloorBaseContentItem item) {
                handleClassifyItem(item);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = (TwinklingRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                handleOnRefresh();
            }
        });
    }

    void handleCarouselFigures(int index) {
        if (mData != null && mData.carouselFigures != null && mData.carouselFigures.size() > index) {
            WGCarouselFigureItem item = mData.carouselFigures.get(index);
            handleItem(item.id, item.name, item.jumpType);
        }
    }

    void handleTopicItem(WGTopicItem item) {
        handleItem(item.id, item.name, item.jumpType);
    }

    void handleFloorHead(WGHomeFloorItem item) {
        handleItem(item.id, item.name, item.jumpType);
    }

    void handleFloorCountryItem(WGHomeFloorBaseContentItem item) {
        handleItem(item.id, item.name, item.jumpType);
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
        if (mListener != null) {
            mListener.onAddShopCart(item, fromPoint);
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

    void handleClassifyItem(WGHomeFloorBaseContentItem item) {
        handleItem(item.id, item.name, item.jumpType);
    }

    void handleGoodItem(WGHomeFloorContentGoodItem item) {
        Intent intent = new Intent(getActivity(), WGGoodDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void handleItem(long id, String name, int jumpType) {
        Intent intent = null;
        if (jumpType == WGConstants.WGAppJumpTypeRegister) {
            if (!WGApplicationUserUtils.getInstance().isLogined()) {
                intent = new Intent(getActivity(), WGRegisterActivity.class);
                startActivity(intent);
            }
        }
        else if (jumpType == WGConstants.WGAppJumpTypeInvitation) {

        }
        else if (jumpType == WGConstants.WGAppJumpTypeGoodDetail) {
            intent = new Intent(getActivity(), WGGoodDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(WGConstants.WGIntentDataKey, id);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (jumpType == WGConstants.WGAppJumpTypeClassifyDetail) {
            intent = new Intent(getActivity(), WGClassifyDetailActivity.class);
            Bundle bundle = new Bundle();
            WGClassifyItem data = new WGClassifyItem();
            data.id = id;
            data.name = name;
            bundle.putSerializable(WGConstants.WGIntentDataKey, data);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else if (jumpType == WGConstants.WGAppJumpTypeSpecailClassifyHomeTab) {
            WGHomeFloorContentClassifyItem data = new WGHomeFloorContentClassifyItem();
            data.id = id;
            data.name = name;
            data.jumpType = jumpType;
            JHActivity activity = (JHActivity) getActivity();
            if (activity != null) {
                activity.sendNotification(WGConstants.WGNotificationTypeHomeTab, data);
            }
        }
        else if (jumpType == WGConstants.WGAppJumpTypeSpecailClassifyGoodBenefitTab) {
            WGHomeFloorContentClassifyItem data = new WGHomeFloorContentClassifyItem();
            data.id = id;
            data.name = name;
            data.jumpType = jumpType;
            JHActivity activity = (JHActivity) getActivity();
            if (activity != null) {
                activity.sendNotification(WGConstants.WGNotificationTypeBenefitTab, data);
            }
        }
        else if (jumpType == WGConstants.WGAppJumpTypeSpecailClassifyNoTab) {
            WGHomeFloorContentClassifyItem data = new WGHomeFloorContentClassifyItem();
            data.id = id;
            data.name = name;
            data.jumpType = jumpType;
            handleSwitchSpecialClassify(data, false);
        }
        else if (jumpType == WGConstants.WGAppJumpTypeSpecailClassifyGoodNoTab) {
            WGHomeFloorContentClassifyItem data = new WGHomeFloorContentClassifyItem();
            data.id = id;
            data.name = name;
            data.jumpType = jumpType;
            handleSwitchSpecialClassify(data, true);
        }
    }

    void handleSwitchSpecialClassify(WGHomeFloorContentClassifyItem item, boolean isGood) {
        Intent intent = null;
        if (isGood) {
            intent = new Intent(getActivity(), WGSpecialClassifyGoodActivity.class);
        }
        else {
            intent = new Intent(getActivity(), WGSpecialClassifyActivity.class);
        }
        Bundle bundle = new Bundle();
        WGClassifyItem data = new WGClassifyItem();
        data.id = item.id;
        data.name = item.name;
        bundle.putSerializable(WGConstants.WGIntentDataKey, data);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void stopRefreshing() {
        mRefreshLayout.finishRefreshing();
    }

    public void refresh(WGHome data) {
        mData = data;
        mAdapter.setHome(data);
    }

    public void handleOnRefresh() {
        if (mListener != null) {
            mListener.onRefresh();
        }
    }
}
