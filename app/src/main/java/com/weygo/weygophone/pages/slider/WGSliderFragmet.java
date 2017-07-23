package com.weygo.weygophone.pages.slider;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.pages.slider.adapter.WGSliderAdapter;
import com.weygo.weygophone.pages.slider.model.WGHomeSlider;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/5/7.
 */

public class WGSliderFragmet extends WGFragment {

    WGHomeSlider mData;

    RecyclerView mRecyclerView;
    WGSliderAdapter mAdapter;

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.wgslider_fragment, container, false);
//
//        initData();
//
//        initRecyclerView(view);
//
//        return view;
//    }


    @Override
    public int fragmentResId() {
        return R.layout.wgslider_fragment;
    }

    @Override
    public void initSubView(View view) {
        initRecyclerView(view);
    }

    @Override
    public void initData() {
        mData = new WGHomeSlider();
        List topics = new ArrayList();
        WGTopicItem topicItem0 = new WGTopicItem();
        topicItem0.name = "zheng";
        topicItem0.pictureURL = "https://www.weygo.com/media/catalog/product/1/2/120073.jpg";
        topics.add(topicItem0);

        WGTopicItem topicItem1 = new WGTopicItem();
        topicItem1.name = "郑";
        topicItem1.pictureURL = "https://www.weygo.com/media/catalog/product/1/2/120073.jpg";
        topics.add(topicItem1);

        WGTopicItem topicItem2 = new WGTopicItem();
        topicItem2.name = "渊谦";
        topicItem2.pictureURL = "https://www.weygo.com/media/catalog/product/1/2/120073.jpg";
        topics.add(topicItem2);

        WGTopicItem topicItem3 = new WGTopicItem();
        topicItem3.name = "王";
        topicItem3.pictureURL = "https://www.weygo.com/media/catalog/product/1/2/120073.jpg";
        topics.add(topicItem3);

        mData.topics = topics;

        List subList01 = new ArrayList();

        WGClassifyItem subItem00 = new WGClassifyItem();
        subItem00.name = "subItem00";
        subList01.add(subItem00);

        WGClassifyItem subItem01 = new WGClassifyItem();
        subItem01.name = "subItem01";
        subList01.add(subItem01);

        WGClassifyItem subItem02 = new WGClassifyItem();
        subItem02.name = "subItem02";
        subList01.add(subItem02);

        WGClassifyItem subItem03 = new WGClassifyItem();
        subItem03.name = "subItem03";
        subList01.add(subItem03);

        WGClassifyItem subItem04 = new WGClassifyItem();
        subItem04.name = "subItem04";
        subList01.add(subItem04);

        List classifyList = new ArrayList();

        WGClassifyItem classifyItem0 = new WGClassifyItem();
        classifyItem0.name = "classifyItem0";
        classifyList.add(classifyItem0);
        classifyItem0.subArray = subList01;


        WGClassifyItem classifyItem1 = new WGClassifyItem();
        classifyItem1.name = "classifyItem1";
        classifyItem1.subArray = subList01;
        classifyList.add(classifyItem1);

        WGClassifyItem classifyItem2 = new WGClassifyItem();
        classifyItem2.name = "classifyItem2";
        classifyItem2.subArray = subList01;
        classifyList.add(classifyItem2);

        WGClassifyItem classifyItem3 = new WGClassifyItem();
        classifyItem3.name = "classifyItem3";
        classifyItem3.subArray = subList01;
        classifyList.add(classifyItem3);

        WGClassifyItem classifyItem4 = new WGClassifyItem();
        classifyItem4.name = "classifyItem4";
        classifyItem4.subArray = subList01;
        classifyList.add(classifyItem4);

        mData.classifys = classifyList;
    }

    void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.sliderRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WGSliderAdapter(getContext(), mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new WGSliderAdapter.SliderOnItemClickListener() {
            @Override
            public void onPersonInfoClick(View view) {
                Log.e("error", "onPersonInfoClick");
            }

            @Override
            public void onScanClick(View view) {
                Log.e("error", "onScanClick");
            }

            @Override
            public void onPostCodeClick(View view) {
                Log.e("error", "onPostCodeClick");
            }

            @Override
            public void onOrderClick(View view) {
                Log.e("error", "onOrderClick");
            }

            @Override
            public void onCouponClick(View view) {
                Log.e("error", "onCouponClick");
            }

            @Override
            public void onMessageCenterClick(View view) {
                Log.e("error", "onMessageCenterClick");
            }

            @Override
            public void onFootPrintsClick(View view) {
                Log.e("error", "onFootPrintsClick");
            }

            @Override
            public void onTopicItemClick(View view, WGTopicItem item) {
                Log.e("error", "onTopicItemClick" + item.name);
            }

            @Override
            public void onSubClassifyItemClick(View view, WGClassifyItem item) {
                Log.e("error", "onSubClassifyItemClick" + item.name);
            }

            @Override
            public void onItemClick(View view, int position) {
                Log.e("error", "onPersonInfoClick");
            }
        });
    }
}
