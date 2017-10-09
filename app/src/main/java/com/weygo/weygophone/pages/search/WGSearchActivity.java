package com.weygo.weygophone.pages.search;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.SwipeItemLayout;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGCommonAlertView;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.goodDetail.model.response.WGAddGoodToCartResponse;
import com.weygo.weygophone.pages.login.WGLoginActivity;
import com.weygo.weygophone.pages.order.commit.WGCommitOrderActivity;
import com.weygo.weygophone.pages.search.fragment.WGSearchHotFragment;
import com.weygo.weygophone.pages.search.fragment.WGSearchResultFragment;
import com.weygo.weygophone.pages.search.model.request.WGHotSearchRequest;
import com.weygo.weygophone.pages.search.model.response.WGHotSearchResponse;
import com.weygo.weygophone.pages.search.widget.WGSearchNavigationView;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.shopcart.adapter.WGShopCartListAdater;
import com.weygo.weygophone.pages.shopcart.model.WGShopCart;
import com.weygo.weygophone.pages.shopcart.model.WGShopCartGoodItem;
import com.weygo.weygophone.pages.shopcart.model.request.WGCheckFailureProductsRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGCleanShopCartRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGDealFailureProductsRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGDealShopCartGiftRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGDeleteGoodFromShopCartRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGShopCartGiftRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGShopCartListRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGUpdateProductsRequest;
import com.weygo.weygophone.pages.shopcart.model.response.WGCheckFailureProductsResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGCleanShopCartResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGDealFailureProductsResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGDealShopCartGiftResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGDeleteGoodFromShopCartResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGShopCartGiftResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGShopCartListResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGUpdateProductsResponse;
import com.weygo.weygophone.pages.shopcart.widget.WGShopCartFailurePopView;
import com.weygo.weygophone.pages.shopcart.widget.WGShopCartGiftPopView;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/8/18.
 */

public class WGSearchActivity extends WGBaseActivity {

    WGSearchNavigationView mNavigationBar;
    WGSearchHotFragment mHotFragment;
    WGSearchResultFragment mResultFragment;

    boolean mIsGrid;

    String mSearchName;

    ViewGroup mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgsearch_activity);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp instanceof String) {
                mSearchName = (String)temp;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar = (WGSearchNavigationView) findViewById(R.id.titlebar);
        mNavigationBar.setInputText(mSearchName);
        mNavigationBar.setListener(new WGSearchNavigationView.OnItemListener() {
            @Override
            public void onEmpty(String code) {
                handleEmpty();
            }

            @Override
            public void onVista() {
                handleGrid();
            }

            @Override
            public void onLeft() {
                finish();
            }

            @Override
            public void onSearch(String searchName) {
                handleSearch(searchName);
            }

            @Override
            public void onShopCart() {
                Intent intent = new Intent(WGSearchActivity.this, WGShopCartListActivity.class);
                startActivity(intent);
            }
        });
        showFragment();
        mLayout = (ViewGroup) findViewById(R.id.mLayout);
    }

    void handleEmpty() {
        mSearchName = null;
        mNavigationBar.setInputText(mSearchName);
        mNavigationBar.setVistaResId(R.drawable.search_vista_normal);
        handleRightStatus(true);
        showFragment();
    }

    void showFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();  //Activity中
        if (JHStringUtils.isNullOrEmpty(mSearchName)) {
            mHotFragment = new WGSearchHotFragment();
            transaction.replace(R.id.content, mHotFragment);
            mNavigationBar.setVistaHidden(true);
        }
        else {
            mResultFragment = new WGSearchResultFragment();
            mResultFragment.setGrid(mIsGrid);
            mResultFragment.setSearchName(mSearchName);
            mResultFragment.setListener(new WGSearchResultFragment.OnListener() {
                @Override
                public void onPurchase(WGHomeFloorContentGoodItem item, View view, Point fromPoint) {
                    handlePurchase(item, view, fromPoint);
                }
            });
            transaction.replace(R.id.content, mResultFragment);
            mNavigationBar.setVistaHidden(false);
        }
        transaction.commit();
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
        int[] distance = {0,0};
        int[] endPoint = mNavigationBar.getShopCartViewPoint();
        WGApplicationAnimationUtils.add(this, mLayout, fromPoint,
                item.pictureURL, R.drawable.common_add_cart, endPoint, distance);
    }

    void handleShopCartCount(WGAddGoodToCartResponse response) {
        if (response.success()) {
            WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(response.data.goodCount);
        }
        else {
            showWarning(response.message);
        }
    }

    void handleGrid() {
        mIsGrid = !mIsGrid;
        mResultFragment.setGrid(mIsGrid);
        if (mIsGrid) {
            mNavigationBar.setVistaResId(R.drawable.search_vista_selected);
        }
        else {
            mNavigationBar.setVistaResId(R.drawable.search_vista_normal);
        }
    }

    void handleSearch(String searchName) {
        mSearchName = searchName;
        mNavigationBar.setInputText(mSearchName);
        handleRightStatus(false);
        if (!JHStringUtils.isNullOrEmpty(searchName)) {
            showFragment();
        }
    }

    void handleRightStatus(boolean hidden) {
        mNavigationBar.setVistaHidden(hidden);
        mNavigationBar.setShopCartHidden(hidden);
    }
}
