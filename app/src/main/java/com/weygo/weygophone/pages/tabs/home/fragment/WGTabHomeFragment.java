package com.weygo.weygophone.pages.tabs.home.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.common.tools.JHWarningUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.classifyDetail.WGClassifyDetailActivity;
import com.weygo.weygophone.pages.clientCenter.list.WGClientServiceActivity;
import com.weygo.weygophone.pages.common.widget.WGSegmentView;
import com.weygo.weygophone.pages.pay.paySuccess.WGPaySuccessActivity;
import com.weygo.weygophone.pages.pay.payWeb.WGPayWebActivity;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.specialClassifyGood.WGSpecialClassifyGoodActivity;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTitleItem;
import com.weygo.weygophone.pages.tabs.home.model.request.WGHomeTabContentRequest;
import com.weygo.weygophone.pages.tabs.home.model.request.WGHomeTitlesRequest;
import com.weygo.weygophone.pages.tabs.home.model.response.WGHomeTabContentResponse;
import com.weygo.weygophone.pages.tabs.home.model.response.WGHomeTitlesResponse;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muma on 2016/12/19.
 */

public class WGTabHomeFragment extends WGFragment {

    //navigationbar
    WGTabNavigationBar mNavigationBar;

    //Segment and page
    WGSegmentView mHomeSegmentView;
    ViewPager mViewPager;
    MyAdapter mPagerAdapter;
    IndicatorViewPager mIndicatorViewPager;

    Map<Integer, WGTabHomeItemFragment> mContentFragmentMap;

    WGHomeTitlesResponse mDataResponse;

    ViewGroup mLayout;

    //Cache
    boolean mHadReadTitlesCache;
    Map<Long, Boolean> mHadReadContentCacheMap;

    public interface OnRequestCompletion {
        void onHomeTitlesCompletion(WGHomeTitlesResponse response);
        void onHomeContentCompletion(WGHomeTabContentResponse response, long menuId);
    }

    @Override
    public int fragmentResId() {
        return R.layout.tab_home_fragment;
    }

    @Override
    public void initData() {
        super.initData();
        mContentFragmentMap = new HashMap<>();
        mHadReadContentCacheMap = new HashMap<>();
    }

