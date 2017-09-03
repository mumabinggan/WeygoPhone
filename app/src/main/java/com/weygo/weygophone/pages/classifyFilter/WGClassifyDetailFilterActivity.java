package com.weygo.weygophone.pages.classifyFilter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyFilterCondition;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyKeyword;
import com.weygo.weygophone.pages.classifyFilter.model.request.WGClassifyDetailFilterRequest;
import com.weygo.weygophone.pages.classifyFilter.model.response.WGClassifyDetailFilterResponse;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by muma on 2017/8/12.
 */

public class WGClassifyDetailFilterActivity extends WGTitleActivity {

    long mClassifyId;

    WGClassifyFilterCondition mData;
    public void setData(WGClassifyFilterCondition condition) {
        mData = condition;
        refresh();
    }

    TextView mBrandTV;
    TagFlowLayout mBrandFlowLayout;
    TextView mKeywordTV;
    TagFlowLayout mKeywordFlowLayout;

    @Override
    public void initContentView() {
        setContentView(R.layout.classifydetailfilter_activity);
    }

    @Override
    public void initData() {
        super.initData();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp instanceof WGClassifyFilterCondition) {
                mData = (WGClassifyFilterCondition) temp;
            }
            else {
                mClassifyId = (long) temp;
                loadClassifyFilterDetail();
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.ClassifyDetail_Filter);
        mNavigationBar.setRightBarItem(R.drawable.classifydetail_filter_confirm);
        mBrandTV = (TextView) findViewById(R.id.brandTV);
        mBrandFlowLayout = (TagFlowLayout) findViewById(R.id.brandFlowLayout);
        mBrandFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (mData != null && mData.brands != null) {
                    WGClassifyKeyword item = mData.brands.get(position);
                    if (item != null) {
                        item.isSelected = !item.isSelected;
                    }
                }
                return true;
            }
        });
        mKeywordTV = (TextView) findViewById(R.id.keywordTV);
        mKeywordFlowLayout = (TagFlowLayout) findViewById(R.id.keywordFlowLayout);
        mKeywordFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (mData != null && mData.classifys != null) {
                    WGClassifyItem item = mData.classifys.get(position);
                    if (item != null) {
                        item.isSelected = !item.isSelected;
                    }
                }
                return true;
            }
        });
        if (mData != null) {
            refresh();
        }
    }

    void refresh() {
        if (mData == null) {
            return;
        }
        if (mData.brands != null) {
            if (mData.brands.size() > 0) {
                mBrandTV.setVisibility(View.VISIBLE);
                mBrandFlowLayout.setVisibility(View.VISIBLE);
            }
            else {
                mBrandTV.setVisibility(View.GONE);
                mBrandFlowLayout.setVisibility(View.GONE);
            }
            if (mData.classifys.size() > 0) {
                mKeywordTV.setVisibility(View.VISIBLE);
                mKeywordFlowLayout.setVisibility(View.VISIBLE);
            }
            else {
                mKeywordTV.setVisibility(View.GONE);
                mKeywordFlowLayout.setVisibility(View.GONE);
            }
        }
        List<String> brandList = new ArrayList<>();
        Set<Integer> selectBrandSet = new HashSet<>();
        List<String> keywordList = new ArrayList<>();
        Set<Integer> selectKeywordSet = new HashSet<>();
        if (mData != null) {
            if (mData.brands != null) {
                for (int num = 0; num < mData.brands.size(); ++ num) {
                    WGClassifyKeyword item = mData.brands.get(num);
                    brandList.add(item.name);
                    if (item.isSelected) {
                        selectBrandSet.add(num);
                    }
                }
            }
            if (mData.classifys != null) {
                for (int num = 0; num < mData.classifys.size(); ++ num) {
                    WGClassifyItem item = mData.classifys.get(num);
                    keywordList.add(item.name);
                    if (item.isSelected) {
                        selectKeywordSet.add(num);
                    }
                }
            }
        }
        final LayoutInflater mInflater = LayoutInflater.from(this);
        TagAdapter<String> brandTagAdapter = new TagAdapter<String>(brandList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.classifydetailfilter_item_tv,
                        mBrandFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mBrandFlowLayout.setAdapter(brandTagAdapter);
        brandTagAdapter.setSelectedList(selectBrandSet);

        TagAdapter<String> keywordTagAdapter = new TagAdapter<String>(keywordList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.classifydetailfilter_item_tv,
                        mBrandFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mKeywordFlowLayout.setAdapter(keywordTagAdapter);
        keywordTagAdapter.setSelectedList(selectKeywordSet);
    }

    public void handleRightBarItem() {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        if (mData!= null) {
            bundle.putSerializable(WGConstants.WGIntentDataKey, mData);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    void loadClassifyFilterDetail() {
        WGClassifyDetailFilterRequest request = new WGClassifyDetailFilterRequest();
        request.classifyId = mClassifyId;
        this.postAsyn(request, WGClassifyDetailFilterResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessClassifyDetailFilter((WGClassifyDetailFilterResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessClassifyDetailFilter(WGClassifyDetailFilterResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mData = response.data;
            refresh();
        }
        else {
            showWarning(response.message);
        }
    }
}
