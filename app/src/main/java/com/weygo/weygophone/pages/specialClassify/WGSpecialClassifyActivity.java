package com.weygo.weygophone.pages.specialClassify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.common.tools.JHWarningUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.classifyDetail.fragment.WGClassifyDetailContentFragment;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyDetail;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.fragment.WGTabHomeItemFragment;
import com.weygo.weygophone.pages.tabs.home.model.WGHome;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.request.WGHomeTabContentRequest;
import com.weygo.weygophone.pages.tabs.home.model.response.WGHomeTabContentResponse;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;

/**
 * Created by muma on 2017/9/10.
 */

public class WGSpecialClassifyActivity extends WGBaseActivity {
    //navigationbar
    WGTabNavigationBar mNavigationBar;

    WGHome mData;

    WGClassifyItem mItem;

    WGTabHomeItemFragment mFragment;

    public interface OnRequestCompletion {
        void onHomeContentCompletion(WGHomeTabContentResponse response);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgspecialclassify_activity);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mItem = (WGClassifyItem)bundle.getSerializable(WGConstants.WGIntentDataKey);
            loadTabContentDataWithMenuId(mItem.id, WGConstants.WGCacheTypeBoth);
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar = (WGTabNavigationBar) findViewById(R.id.asia_navigationBar);
        mNavigationBar.setTitle(mItem.name);
        mNavigationBar.setLeftImage(R.drawable.common_return);
        mNavigationBar.setOnClickListener(new WGTabNavigationBar.OnClickHomeNavigationBarListener() {
            @Override
            public void onClickBriefIntro(View var1) {
//                Intent intent = new Intent(getActivity(), WGClientServiceActivity.class);
//                startActivity(intent);
                //startActivity(new Intent(getActivity(), ZopimChatActivity.class));
                finish();
            }

            @Override
            public void onClickSearch(View var1) {
                Intent intent = new Intent(WGSpecialClassifyActivity.this, WGSearchActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickCart(View var1) {
                Intent intent = new Intent(WGSpecialClassifyActivity.this, WGShopCartListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickTitle(View var1) {

            }
        });
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        mFragment = new WGTabHomeItemFragment();
        mFragment.setListener(new WGTabHomeItemFragment.OnListener() {
            @Override
            public void onRefresh() {
                loadTabContentDataWithMenuId(mItem.id, WGConstants.WGCacheTypeCachePrior);
            }

            @Override
            public void onSwitchTab(WGHomeFloorContentClassifyItem item) {

            }
        });
        transaction.add(R.id.container, mFragment);

        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }

    //Request
    public void loadTabContentDataWithMenuId(long menuId, int cacheType) {
        loadTabContentDataWithMenuId(menuId, cacheType,
                new OnRequestCompletion() {
                    @Override
                    public void onHomeContentCompletion(WGHomeTabContentResponse response) {
                        handleTabContentDataSuccess(response);
                    }
                });
    }

    public void loadTabContentDataWithMenuId(final long menuId, int cacheType,
                                             OnRequestCompletion completion) {
        if (cacheType != WGConstants.WGCacheTypeNetwork) {
            WGHomeTabContentResponse cacheResponse = null;
            if (cacheResponse != null) {
                if (completion != null) {
                    completion.onHomeContentCompletion(cacheResponse);
                    if (cacheType == WGConstants.WGCacheTypeCachePrior) {
                        return;
                    }
                }
            }
        }
        final WGHomeTabContentRequest request = new WGHomeTabContentRequest();
        request.menuId = menuId;
        if (mData != null) {
            request.showsLoadingView = false;
        }
        postAsyn(request, WGHomeTabContentResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleTabContentDataSuccess((WGHomeTabContentResponse) response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                JHWarningUtils.showToast(WGSpecialClassifyActivity.this, R.string.Request_Fail_Tip);
            }
        });

    }

    void handleTabContentDataSuccess(WGHomeTabContentResponse response) {
        mFragment.stopRefreshing();
        if (response != null && response.success()) {
            mData = response.data;
            WGHomeTabContentResponse cacheResponse = response;
            JHLocalSettingUtils.setStringLocalSetting(this,
                    WGConstants.WGLocalSettingAsiaCache, JSON.toJSONString(cacheResponse));
            if (mFragment != null) {
                mFragment.refresh(response.data);
            }
        }
        else {
            JHWarningUtils.showToast(this, response.message);
        }
    }
}