    @Override
    public void initSubView(View view) {
        super.initSubView(view);
        mLayout = (ViewGroup) view.findViewById(R.id.layout);
        final WGMainActivity activity = (WGMainActivity) getActivity();
        mNavigationBar = (WGTabNavigationBar) view.findViewById(R.id.home_navigationBar);
        mNavigationBar.setOnClickListener(new WGTabNavigationBar.OnClickHomeNavigationBarListener() {
            @Override
            public void onClickBriefIntro(View var1) {
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
            }
        });

        mHomeSegmentView = (WGSegmentView) view.findViewById(R.id.home_segmentView);
        mViewPager = (ViewPager) view.findViewById(R.id.home_viewPager);

        mIndicatorViewPager = new IndicatorViewPager(mHomeSegmentView, mViewPager);
        mPagerAdapter = new MyAdapter(getFragmentManager());

        mIndicatorViewPager.setAdapter(mPagerAdapter);
        mIndicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
                loadTabContentDataWithMenuId(menuIdWithIndex(currentItem), WGConstants.WGCacheTypeCachePrior);
            }
        });
    }

    long menuIdWithIndex(int index) {
        WGTitleItem item = dataWithIndex(index);
        if (item != null) {
            return item.id;
        }
        return -1;
    }

    WGTitleItem dataWithMenuId(long menuId) {
        List<WGTitleItem> list = mDataResponse.data;
        for (int num = 0; num < list.size(); ++num) {
            WGTitleItem item = list.get(num);
            if (item.id == menuId) {
                return item;
            }
        }
        return null;
    }

    int indexWithMenuId(long menuId) {
        List<WGTitleItem> list = mDataResponse.data;
        for (int num = 0; num < list.size(); ++num) {
            WGTitleItem item = list.get(num);
            if (item.id == menuId) {
                return num;
            }
        }
        return 0;
    }

    WGTitleItem dataWithIndex(int index) {
        if (mDataResponse.data.size() <= index) {
            index = 0;
        }
        WGTitleItem item = mDataResponse.data.get(index);
        return item;
    }

    @Override
    public void loadRequest() {
        super.loadRequest();
        loadTitleData();
    }

    //Request
    void loadTitleData() {
        loadTitleDataOnCompletion(new OnRequestCompletion() {
            @Override
            public void onHomeTitlesCompletion(WGHomeTitlesResponse response) {
                handleHomeTitlesSuccess(response);
            }

            @Override
            public void onHomeContentCompletion(WGHomeTabContentResponse response, long menuId) {

            }
        });
    }

    void loadTitleDataOnCompletion(OnRequestCompletion completion) {
        if (!mHadReadTitlesCache) {
            String jsonString = JHLocalSettingUtils.getStringLocalSetting(getContext(),
                    WGConstants.WGLocalSettingHomeCache);
            mDataResponse = JSON.parseObject(jsonString, WGHomeTitlesResponse.class);
            if (mDataResponse != null) {
                if (completion != null) {
                    completion.onHomeTitlesCompletion(mDataResponse);
                }
            }
            mHadReadTitlesCache = true;
        }

        final WGHomeTitlesRequest request = new WGHomeTitlesRequest();
        request.showsLoadingView = mDataResponse != null ? false : true;
//        request.showsLoadingView = false;
        postAsyn(request, WGHomeTitlesResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleHomeTitlesSuccess((WGHomeTitlesResponse) response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                JHWarningUtils.showToast(getContext(), R.string.Request_Fail_Tip);
            }
        });

    }

    void handleHomeTitlesSuccess(WGHomeTitlesResponse response) {
        if (response != null && response.success()) {
            if (mDataResponse == null) {
                mDataResponse = response;
            }
            else {
                mDataResponse.setTitles(response);
            }
            JHLocalSettingUtils.setStringLocalSetting(getContext(),
                    WGConstants.WGLocalSettingHomeCache, JSON.toJSONString(mDataResponse));
            //mTitleList = response.data;
            mPagerAdapter.setTitleList(response.data);
            int index = mHomeSegmentView.getCurrentItem();
            long menuId = menuIdWithIndex(index);
            if (menuId >= 0) {
                loadTabContentDataWithMenuId(menuId, WGConstants.WGCacheTypeBoth);
            }
        }
        else {
            JHWarningUtils.showToast(getContext(), response.message);
        }
    }

    //Request
    public void loadTabContentDataWithMenuId(long menuId, int cacheType) {
        loadTabContentDataWithMenuId(menuId, cacheType,
                new OnRequestCompletion() {
                    @Override
                    public void onHomeTitlesCompletion(WGHomeTitlesResponse response) {

                    }

                    @Override
                    public void onHomeContentCompletion(WGHomeTabContentResponse response, long menuId) {
                        handleTabContentDataSuccess(response, menuId);
                    }
                });
    }

    public void loadTabContentDataWithMenuId(final long menuId, int cacheType,
                                             OnRequestCompletion completion) {
        if (cacheType != WGConstants.WGCacheTypeNetwork) {
            WGTitleItem item = dataWithMenuId(menuId);
            Boolean readBool = mHadReadContentCacheMap.get(new Long(menuId));
            if (item != null) {
                if (readBool != null && readBool.booleanValue()) {
                    if (cacheType == WGConstants.WGCacheTypeCachePrior) {
                        if (item.data != null) {
                            WGHomeTabContentResponse response = new WGHomeTabContentResponse();
                            response.data = item.data;
                            response.code = 1;
                            if (completion != null) {
                                completion.onHomeContentCompletion(response, menuId);
                            }
                            return;
                        }
                    }
                    else {
                        if (item.data != null) {
                            WGHomeTabContentResponse response = new WGHomeTabContentResponse();
                            response.data = item.data;
                            response.code = 1;
                            if (completion != null) {
                                completion.onHomeContentCompletion(response, menuId);
                            }
                        }
                    }
                }
                else {
                    if (item.data != null) {
                        WGHomeTabContentResponse response = new WGHomeTabContentResponse();
                        response.data = item.data;
                        response.code = 1;
                        if (completion != null) {
                            completion.onHomeContentCompletion(response, menuId);
                        }
                    }
                    mHadReadContentCacheMap.put(new Long(menuId), new Boolean(true));
                }
            }
        }
        final WGHomeTabContentRequest request = new WGHomeTabContentRequest();
        request.menuId = menuId;
        WGTitleItem item = dataWithMenuId(menuId);
        if (item.data != null) {
            request.showsLoadingView = false;
        }
//        request.showsLoadingView = false;
        postAsyn(request, WGHomeTabContentResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleTabContentDataSuccess((WGHomeTabContentResponse) response, menuId);
            }

            @Override
            public void onFailure(JHRequestError error) {
                JHWarningUtils.showToast(getContext(), R.string.Request_Fail_Tip);
            }
        });

    }

    void handleTabContentDataSuccess(WGHomeTabContentResponse response, long menuId) {
        int index = indexWithMenuId(menuId);
        WGTabHomeItemFragment fragment = mContentFragmentMap.get(index);
        stopRefreshing(index);
        if (response != null && response.success()) {
            if (mDataResponse.data.size() > index) {
                WGTitleItem item = mDataResponse.data.get(index);
                item.data = response.data;
                JHLocalSettingUtils.setStringLocalSetting(getContext(),
                        WGConstants.WGLocalSettingHomeCache, JSON.toJSONString(mDataResponse));
            }
            if (fragment != null) {
                fragment.refresh(response.data);
            }
        }
        else {
            JHWarningUtils.showToast(getContext(), response.message);
        }
    }

    void stopRefreshing(int index) {
        WGTabHomeItemFragment fragment = mContentFragmentMap.get(index);
        if (fragment != null) {
            fragment.stopRefreshing();
        }
    }

    void handleSwitchTab(WGHomeFloorContentClassifyItem item) {
        if (item.jumpType == WGConstants.WGAppJumpTypeSpecailClassifyHomeTab) {
            //handleSwitchHomeTab(item);
        }
        else if (item.jumpType == WGConstants.WGAppJumpTypeSpecailClassifyGoodBenefitTab) {

        }
        else if (item.jumpType == WGConstants.WGAppJumpTypeSpecailClassifyGoodNoTab) {

        }
        else if (item.jumpType == WGConstants.WGAppJumpTypeSpecailClassifyNoTab) {

        }
    }

    public void handleSwitchHomeItemTab(WGHomeFloorContentClassifyItem item) {
        if (mDataResponse != null && mDataResponse.data != null) {
            for (int num = 0; num < mDataResponse.data.size(); ++num) {
                WGTitleItem item1 = mDataResponse.data.get(num);
                if (item.id == item1.id) {
                    mIndicatorViewPager.setCurrentItem(num, true);
                    break;
                }
            }
        }
    }

    public class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private List<WGTitleItem> mTitleList;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void setTitleList(List<WGTitleItem> list) {
            mTitleList = list;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            if (mTitleList == null) {
                return 0;
            }
            return mTitleList.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                convertView = inflater.inflate(R.layout.common_segment_title, container, false);
            }
            TextView textView = (TextView) convertView;
            if (mTitleList != null && mTitleList.size() > position) {
                WGTitleItem item = mTitleList.get(position);
                textView.setText(item.name);
                textView.setWidth(320 / getCount() * R.dimen.x1);
            }
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(final int position) {
            Log.e("-getFragmentForPage--", "" + position);
            WGTabHomeItemFragment fragment = mContentFragmentMap.get(position);
            if (fragment == null) {
                Log.e("fragment=null", "frag");
                fragment = new WGTabHomeItemFragment();
                fragment.setListener(new WGTabHomeItemFragment.OnListener() {
                    @Override
                    public void onRefresh() {
                        loadTabContentDataWithMenuId(menuIdWithIndex(position), WGConstants.WGCacheTypeCachePrior);
                    }

                    @Override
                    public void onSwitchTab(WGHomeFloorContentClassifyItem item) {
                        handleSwitchTab(item);
                    }

                    @Override
                    public void onAddShopCart(WGHomeFloorContentGoodItem item, Point fromPoint) {
                        handleAddShopCartAnimation(item, fromPoint);
                    }
                });
                mContentFragmentMap.put(position, fragment);
            }
            return fragment;
        }

        void handleAddShopCartAnimation(WGHomeFloorContentGoodItem item, Point fromPoint) {
            int[] distance = {0,0};
            int[] endPoint = mNavigationBar.getShopCartViewPoint();
            WGApplicationAnimationUtils.add(getContext(), mLayout, fromPoint,
                    item.pictureURL, R.drawable.common_add_cart, endPoint, distance);
        }

//        @Override
//        public View getViewForPage(int position, View convertView, ViewGroup container) {
//            Log.e("=getViewForPage=", position + "");
//            if (convertView == null) {
//                convertView = new TextView(container.getContext());
//            }
//            TextView textView = (TextView) convertView;
//            textView.setText(names.get(position));
//            textView.setGravity(Gravity.CENTER);
//            textView.setTextColor(Color.GRAY);
//            return convertView;
//        }

        @Override
        public int getItemPosition(Object object) {
            //这是ViewPager适配器的特点,有两个值 POSITION_NONE，POSITION_UNCHANGED，默认就是POSITION_UNCHANGED,
            // 表示数据没变化不用更新.notifyDataChange的时候重新调用getViewForPage
            return PagerAdapter.POSITION_UNCHANGED;
        }

        private int getTextWidth(TextView textView) {
            if (textView == null) {
                return 0;
            }
            Rect bounds = new Rect();
            String text = textView.getText().toString();
            Paint paint = textView.getPaint();
            paint.getTextBounds(text, 0, text.length(), bounds);
            int width = bounds.left + bounds.width();
            return width;
        }
    }
}
