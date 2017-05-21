package com.weygo.weygophone.pages.integral.myIntegral;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.pages.integral.myIntegral.adapter.WGMyIntegrationAdapter;
import com.weygo.weygophone.pages.integral.myIntegral.model.WGIntegrationDetail;
import com.weygo.weygophone.pages.integral.myIntegral.model.request.WGIntegrationDetailRequest;
import com.weygo.weygophone.pages.integral.myIntegral.model.response.WGIntegrationDetailResponse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/5/20.
 */

public class WGMyIntegralActivity extends WGTitleActivity {

    WGIntegrationDetail mIntegrationDetail;

    protected TwinklingRefreshLayout mRefreshLayout;
    protected RecyclerView mRecyclerView;
    protected WGMyIntegrationAdapter mAdapter;

    TextView mIntegrationTextView;

    RelativeLayout mContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadIntegrationDetail(true, false);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgmyintegration_activity);
    }

    @Override
    public void initSubView() {
        super.initSubView();

        mNavigationBar.setTitle(R.string.MyIntegral_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(this,
                JHDividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGMyIntegrationAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadIntegrationDetail(false, true);
            }
        });

        mIntegrationTextView = (TextView) findViewById(R.id.integralCountTextView);

        mContentLayout = (RelativeLayout) findViewById(R.id.contentLayout);
    }

    @Override
    public void handleRightBarItem() {
        super.handleRightBarItem();
    }

    void refreshUI() {
        if (mIntegrationDetail!= null) {
            mContentLayout.setVisibility(View.VISIBLE);
            mIntegrationTextView.setText(JHResourceUtils.getInstance().getString(R.string.MyIntegral_Number) +
                    mIntegrationDetail.totalCount);
        }
    }

    void loadIntegrationDetail(final boolean refresh, final boolean pulling) {
        final WGIntegrationDetailRequest request = new WGIntegrationDetailRequest();
        int size = 0;
        if (mIntegrationDetail != null && mIntegrationDetail.hasHistory()) {
            size = mIntegrationDetail.historyCount();
        }
        request.pageId = refresh ? 0 : size;
        if (pulling) {
            request.showsLoadingView = false;
        }
        this.postAsyn(request, WGIntegrationDetailResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleIntegrationDetailResponse((WGIntegrationDetailResponse)response, refresh, pulling);
            }

            @Override
            public void onFailure(JHRequestError error) {
                handleIntegrationDetailResponse(null, refresh, pulling);
            }
        });
    }

    void handleIntegrationDetailResponse(WGIntegrationDetailResponse response, boolean refresh, boolean pulling) {
        mRefreshLayout.finishRefreshing();
        if (response == null) {
            showWarning(R.string.Request_Fail_Tip);
            return;
        }
        if (response.success()) {
            if (mIntegrationDetail == null) {
                mIntegrationDetail = response.data;
            }
            else {
                if (response.data.hasHistory()) {
                    mIntegrationDetail.history.addAll(response.data.history);
                    mAdapter.setData(mIntegrationDetail);
                }
            }
            refreshUI();
        }
        else {
            showWarning(response.message);
        }
    }

}
