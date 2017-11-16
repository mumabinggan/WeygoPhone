package com.weygo.weygophone.pages.footprint;

import android.view.View;

import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.collection.WGGoodListActivity;
import com.weygo.weygophone.pages.footprint.model.request.WGFootPrintRequest;
import com.weygo.weygophone.pages.footprint.model.response.WGFootPrintResponse;

import java.util.ArrayList;

/**
 * Created by muma on 2017/5/19.
 */

public class WGFootprintActivity extends WGGoodListActivity {
    @Override
    public void initContentView() {
        setContentView(R.layout.wgfootprint_activity);
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.Printfoot_Title);
    }

    public void loadGoodList(final boolean refresh, final boolean pulling) {
        final WGFootPrintRequest request = new WGFootPrintRequest();
        request.pageId = refresh ? 0 : mList.size();
        if (pulling) {
            request.showsLoadingView = false;
        }
        this.postAsyn(request, WGFootPrintResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleFootPrintListResponse((WGFootPrintResponse)response, refresh, pulling, request.pageSize);
            }

            @Override
            public void onFailure(JHRequestError error) {
                finishRefresh(mRefreshLayout, refresh, pulling);
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleFootPrintListResponse(WGFootPrintResponse response, boolean refresh, boolean pulling, int pageSize) {
        finishRefresh(mRefreshLayout, refresh, pulling);
        if (response.success()) {
            if (mList == null) {
                mList = new ArrayList();
            }
            if (refresh) {
                mList.clear();
            }
            if (response.data != null) {
                mList.addAll(response.data);
            }
            mAdapter.setData(mList);
        }
        else {
            showWarning(response.message);
        }
        if (mList == null || mList.size() == 0) {
            mContentView.setVisibility(View.INVISIBLE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
        else {
            mContentView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.INVISIBLE);
        }
    }
}
