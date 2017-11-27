package com.weygo.weygophone.pages.goodDetail.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.shizhefei.view.indicator.Indicator;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHFontUtils;
import com.weygo.common.tools.JHResourceUtils;
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
                handleSelectViewPager(position);
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
        handleSelectViewPager(0);
    }

    void handleSelectViewPager(int position) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        LinearLayout linearLayout = null;
        if (position == 0) {
            linearLayout = (LinearLayout) inflater.inflate(R.layout.wggooddetail_des, null);
            if (mGoodDetail != null) {
                TextView textView = (TextView)linearLayout.findViewById(R.id.contentTV);
                GridView gridView = (GridView) linearLayout.findViewById(R.id.gridView);
                if (mGoodDetail.productDes != null && mGoodDetail.productDes.size() > 0) {
                    GridViewAdapter adapter = new GridViewAdapter(getContext());
                    List list = new ArrayList();
                    for (int num = 0; num < mGoodDetail.productDes.size(); ++num) {
                        WGGoodDetail.WGGoodDetailDesItem item = mGoodDetail.productDes.get(num);
                        list.add(item.name);
                        list.add(item.value);
                    }
                    gridView.setAdapter(adapter);
                    adapter.setList(list);
                    ViewGroup.LayoutParams params = gridView.getLayoutParams();
                    params.height = (int) (list.size()/2 * JHAdaptScreenUtils.pixelWidth(getContext(), 41) +
                            JHAdaptScreenUtils.pixelWidth(getContext(), 1));
                    gridView.setLayoutParams(params);
                    gridView.setVisibility(VISIBLE);
                    textView.setVisibility(GONE);
                }
                else {
                    textView.setText(mGoodDetail.productInfo);
                    gridView.setVisibility(GONE);
                    textView.setVisibility(VISIBLE);
                }
            }
        }
        else if (position == 1 || position == 2) {
            linearLayout = (LinearLayout) inflater.inflate(R.layout.wggooddetail_delivertips, null);
            TextView textView = (TextView) linearLayout.findViewById(R.id.contentTV);
            if (position == 1) {
                textView.setText(mGoodDetail.purchaseTip);
            }
            else {
                textView.setText(mGoodDetail.deliveryInfo);
            }
            textView.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        linearLayout.measure(w, h);
        int height = linearLayout.getMeasuredHeight();
        ViewGroup.LayoutParams params = mViewPager.getLayoutParams();
        params.height = height;
        mViewPager.setLayoutParams(params);
    }

    private class GridViewAdapter extends BaseAdapter {

        private Context context;

        List mList;
        public void setList(List list) {
            mList = list;
            notifyDataSetChanged();
        }

        public GridViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            if (mList == null) {
                return 0;
            }
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView result = new TextView(context);
            result.setText(mList.get(position) + "");
            result.setTextColor(JHResourceUtils.getInstance().getColor(R.color.WGAppBaseTitleAAColor));
            result.setTextSize(12);
            float height = JHAdaptScreenUtils.pixelWidth(getContext(), 40);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, (int) height));
            result.setLayoutParams(params);
            result.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
            result.setPadding(14, 0, 14, 0);
//            result.setBackgroundColor(Color.GREEN); //设置背景颜色
            result.setBackgroundColor(Color.WHITE); //设置背景颜色
            return result;
        }

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
            if (convertView == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                if (position == 0) {
                    LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.wggooddetail_des, container, false);
                    if (mGoodDetail != null) {
                        GridView gridView = (GridView) linearLayout.findViewById(R.id.gridView);
                        TextView textView = (TextView)linearLayout.findViewById(R.id.contentTV);
                        if (mGoodDetail.productDes != null && mGoodDetail.productDes.size() > 0) {
                            GridViewAdapter adapter = new GridViewAdapter(getContext());
                            List list = new ArrayList();
                            for (WGGoodDetail.WGGoodDetailDesItem item : mGoodDetail.productDes) {
                                list.add(item.name);
                                list.add(item.value);
                            }
                            adapter.setList(list);
                            gridView.setAdapter(adapter);
                            gridView.setVisibility(VISIBLE);
                            textView.setVisibility(GONE);
                        }
                        else {
                            textView.setText(mGoodDetail.productInfo);
                            gridView.setVisibility(GONE);
                            textView.setVisibility(VISIBLE);
                        }
                        convertView = linearLayout;
                    }
                }
                else if (position == 1 || position == 2) {
                    LinearLayout linearLayout = (LinearLayout)inflater.inflate(R.layout.wggooddetail_delivertips, container, false);
                    TextView textView = (TextView)linearLayout.findViewById(R.id.contentTV);
                    if (position == 1) {
                        textView.setText(mGoodDetail.purchaseTip);
                    }
                    else {
                        textView.setText(mGoodDetail.deliveryInfo);
                    }
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
