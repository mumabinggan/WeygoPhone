package com.weygo.weygophone.pages.search.fragment;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.StringCodec;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.common.tools.JHWarningUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGCellStyle7View;
import com.weygo.weygophone.pages.address.edit.WGEditAddressActivity;
import com.weygo.weygophone.pages.address.edit.model.WGEditAddressData;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyFilterCondition;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyKeyword;
import com.weygo.weygophone.pages.classifyFilter.model.request.WGClassifyDetailFilterRequest;
import com.weygo.weygophone.pages.classifyFilter.model.response.WGClassifyDetailFilterResponse;
import com.weygo.weygophone.pages.common.widget.WGSegmentView;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.search.model.WGSearchKeywordItem;
import com.weygo.weygophone.pages.search.model.request.WGHotSearchRequest;
import com.weygo.weygophone.pages.search.model.response.WGHotSearchResponse;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.fragment.WGTabHomeItemFragment;
import com.weygo.weygophone.pages.tabs.home.model.WGTitleItem;
import com.weygo.weygophone.pages.tabs.home.model.request.WGHomeTabContentRequest;
import com.weygo.weygophone.pages.tabs.home.model.request.WGHomeTitlesRequest;
import com.weygo.weygophone.pages.tabs.home.model.response.WGHomeTabContentResponse;
import com.weygo.weygophone.pages.tabs.home.model.response.WGHomeTitlesResponse;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by muma on 2016/12/19.
 */

public class WGSearchHotFragment extends WGFragment {

    WGCellStyle7View mHotTitleView;
    TagFlowLayout mHotFlowLayout;

    WGCellStyle7View mHistoryTitleView;
    TagFlowLayout mHistoryFlowLayout;

    WGHotSearchResponse mData;
    List<WGSearchKeywordItem> mHistoryList;

    public void setData(WGHotSearchResponse data) {
        mData = data;
        refresh();
    }

    String mSearchName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp instanceof String) {
                mSearchName = (String)temp;
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int fragmentResId() {
        return R.layout.search_history_fragment;
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initSubView(View view) {
        super.initSubView(view);
        mHotTitleView = (WGCellStyle7View) view.findViewById(R.id.hotTitleView);
        mHotTitleView.setTitle(R.string.Search_Hot_Title);
        mHotTitleView.setImage(R.drawable.search_hot);
        mHotFlowLayout = (TagFlowLayout) view.findViewById(R.id.hotFlowLayout);
        mHotFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                handleHotTagClick(view, position);
                return false;
            }
        });
        mHistoryTitleView = (WGCellStyle7View) view.findViewById(R.id.historyTitleView);
        mHistoryTitleView.setTitle(R.string.Search_History_Title);
        mHistoryTitleView.setImage(R.drawable.search_history);
        mHistoryFlowLayout = (TagFlowLayout) view.findViewById(R.id.historyFlowLayout);
        mHistoryFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                handleHistoryTagClick(view, position);
                return false;
            }
        });
    }

    @Override
    public void loadRequest() {
        super.loadRequest();
        loadHotSearch();
    }

    void handleHotTagClick(View view, int postion) {
        WGSearchKeywordItem item = mData.data.get(postion);
        openActivity(item);
    }

    void handleHistoryTagClick(View view, int postion) {
        WGSearchKeywordItem item = mHistoryList.get(postion);;
        openActivity(item);
    }

    void openActivity(WGSearchKeywordItem item) {
        WGApplicationGlobalUtils.getInstance().addLocalSettingHistorySearch(item);
        Intent intent = new Intent(getActivity(), WGSearchActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.name);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void refresh() {
        List<String> hotList = new ArrayList<>();
        List<String> historyList = new ArrayList<>();
        if (mData != null) {
            if (mData.data != null) {
                for (int num = 0; num < mData.data.size(); ++ num) {
                    WGSearchKeywordItem item = mData.data.get(num);
                    hotList.add(item.name);
                }
            }
        }
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        TagAdapter<String> hotTagAdapter = new TagAdapter<String>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.classifydetailfilter_item_tv,
                        mHotFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mHotFlowLayout.setAdapter(hotTagAdapter);

        mHistoryList = WGApplicationGlobalUtils.getInstance().getLocalSettingHistorySearchArray();
        if (mHistoryList != null) {
            for (int num = 0; num < mHistoryList.size(); num++) {
                WGSearchKeywordItem item = mHistoryList.get(num);
                historyList.add(item.name);
            }
            TagAdapter<String> historyTagAdapter = new TagAdapter<String>(historyList) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tv = (TextView) mInflater.inflate(R.layout.classifydetailfilter_item_tv,
                            mHistoryFlowLayout, false);
                    tv.setText(s);
                    return tv;
                }
            };
            mHistoryFlowLayout.setAdapter(historyTagAdapter);
        }

        if (mHistoryList == null) {
            mHistoryTitleView.setVisibility(View.INVISIBLE);
            mHistoryFlowLayout.setVisibility(View.INVISIBLE);
        }
        else {
            mHistoryTitleView.setVisibility(View.VISIBLE);
            mHistoryFlowLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    void loadHotSearch() {
        WGHotSearchRequest request = new WGHotSearchRequest();
        this.postAsyn(request, WGHotSearchResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessHotSearchResponse((WGHotSearchResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessHotSearchResponse(WGHotSearchResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mData = response;
            refresh();
        }
        else {
            showWarning(response.message);
        }
    }
}
