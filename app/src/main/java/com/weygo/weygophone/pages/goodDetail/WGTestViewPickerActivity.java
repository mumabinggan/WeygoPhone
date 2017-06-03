package com.weygo.weygophone.pages.goodDetail;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.pages.goodDetail.adapter.WGGoodDetailAdapter;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/6/3.
 */

public class WGTestViewPickerActivity extends WGBaseActivity {
    private View view1, view2, view3;
    private ViewPager viewPager;  //对应的viewPager

    private List<View> viewList;//view数组
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.tesss_activity);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initSubView() {
        super.initSubView();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        LayoutInflater inflater=getLayoutInflater();

        viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);

        final TestPagerAdapter pagerAdapter = new TestPagerAdapter();

        viewPager.setAdapter(pagerAdapter);

        viewPager.setCurrentItem(2);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.e("=onPageScrolled=", "" + position);
            }

            @Override
            public void onPageSelected(int position) {
                View view = pagerAdapter.getPrimaryItem();
                LinearLayout layout = (LinearLayout) view;
                TextView textView = (TextView) layout.findViewById(R.id.contentTV);
                Log.e("---text---", (String )textView.getText()+ ":" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("=onPageStateChanged=", "" + state);
            }
        });
    }

    class TestPagerAdapter extends PagerAdapter {
        private View mCurrentView;

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            // TODO Auto-generated method stub
            container.removeView(viewList.get(position));
            Log.e("==destroyItem==", position + "");
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(viewList.get(position));
            Log.e("==instantiateItem==", position + "");
            return viewList.get(position);
        }

        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentView = (View)object;
            Log.e("==setPrimaryItem==", position + "");
        }

        @Override
        public void startUpdate(ViewGroup container) {
            super.startUpdate(container);
            Log.e("==startUpdate==", "s");
        }

        @Override
        public void finishUpdate(ViewGroup container) {
            super.finishUpdate(container);
            Log.e("==finishUpdate==", "s");
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        public View getPrimaryItem() {
            return mCurrentView;
        }
    };

}
