package com.weygo.weygophone.pages.classifyDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.classifyDetail.fragment.WGClassifyDetailContentFragment;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyDetail;
import com.weygo.weygophone.pages.classifyDetail.widget.WGClassifyPopView;
import com.weygo.weygophone.pages.goodDetail.adapter.WGGoodDetailAdapter;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;
import com.weygo.weygophone.pages.goodDetail.widget.WGGoodDetailOperateView;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/8/2.
 */

public class WGClassifyDetailActivity extends WGBaseActivity {

    WGTabNavigationBar mNavigationBar;

    WGClassifyItem mItem;

    public WGClassifyDetail mData;

    WGClassifyPopView mPopupView;

    @Override
    public void initContentView() {
        setContentView(R.layout.classifydetail_activity);
    }

    @Override
    public void initData() {
        super.initData();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mItem = (WGClassifyItem)bundle.getSerializable(WGConstants.WGIntentDataKey);
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar = (WGTabNavigationBar) findViewById(R.id.classify_navigationBar);
        mNavigationBar.setLeftImage(R.drawable.common_return);
        mNavigationBar.setTitle(mItem.name);
        mNavigationBar.setOnClickListener(new WGTabNavigationBar.OnClickHomeNavigationBarListener() {
            @Override
            public void onClickBriefIntro(View var1) {
                finish();
            }

            @Override
            public void onClickSearch(View var1) {
                Intent intent = new Intent(WGClassifyDetailActivity.this, WGSearchActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickCart(View var1) {
                Intent intent = new Intent(WGClassifyDetailActivity.this, WGShopCartListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickTitle(View var1) {
                handleTitle();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        WGClassifyDetailContentFragment fragment = new WGClassifyDetailContentFragment();
        fragment.setListener(new WGClassifyDetailContentFragment.RefreshListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onRequestCompletion(WGClassifyDetail data) {
                mData = data;
                if (mData != null && !JHStringUtils.isNullOrEmpty(mData.name)) {
                    mNavigationBar.setTitle(mData.name);
                }
            }
        });
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
        if (mItem != null) {
            fragment.setClassifyId(mItem.id);
        }
    }

    void handleTitle() {
        if (mData != null && mData.subArray != null
                && mData.subArray.size() > 0) {
            mPopupView = (WGClassifyPopView) getLayoutInflater().inflate(R.layout.common_subclassify_pop, null);
            mPopupView.setData(mData.subArray);
            mPopupView.setListener(new WGClassifyPopView.OnItemListener() {
                @Override
                public void onItemClick(WGClassifyItem item) {
                    handleClassifyItem(item);
                }
            });
            JHBasePopupWindow window = new JHBasePopupWindow(mPopupView,
                    JHAdaptScreenUtils.devicePixelWidth(this),
                    JHAdaptScreenUtils.devicePixelHeight(this));
            mPopupView.setPopupWindow(window);
            window.showAtLocation(mPopupView, Gravity.CENTER, 0, 0);
        }
    }

    void handleClassifyItem(WGClassifyItem item) {
        mPopupView.dismiss();
        Intent intent = new Intent(this, WGClassifyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
