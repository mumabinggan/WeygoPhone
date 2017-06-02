package com.weygo.weygophone.pages.goodDetail.widget;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.tools.JHFontUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.widget.WGViewPager;
import com.weygo.weygophone.pages.common.widget.WGSegmentView;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/5/31.
 */

public class WGGoodDetailDescriptionView extends JHLinearLayout {

    //Segment and page
    WGSegmentView mHomeSegmentView;
    WGViewPager mViewPager;
    WGGoodDetailDescriptionView.MyAdapter mPagerAdapter;
    IndicatorViewPager mIndicatorViewPager;

    WGGoodDetail mGoodDetail;

    public WGGoodDetailDescriptionView(Context context) {
        this(context, null);
    }

    public WGGoodDetailDescriptionView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGGoodDetailDescriptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mHomeSegmentView = (WGSegmentView) findViewById(R.id.segmentView);
                float unSelectSize = JHFontUtils.font(getContext(), 12);
        float selectSize = unSelectSize;
        int selectColor = getResources().getColor(R.color.WGAppBaseColor);
        int unSelectColor = getResources().getColor(R.color.WGAppBaseTitleAColor);
        mHomeSegmentView.setScrollBar(new ColorBar(getContext(), selectColor, 5));
        mHomeSegmentView.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
        mViewPager = (WGViewPager) findViewById(R.id.viewPager);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //handleSelectViewPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mIndicatorViewPager = new IndicatorViewPager(mHomeSegmentView, mViewPager);
        mPagerAdapter = new MyAdapter();
        mIndicatorViewPager.setAdapter(mPagerAdapter);
    }

    public void showWithData(WGGoodDetail goodDetail) {
        if (mGoodDetail != null) {
            return;
        }

        mGoodDetail = goodDetail;
        mPagerAdapter.setGoodDetail(goodDetail);
    }

    void handleSelectViewPager(int index) {
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        View view = mViewPager.getChildAt(index);
        if (index == 0) {
            params.height = view.getHeight();
        }
        else {
            params.height = 200;
        }
        params.height = view.getHeight();

        Log.e("---height--", view.getHeight() + ":" + view.getMeasuredHeight() + "+" + mViewPager.getChildCount());
        mViewPager.setLayoutParams(params);
    }

    private class MyAdapter extends IndicatorViewPager.IndicatorViewPagerAdapter {

        WGGoodDetail mGoodDetail;
        public void setGoodDetail(WGGoodDetail goodDetail) {
            mGoodDetail = goodDetail;
            if (mGoodDetail != null) {
                if (mTitleList == null) {
                    mTitleList = new ArrayList();
                }
                if (mGoodDetail.productDes != null && mGoodDetail.productDes.size() > 0) {
                    mTitleList.add(R.string.GoodDetail_Des);
                }
                else {
                    mTitleList.add(R.string.GoodDetail_CommodityInfo);
                }
                mTitleList.add(R.string.GoodDetail_Info);
                mTitleList.add(R.string.GoodDetail_Deliver);
            }
            notifyDataSetChanged();
        }

        private List<Integer> mTitleList;

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
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.common_segment_title, container, false);
            }
            TextView textView = (TextView) convertView;
            if (mTitleList != null && mTitleList.size() > position) {
                textView.setText(mTitleList.get(position));
                textView.setWidth(320 / getCount() * R.dimen.x1);
            }
            return convertView;
        }

        @Override
        public int getPageViewType(int position) {
            return position;
        }

        @Override
        public View getViewForPage(int position, View convertView, ViewGroup container) {
            Log.e("---getViewForPage---", container.getClass().toString());
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                if (position == 0) {
                    LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.wggooddetail_delivertips, container, false);
                    TextView textView = (TextView)linearLayout.findViewById(R.id.contentTV);
                    textView.setText(mGoodDetail.productInfo);
                    convertView = linearLayout;
//                    LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.wggooddetail_des, container, false);
//                    if (mGoodDetail != null) {
//                        TableLayout tableLayout = (TableLayout)linearLayout.findViewById(R.id.tableLayout);
//                        TextView textView = (TextView)linearLayout.findViewById(R.id.contentTV);
//                        if (mGoodDetail.productDes != null && mGoodDetail.productDes.size() > 0) {
//                            for (int x = 0; x < mGoodDetail.productDes.size(); x++) { // 循环设置表格行
//                                TableRow row = new TableRow(getContext()); // 定义表格行
//                                WGGoodDetail.WGGoodDetailDesItem item = mGoodDetail.productDes.get(x);
//                                TextView keyText = new TextView(getContext());
//                                keyText.setText(item.name);
//                                row.addView(keyText, 0);
//                                TextView valueText = new TextView(getContext());
//                                valueText.setText(item.value);
//                                row.addView(valueText, 1);
//                                tableLayout.addView(row); // 向表格之中增加若干个表格行
//                                tableLayout.setVisibility(VISIBLE);
//                                textView.setVisibility(INVISIBLE);
//                            }
//                        }
//                        else {
//                            textView.setText(mGoodDetail.productInfo);
//                            tableLayout.setVisibility(INVISIBLE);
//                            textView.setVisibility(VISIBLE);
//                        }
//                        convertView = linearLayout;
//                    }

                }
                else if (position == 1) {
                    LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.wggooddetail_delivertips1, container, false);
                    TextView textView = (TextView)linearLayout.findViewById(R.id.contentTV);

                    textView.setText(mGoodDetail.purchaseTip);
                    convertView = linearLayout;
                }
                else {
                    LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.wggooddetail_delivertips2, container, false);
                    TextView textView = (TextView)linearLayout.findViewById(R.id.contentTV);
                    textView.setText(mGoodDetail.deliveryInfo);
                    convertView = linearLayout;
                }
            }
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
