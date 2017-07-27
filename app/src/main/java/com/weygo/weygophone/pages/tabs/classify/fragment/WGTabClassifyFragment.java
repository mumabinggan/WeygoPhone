package com.weygo.weygophone.pages.tabs.classify.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.pages.tabs.classify.adapter.WGClassifyAdapter;
import com.weygo.weygophone.pages.tabs.classify.adapter.WGSubClassifyAdapter;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyHotGoodItem;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.classify.model.request.WGClassifyRequest;
import com.weygo.weygophone.pages.tabs.classify.model.response.WGClassifyResponse;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;

import java.util.List;

/**
 * Created by muma on 2017/5/14.
 */

public class WGTabClassifyFragment extends WGFragment {

    //navigationbar
    WGTabNavigationBar mNavigationBar;

    List<WGClassifyItem> mData;

    RecyclerView mClassifyRecyclerView;
    WGClassifyAdapter mClassifyAdapter;

    ImageView mImageView;

    RecyclerView mSubClassifyRecyclerView;
    WGSubClassifyAdapter mSubClassifyAdapter;

    @Override
    public int fragmentResId() {
        return R.layout.tab_classify_fragment;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void loadRequest() {
        super.loadRequest();
        loadClassify();
    }

    @Override
    public void initSubView(View view) {

        final WGMainActivity activity = (WGMainActivity) getActivity();
        mNavigationBar = (WGTabNavigationBar) view.findViewById(R.id.classify_navigationBar);
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

        initRecyclerView(view);

        mImageView = (ImageView) view.findViewById(R.id.classify_arr);
    }

    void initRecyclerView(View view) {
        mClassifyRecyclerView = (RecyclerView) view.findViewById(R.id.classifyRecyclerView);
        mClassifyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mClassifyAdapter = new WGClassifyAdapter(getContext(), null);
        mClassifyRecyclerView.setAdapter(mClassifyAdapter);
        mClassifyAdapter.setOnItemClickListener(new JHRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                handleClassifyItemClick(view, position);
            }
        });

        mSubClassifyRecyclerView = (RecyclerView) view.findViewById(R.id.subClassifyRecyclerView);
        mSubClassifyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSubClassifyAdapter = new WGSubClassifyAdapter(getContext(), null);
        mSubClassifyRecyclerView.setAdapter(mSubClassifyAdapter);
        mSubClassifyAdapter.setOnItemClickListener(new WGSubClassifyAdapter.SubClassifyOnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onSubClassifyHotGoodItemClick(View view, WGClassifyHotGoodItem item) {
                handleSubClassifyHotGoodItem(view, item);
            }

            @Override
            public void onSubClassifyItemClick(View view, WGClassifyItem item) {
                handleSubClassifyItemClick(view, item);
            }
        });
    }

    void handleClassifyItemClick(View view, int position) {
        if (mData != null && mData.size() > position) {
            mSubClassifyAdapter.setData(mData.get(position));
        }
        else {
            mSubClassifyAdapter.setData(null);
        }
    }

    void handleSubClassifyItemClick(View view, WGClassifyItem item) {
        Log.e("handle", "handleSubClassifyItemClick" + item.name);
    }

    void handleSubClassifyHotGoodItem(View view, WGClassifyHotGoodItem item) {
        Log.e("handle", "handleSubClassifyItemClick" + item.name);
    }

    void loadClassify() {
        WGClassifyRequest request = new WGClassifyRequest();
        request.is_hot = 0;
        this.postAsyn(request, WGClassifyResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessClassify((WGClassifyResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessClassify(WGClassifyResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mData = response.data;
            mClassifyAdapter.setData(mData);
            if (mData != null && mData.size() > 0) {
                mSubClassifyAdapter.setData(mData.get(0));
            }
            else {
                mSubClassifyAdapter.setData(null);
            }
        }
        else {
            showWarning(response.message);
        }
    }
}
