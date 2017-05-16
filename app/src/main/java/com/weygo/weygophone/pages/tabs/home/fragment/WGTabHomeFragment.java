package com.weygo.weygophone.pages.tabs.home.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHFontUtils;
import com.weygo.common.tools.JHWarningUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeTitleItem;
import com.weygo.weygophone.pages.tabs.home.model.request.WGHomeTitlesRequest;
import com.weygo.weygophone.pages.tabs.home.model.response.WGHomeTitlesResponse;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2016/12/19.
 */

public class WGTabHomeFragment extends WGFragment {

    //navigationbar
    WGTabNavigationBar mNavigationBar;

    //Segment and page
    ScrollIndicatorView mHomeSegmentView;
    ViewPager mViewPager;
    MyAdapter mPagerAdapter;
    IndicatorViewPager mIndicatorViewPager;

    //data
    List<WGHomeTitleItem> mTitleList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_home_fragment,container,false);
        final WGMainActivity activity = (WGMainActivity) getActivity();
        mNavigationBar = (WGTabNavigationBar) view.findViewById(R.id.home_navigationBar);
        mNavigationBar.setOnClickListener(new WGTabNavigationBar.OnClickHomeNavigationBarListener() {
            @Override
            public void onClickBriefIntro(View var1) {
                /*
                Intent intent = new Intent(getActivity(), WGSliderActivity.class);
                startActivity(intent);
                */
                activity.testActivity();
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

        mHomeSegmentView = (ScrollIndicatorView) view.findViewById(R.id.home_segmentView);
        mViewPager = (ViewPager) view.findViewById(R.id.home_viewPager);

        float unSelectSize = JHFontUtils.font(getContext(), 12);
        float selectSize = unSelectSize;
        int selectColor = getResources().getColor(R.color.WGAppBaseColor);
        int unSelectColor = getResources().getColor(R.color.WGAppBaseTitleAColor);
        mHomeSegmentView.setScrollBar(new ColorBar(getActivity(), selectColor, 5));
        mHomeSegmentView.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));

        mIndicatorViewPager = new IndicatorViewPager(mHomeSegmentView, mViewPager);
        mPagerAdapter = new MyAdapter();
        List versions = new ArrayList();
        versions.add("Cupcake");
        versions.add("Donut");
        versions.add("Éclair");
        versions.add("Froyo");
        mPagerAdapter.versions = versions;

        List names = new ArrayList();
        names.add("Cupcake");
        names.add("Donut");
        names.add("Éclair");
        names.add("Froyo");
        mPagerAdapter.names = names;

        mIndicatorViewPager.setAdapter(mPagerAdapter);

        loadTitleData();

        return view;
    }

    //Request
    void loadTitleData() {
        final WGHomeTitlesRequest request = new WGHomeTitlesRequest();
        postAsyn(request, WGHomeTitlesResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleHomeTitlesSuccess((WGHomeTitlesResponse) response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                JHWarningUtils.showToast(getContext(), R.string.app_name);
            }
        });
    }

    void handleHomeTitlesSuccess(WGHomeTitlesResponse response) {
        if (response.success()) {
            mTitleList = response.data;
            mPagerAdapter.setTitleList(mTitleList);
        }
        else {
            JHWarningUtils.showToast(getContext(), response.message);
        }
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
        public List<String> versions;
        public List<String> names;

        private List<WGHomeTitleItem> mTitleList;
        public void setTitleList(List<WGHomeTitleItem> list) {
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
                WGHomeTitleItem item = mTitleList.get(position);
                textView.setText(item.name);
                textView.setWidth(320 / getCount() * R.dimen.x1);
            }
            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new TextView(container.getContext());
            }
            TextView textView = (TextView) convertView;
            textView.setText(names.get(position));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.GRAY);
            return convertView;
        }

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
