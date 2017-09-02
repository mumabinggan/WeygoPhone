package com.weygo.weygophone.pages.collection;

import android.view.View;

import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.collection.model.request.WGCancelCollectionGoodRequest;
import com.weygo.weygophone.pages.collection.model.request.WGCollectionListRequest;
import com.weygo.weygophone.pages.collection.model.response.WGCancelCollectionGoodResponse;
import com.weygo.weygophone.pages.collection.model.response.WGCollectionListResponse;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.ArrayList;

/**
 * Created by muma on 2017/5/19.
 */

public class WGCollectionActivity extends WGGoodListActivity {

    @Override
    public void initContentView() {
        setContentView(R.layout.wgcollection_activity);
    }

    @Override
    public void initSubView() {
        super.initSubView();

        mNavigationBar.setTitle(R.string.Collection_Title);
    }

    void handleLongTouchGoodItem(View view, final WGHomeFloorContentGoodItem item) {
        showAlert(this, R.string.Collection_Delete_Tip, new OnTouchAlertListener() {
            @Override
            public void onTouchIndex(int which) {
                if (which == 1) {
                    loadDeleteGood(item);
                }
            }
        });
    }

    void handleTouchAddCart(View view, WGHomeFloorContentGoodItem item) {

    }

    void loadGoodList(final boolean refresh, final boolean pulling) {
        final WGCollectionListRequest request = new WGCollectionListRequest();
        request.pageId = refresh ? 0 : mList.size();
        if (pulling) {
            request.showsLoadingView = false;
        }
        this.postAsyn(request, WGCollectionListResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleCollectionListResponse((WGCollectionListResponse)response, refresh, pulling);
            }

            @Override
            public void onFailure(JHRequestError error) {
                handleCollectionListResponse(null, refresh, pulling);
            }
        });
    }

    void handleCollectionListResponse(WGCollectionListResponse response, boolean refresh, boolean pulling) {
        mRefreshLayout.finishRefreshing();
        if (response == null) {
            showWarning(R.string.Request_Fail_Tip);
            return;
        }
        if (response.success()) {
            if (mList == null) {
                mList = new ArrayList();
            }
            if (response.data != null) {
                mList.addAll(response.data);
                mAdapter.setData(mList);
            }
        }
        else {
            showWarning(response.message);
        }
    }

    void loadDeleteGood(WGHomeFloorContentGoodItem item) {
        final long goodId = item.id;
        final WGCancelCollectionGoodRequest request = new WGCancelCollectionGoodRequest();
        request.id = item.favoriteId;
        this.postAsyn(request, WGCancelCollectionGoodResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleCancelCollectionResponse((WGCancelCollectionGoodResponse)response, goodId);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleCancelCollectionResponse(WGCancelCollectionGoodResponse response, long goodId) {
        if (response.success()) {
            for (int num = 0; num < mList.size(); ++num) {
                WGHomeFloorContentGoodItem item = (WGHomeFloorContentGoodItem)mList.get(num);
                if (item.id == goodId) {
                    mList.remove(num);
                    break;
                }
            }
            mAdapter.setData(mList);
        }
        else {
            showWarning(response.message);
        }
    }
}
