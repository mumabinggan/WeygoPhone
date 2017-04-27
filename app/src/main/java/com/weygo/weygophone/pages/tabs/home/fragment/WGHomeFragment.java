package com.weygo.weygophone.pages.tabs.home.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.viewpagerindicator.TabPageIndicator;
import com.weygo.common.base.JHFragment;
import com.weygo.common.tools.JHFontUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.slider.WGSliderActivity;
import com.weygo.weygophone.pages.tabs.home.adapter.WGHomeSegmentPagerAdapter;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeNavigationBar;

/**
 * Created by muma on 2016/12/19.
 */

public class WGHomeFragment extends JHFragment {

    WGHomeNavigationBar mNavigationBar;

    ScrollIndicatorView mHomeSegmentView;

    ViewPager mViewPager;

    WGHomeSegmentPagerAdapter mPagerAdapter;

    private IndicatorViewPager mIndicatorViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);

        mNavigationBar = (WGHomeNavigationBar) view.findViewById(R.id.home_navigationBar);
        mNavigationBar.setOnClickListener(new WGHomeNavigationBar.OnClickHomeNavigationBarListener() {
            @Override
            public void onClickBriefIntro(View var1) {
                Intent intent = new Intent(getActivity(), WGSliderActivity.class);
                startActivity(intent);
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
        mIndicatorViewPager.setAdapter(new MyAdapter());
        return view;
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {
        private String[] versions = {"Cupcake", "Donut", "Éclair", "Froyo"};
        private String[] names = {"纸杯蛋糕", "甜甜圈", "闪电泡芙", "冻酸奶"};

        @Override
        public int getCount() {
            return versions.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                convertView = inflater.inflate(R.layout.common_segment_title, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(versions[position]);
            //因为wrap的布局 字体大小变化会导致textView大小变化产生抖动，这里通过设置textView宽度就避免抖动现象
            //1.3f是根据上面字体大小变化的倍数1.3f设置
            textView.setWidth(320 / getCount() * R.dimen.x1);

            return convertView;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = new TextView(container.getContext());
            }
            TextView textView = (TextView) convertView;
            textView.setText(names[position]);
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

        public void loadTitle() {

        }

    }
}
