package com.weygo.weygophone.pages.collection;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.baoyz.widget.PullRefreshLayout;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.collection.adapter.WGGoodListAdapter;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.goodDetail.model.response.WGAddGoodToCartResponse;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/5/19.
 */

public class WGGoodListActivity extends WGTitleActivity {

    protected TwinklingRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected WGGoodListAdapter mAdapter;

    protected List mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadGoodList(true, false);
    }

//    @Override
//    public void initData() {
//        super.initData();
//        mList = new ArrayList();
//        WGHomeFloorContentGoodItem item = new WGHomeFloorContentGoodItem();
//        item.pictureURL = "";
//        item.name = "郑少要";
//        item.chineseName = "sss";
//        item.briefDescription = "asdfasdfasdfas";
//        item.price = "12d";
//        item.currentPrice = "fss";
//        item.reducePrice = "fs2323";
//        mList.add(item);
//
//        WGHomeFloorContentGoodItem item2 = new WGHomeFloorContentGoodItem();
//        item2.pictureURL = "";
//        item2.name = "郑少要";
//        item2.chineseName = "sss";
//        item2.briefDescription = "asdfasdfasdfas";
//        item2.price = "12d";
//        item2.currentPrice = "fss";
//        item2.reducePrice = "fs2323";
//        mList.add(item);
//        mList.add(item);
//        mList.add(item);
//        mList.add(item);
//        mList.add(item);
//        mList.add(item);
//        mList.add(item);mList.add(item);mList.add(item);mList.add(item);
//        mList.add(item);
//        mList.add(item);
//        mList.add(item);
//        mList.add(item);
//        mList.add(item);
//        mList.add(item);mList.add(item);mList.add(item);
//        mList.add(item);
//        mList.add(item);mList.add(item);
//    }

    @Override
    public void initSubView() {
        super.initSubView();

        mNavigationBar.setTitle(R.string.Collection_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(this,
                JHDividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGGoodListAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new WGGoodListAdapter.WGGoodListOnItemClickListener() {
            @Override
            public void onTouchGoodItem(View view, WGHomeFloorContentGoodItem item) {
                handleTouchGoodItem(view, item);
            }
            @Override
            public void onTouchAddCart(View view, WGHomeFloorContentGoodItem item, Point point) {
                handleTouchAddCart(view, item, point);
            }
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongTouchGoodItem(View view, WGHomeFloorContentGoodItem item) {
                handleLongTouchGoodItem(view, item);
            }
        });

        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                loadGoodList(true, true);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadGoodList(false, true);
            }
        });
    }

    @Override
    public void handleRightBarItem() {
        super.handleRightBarItem();
    }

    void handleTouchGoodItem(View view, WGHomeFloorContentGoodItem item) {
        Intent intent = new Intent(this, WGGoodDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void handleLongTouchGoodItem(View view, WGHomeFloorContentGoodItem item) {
    }

    void handleTouchAddCart(View view, WGHomeFloorContentGoodItem item, Point point) {
        handlePurchase(item, view, point);
    }

    public void refreshUI() {
        mAdapter.setData(mList);
    }

    void loadGoodList(boolean refresh, boolean pulling) {

    }

    void loadDeleteGood(WGHomeFloorContentGoodItem item) {

    }

    void handlePurchase(WGHomeFloorContentGoodItem item, View view, Point fromPoint) {
        if (JHStringUtils.isNullOrEmpty(WGApplicationUserUtils.getInstance().currentPostCode())) {
            WGPostPopView popupView = (WGPostPopView) getLayoutInflater().inflate(R.layout.common_cap_pop, null);
            popupView.setListener(new WGPostPopView.OnItemListener() {
                @Override
                public void onSuccess() {

                }
            });
            JHBasePopupWindow window = new JHBasePopupWindow(popupView,
                    JHAdaptScreenUtils.devicePixelWidth(this),
                    JHAdaptScreenUtils.devicePixelHeight(this));
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
//        if (mData != null && mData.carouselFigures != null && mData.carouselFigures.size() > 0) {
//            //动画
//            int[] distance = {0,0};
//            WGApplicationAnimationUtils.add(this, mLayout, view,
//                    item.pictureURL, R.drawable.common_add_cart, mNavigationBar.getShopCartView(), distance);
//        }
    }

    void handleShopCartCount(WGAddGoodToCartResponse response) {
        if (response.success()) {
            WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(response.data.goodCount);
        }
        else {
            showWarning(response.message);
        }
    }
}
