package com.weygo.weygophone.pages.classifyDetail;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.classifyDetail.fragment.WGClassifyDetailContentFragment;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyDetail;
import com.weygo.weygophone.pages.goodDetail.adapter.WGGoodDetailAdapter;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;
import com.weygo.weygophone.pages.goodDetail.widget.WGGoodDetailOperateView;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/8/2.
 */

public class WGClassifyDetailActivity extends WGBaseActivity {

    WGTabNavigationBar mNavigationBar;

    public long classifyId;

    public WGClassifyDetail mData;

    @Override
    public void initContentView() {
        setContentView(R.layout.classifydetail_activity);
    }

    @Override
    public void initData() {
        super.initData();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            classifyId = (long)bundle.getSerializable(WGConstants.WGIntentDataKey);
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar = (WGTabNavigationBar) findViewById(R.id.classify_navigationBar);
        mNavigationBar.setLeftImage(R.drawable.common_return);
        mNavigationBar.setOnClickListener(new WGTabNavigationBar.OnClickHomeNavigationBarListener() {
            @Override
            public void onClickBriefIntro(View var1) {
                finish();
            }

            @Override
            public void onClickSearch(View var1) {

            }

            @Override
            public void onClickCart(View var1) {

            }

            @Override
            public void onClickTitle(View var1) {

            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        WGClassifyDetailContentFragment fragment = new WGClassifyDetailContentFragment();
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
        fragment.setClassifyId(classifyId);
    }

}
