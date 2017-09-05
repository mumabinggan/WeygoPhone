package com.weygo.weygophone.pages.tabs.foreign.fragment;

import android.content.Intent;
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
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.tabs.home.fragment.WGTabHomeItemFragment;
import com.weygo.weygophone.pages.tabs.home.model.WGHome;
import com.weygo.weygophone.pages.tabs.home.model.request.WGHomeTabContentRequest;
import com.weygo.weygophone.pages.tabs.home.model.response.WGHomeTabContentResponse;
import com.weygo.weygophone.pages.tabs.home.model.response.WGHomeTitlesResponse;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;

/**
 * Created by muma on 2017/7/25.
 */

public class WGTabAsiaFragment extends WGFragment {

    //navigationbar
    WGTabNavigationBar mNavigationBar;

    WGHome mData;

    WGTabHomeItemFragment mFragment;

    public interface OnRequestCompletion {
        void onHomeContentCompletion(WGHomeTabContentResponse response);
    }

    @Override
    public int fragmentResId() {
        return R.layout.tab_asia_fragment;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initSubView(View view) {
        super.initSubView(view);
        final WGMainActivity activity = (WGMainActivity) getActivity();
        mNavigationBar = (WGTabNavigationBar) view.findViewById(R.id.asia_navigationBar);
        mNavigationBar.setTitle(R.string.TabAsia);
        mNavigationBar.setOnClickListener(new WGTabNavigationBar.OnClickHomeNavigationBarListener() {
            @Override
            public void onClickBriefIntro(View var1) {
//                Intent intent = new Intent(getActivity(), WGClientServiceActivity.class);
//                startActivity(intent);
                //startActivity(new Intent(getActivity(), ZopimChatActivity.class));
                activity.openSlider();
            }

            @Override
            public void onClickSearch(View var1) {
                Intent intent = new Intent(getActivity(), WGSearchActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickCart(View var1) {
                Intent intent = new Intent(getActivity(), WGShopCartListActivity.class);
                startActivity(intent);
            }

            @Override
            public void onClickTitle(View var1) {

            }
        });
        ///步骤一：添加一个FragmentTransaction的实例
        FragmentManager fragmentManager =getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        //步骤二：用add()方法加上Fragment的对象rightFragment
        mFragment = new WGTabHomeItemFragment();
        transaction.add(R.id.linearLayout, mFragment);

        //步骤三：调用commit()方法使得FragmentTransaction实例的改变生效
        transaction.commit();
    }

    @Override
    public void loadRequest() {
        super.loadRequest();
        loadTabContentDataWithMenuId(193, WGConstants.WGCacheTypeBoth);
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
                JHWarningUtils.showToast(getContext(), R.string.Request_Fail_Tip);
            }
        });

    }

    void handleTabContentDataSuccess(WGHomeTabContentResponse response) {
        mFragment.stopRefreshing();
        if (response != null && response.success()) {
            mData = response.data;
            WGHomeTabContentResponse cacheResponse = response;
            JHLocalSettingUtils.setStringLocalSetting(getContext(),
                    WGConstants.WGLocalSettingAsiaCache, JSON.toJSONString(cacheResponse));
            if (mFragment != null) {
                mFragment.refresh(response.data);
            }
        }
        else {
            JHWarningUtils.showToast(getContext(), response.message);
        }
    }
}
