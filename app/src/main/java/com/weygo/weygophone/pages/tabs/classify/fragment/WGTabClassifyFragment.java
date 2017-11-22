package com.weygo.weygophone.pages.tabs.classify.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.base.JHResponse;
import com.weygo.common.base.NoAlphaItemAnimator;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.classifyDetail.WGClassifyDetailActivity;
import com.weygo.weygophone.pages.classifyFilter.WGClassifyDetailFilterActivity;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
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

    LinearLayout mLayout;

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
    }

    @Override
    public void onResume() {
        super.onResume();
        loadClassify();
    }

    @Override
    public void initSubView(View view) {

        final WGMainActivity activity = (WGMainActivity) getActivity();
        mLayout = (LinearLayout) view.findViewById(R.id.layout);
        mNavigationBar = (WGTabNavigationBar) view.findViewById(R.id.classify_navigationBar);
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

        initRecyclerView(view);

        mImageView = (ImageView) view.findViewById(R.id.classify_arr);
    }

    void initRecyclerView(View view) {
        mClassifyRecyclerView = (RecyclerView) view.findViewById(R.id.classifyRecyclerView);
        mClassifyRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mClassifyAdapter = new WGClassifyAdapter(getContext(), null);
        mClassifyAdapter.setHasStableIds(true);
        mClassifyRecyclerView.setAdapter(mClassifyAdapter);
        mClassifyAdapter.setOnItemClickListener(new JHRecyclerViewAdapter.OnBaseItemClickListener() {
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
        Intent intent = new Intent(getActivity(), WGClassifyDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void handleSubClassifyHotGoodItem(View view, WGClassifyHotGoodItem item) {
        Intent intent = new Intent(getActivity(), WGGoodDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
        intent.putExtras(bundle);
        startActivity(intent);
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
