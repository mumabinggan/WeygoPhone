package com.weygo.weygophone.pages.bind;

import android.graphics.Color;
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
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHFontUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGApplicationPageUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGViewPager;
import com.weygo.weygophone.pages.bind.fragment.WGBindHadRegisterFragment;
import com.weygo.weygophone.pages.bind.fragment.WGBindUnRegisterFragment;
import com.weygo.weygophone.pages.bind.model.WGBindData;
import com.weygo.weygophone.pages.bind.model.request.WGBindRegisteredRequest;
import com.weygo.weygophone.pages.bind.model.request.WGBindUnRegisteredRequest;
import com.weygo.weygophone.pages.bind.model.response.WGBindRegisteredResponse;
import com.weygo.weygophone.pages.bind.model.response.WGBindUnRegisteredResponse;
import com.weygo.weygophone.pages.common.widget.WGSegmentView;
import com.weygo.weygophone.pages.tabs.home.fragment.WGTabHomeItemFragment;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by muma on 2017/10/10.
 */

public class WGBindActivity extends WGTitleActivity {

    WGBindData mData;

    //Segment and page
    WGSegmentView mHomeSegmentView;
    ViewPager mViewPager;
    MyAdapter mPagerAdapter;
    IndicatorViewPager mIndicatorViewPager;

    Map<Integer, WGFragment> mContentFragmentMap;

    @Override
    public void initContentView() {
        setContentView(R.layout.wgbind_activity);
    }

    @Override
    public void initData() {
        super.initData();
        mContentFragmentMap = new HashMap<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp instanceof WGBindData) {
                mData = (WGBindData) temp;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        if (mData != null) {
            if (mData.type == WGConstants.WGBindTypeFaceBook) {
                mNavigationBar.setTitle(R.string.Login_Facebook);
            }
            else {
                mNavigationBar.setTitle(R.string.Login_Wechat);
            }
        }

        mHomeSegmentView = (WGSegmentView) findViewById(R.id.home_segmentView);
        mViewPager = (ViewPager) findViewById(R.id.home_viewPager);

        mIndicatorViewPager = new IndicatorViewPager(mHomeSegmentView, mViewPager);
        mPagerAdapter = new MyAdapter(getSupportFragmentManager());

        mIndicatorViewPager.setAdapter(mPagerAdapter);
        mIndicatorViewPager.setOnIndicatorPageChangeListener(new IndicatorViewPager.OnIndicatorPageChangeListener() {
            @Override
            public void onIndicatorPageChange(int preItem, int currentItem) {
            }
        });
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

        private Integer[] mTitleList = {R.string.ThirdPartyBing_HadRegister,
                R.string.ThirdPartyBing_UnRegister};

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            if (mTitleList == null) {
                return 0;
            }
            return mTitleList.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                LayoutInflater inflate = LayoutInflater.from(getApplicationContext());
                convertView = inflate.inflate(R.layout.common_segment_title, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(mTitleList[position % mTitleList.length]);
            int padding = 10;
            textView.setPadding(padding, 0, padding, 0);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(final int position) {
            WGFragment fragment = mContentFragmentMap.get(position);
            if (fragment == null) {
                if (position == 0) {
                    WGBindHadRegisterFragment tempFragment = new WGBindHadRegisterFragment();
                    tempFragment.setListener(new WGBindHadRegisterFragment.OnListener() {
                        @Override
                        public void onBind(WGBindRegisteredRequest request) {
                            loadBindRegister(request);
                        }
                    });
                    fragment = tempFragment;
                }
                else {
                    WGBindUnRegisterFragment tempFragment = new WGBindUnRegisterFragment();
                    tempFragment.setListener(new WGBindUnRegisterFragment.OnListener() {
                        @Override
                        public void onBind(WGBindUnRegisteredRequest request) {
                            loadBindUnRegister(request);
                        }
                    });
                    fragment = tempFragment;
                }
                mContentFragmentMap.put(position, fragment);
            }
            return fragment;
        }

//        @Override
//        public View getViewForPage(int position, View convertView, ViewGroup container) {
//            Log.e("=getViewForPage=", position + "");
//            if (convertView == null) {
//                convertView = new TextView(container.getContext());
//            }
//            TextView textView = (TextView) convertView;
//            textView.setText("hello");
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

    void loadBindUnRegister(WGBindUnRegisteredRequest request) {
        request.type = mData.type;
        request.uniqueId = mData.uniqueId;
        this.postAsyn(request, WGBindUnRegisteredResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleBindResponse((WGBindUnRegisteredResponse )response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleBindResponse(WGBindUnRegisteredResponse response) {
        if (response.success()) {
            WGApplicationUserUtils.getInstance().reset();
            WGApplicationUserUtils.getInstance().setUser(response.data);
            sendRefreshNotification(WGConstants.WGRefreshNotificationTypeLogin);
            sendNotification(WGConstants.WGNotificationTypeLogin);
            WGApplicationPageUtils.getInstance().switchTab(WGConstants.WGTabHome);
        }
        else {
            showWarning(response.message);
        }
    }

    void loadBindRegister(WGBindRegisteredRequest request) {
        request.type = mData.type;
        request.uniqueId = mData.uniqueId;
        this.postAsyn(request, WGBindRegisteredResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleBindRegisteredResponse((WGBindRegisteredResponse )response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleBindRegisteredResponse(WGBindRegisteredResponse response) {
        if (response.success()) {
            WGApplicationUserUtils.getInstance().reset();
            WGApplicationUserUtils.getInstance().setUser(response.data);
            sendRefreshNotification(WGConstants.WGRefreshNotificationTypeLogin);
            sendNotification(WGConstants.WGNotificationTypeLogin);
            WGApplicationPageUtils.getInstance().switchTab(WGConstants.WGTabHome);
        }
        else {
            showWarning(response.message);
        }
    }
}
