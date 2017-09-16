package com.weygo.weygophone.pages.specialClassifyGood;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.JHWarningUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.classifyDetail.fragment.WGClassifyDetailContentFragment;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyDetail;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.foreign.fragment.WGTabAsiaFragment;
import com.weygo.weygophone.pages.tabs.home.fragment.WGTabHomeItemFragment;
import com.weygo.weygophone.pages.tabs.home.model.WGHome;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.request.WGHomeTabContentRequest;
import com.weygo.weygophone.pages.tabs.home.model.response.WGHomeTabContentResponse;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;

/**
 * Created by muma on 2017/9/10.
 */

public class WGSpecialClassifyGoodActivity extends WGBaseActivity {
    //navigationbar
    WGTabNavigationBar mNavigationBar;

    WGHome mData;

    WGClassifyItem mItem;

    WGTabHomeItemFragment mFragment;

    ViewGroup mLayout;

    public interface OnRequestCompletion {
        void onHomeContentCompletion(WGHomeTabContentResponse response);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgspecialclassifygood_activity);
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
                Intent intent = new Intent(WGSpecialClassifyGoodActivity.this, WGSearchActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickCart(View var1) {
                Intent intent = new Intent(WGSpecialClassifyGoodActivity.this, WGShopCartListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickTitle(View var1) {

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
//                mData = data;
//                if (mData != null && !JHStringUtils.isNullOrEmpty(mData.name)) {
//                    mNavigationBar.setTitle(mData.name);
//                }
            }

            @Override
            public void onAddShopCart(WGHomeFloorContentGoodItem item, Point fromPoint) {
                handleAddShopCartAnimation(item, fromPoint);
            }
        });
        fragmentTransaction.add(R.id.container, fragment);
        fragmentTransaction.commit();
        if (mItem != null) {
            fragment.setClassifyId(mItem.id);
        }
        mLayout = (ViewGroup) findViewById(R.id.container);
    }

    void handleAddShopCartAnimation(WGHomeFloorContentGoodItem item, Point fromPoint) {
        int[] distance = {0,0};
        int[] endPoint = mNavigationBar.getShopCartViewPoint();
        WGApplicationAnimationUtils.add(this, mLayout, fromPoint,
                item.pictureURL, R.drawable.common_add_cart, endPoint, distance);
    }

//    //Request
//    public void loadTabContentDataWithMenuId(long menuId, int cacheType) {
//        loadTabContentDataWithMenuId(menuId, cacheType,
//                new WGTabAsiaFragment.OnRequestCompletion() {
//                    @Override
//                    public void onHomeContentCompletion(WGHomeTabContentResponse response) {
//                        handleTabContentDataSuccess(response);
//                    }
//                });
//    }
//
//    public void loadTabContentDataWithMenuId(final long menuId, int cacheType,
//                                             WGTabAsiaFragment.OnRequestCompletion completion) {
//        if (cacheType != WGConstants.WGCacheTypeNetwork) {
//            WGHomeTabContentResponse cacheResponse = null;
//            if (cacheResponse != null) {
//                if (completion != null) {
//                    completion.onHomeContentCompletion(cacheResponse);
//                    if (cacheType == WGConstants.WGCacheTypeCachePrior) {
//                        return;
//                    }
//                }
//            }
//        }
//        final WGHomeTabContentRequest request = new WGHomeTabContentRequest();
//        request.menuId = menuId;
//        if (mData != null) {
//            request.showsLoadingView = false;
//        }
//        postAsyn(request, WGHomeTabContentResponse.class, new JHResponseCallBack() {
//            @Override
//            public void onSuccess(JHResponse response) {
//                handleTabContentDataSuccess((WGHomeTabContentResponse) response);
//            }
//
//            @Override
//            public void onFailure(JHRequestError error) {
//                JHWarningUtils.showToast(getContext(), R.string.Request_Fail_Tip);
//            }
//        });
//
//    }
//
//    void handleTabContentDataSuccess(WGHomeTabContentResponse response) {
//        mFragment.stopRefreshing();
//        if (response != null && response.success()) {
//            mData = response.data;
//            WGHomeTabContentResponse cacheResponse = response;
//            JHLocalSettingUtils.setStringLocalSetting(getContext(),
//                    WGConstants.WGLocalSettingAsiaCache, JSON.toJSONString(cacheResponse));
//            if (mFragment != null) {
//                mFragment.refresh(response.data);
//            }
//        }
//        else {
//            JHWarningUtils.showToast(getContext(), response.message);
//        }
//    }
}
