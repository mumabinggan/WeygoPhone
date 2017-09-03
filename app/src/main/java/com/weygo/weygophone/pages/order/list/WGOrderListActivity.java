package com.weygo.weygophone.pages.order.list;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHPaddingDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHDialogUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.clientCenter.list.WGClientServiceActivity;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.order.detail.WGOrderDetailActivity;
import com.weygo.weygophone.pages.order.detail.model.response.WGRebuyResponse;
import com.weygo.weygophone.pages.order.list.adapter.WGOrderListAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.model.request.WGOrderListRequest;
import com.weygo.weygophone.pages.order.list.model.response.WGOrderListResponse;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodsView;
import com.weygo.weygophone.pages.order.list.widget.WGShopCartNavigationView;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.search.widget.WGSearchNavigationView;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.tabs.home.widget.WGTabNavigationBar;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.weygo.common.base.JHDividerItemDecoration.VERTICAL_LIST;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListActivity extends WGBaseActivity {

    LinearLayout mLayout;

    WGShopCartNavigationView mNavigationBar;

    TwinklingRefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    WGOrderListAdapter mAdapter;

    List mArray;

    int mType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadOrderList(true, false);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgorderlist_activity);
    }

    @Override
    public void initData() {
        super.initData();
        mArray = new ArrayList();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp instanceof Integer) {
                mType = (Integer)temp;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mLayout = (LinearLayout) findViewById(R.id.layout);
        mNavigationBar = (WGShopCartNavigationView) findViewById(R.id.titlebar);
        mNavigationBar.setTitle(R.string.OrderList_Title);
        mNavigationBar.setListener(new WGShopCartNavigationView.OnItemListener() {
            @Override
            public void onLeft() {
                finish();
            }

            @Override
            public void onRight() {
                Intent intent = new Intent(WGOrderListActivity.this, WGShopCartListActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGOrderListAdapter(this, mArray);
        mAdapter.setListener(new WGOrderListAdapter.OnItemListener() {
            @Override
            public void onOrderDetail(WGOrderListItem item) {
                handleOrderDetail(item);
            }

            @Override
            public void onGoodItem(WGOrderGoodItem item) {
                handleGoodItem(item);
            }

            @Override
            public void onRebuy(View view, WGOrderListItem item) {
                handleRebuyOrder(view, item);
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        float dividerHeight = JHResourceUtils.getInstance().getDimension(R.dimen.x8);
        mRecyclerView.addItemDecoration(new JHPaddingDecoration(this, (int)dividerHeight));
        mRefreshLayout = (TwinklingRefreshLayout) findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter(){
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                loadOrderList(true, true);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                loadOrderList(false, true);
            }
        });

    }

    void handleOrderDetail(WGOrderListItem item) {
        Intent intent = new Intent(this, WGOrderDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void handleRebuyOrder(final View view, WGOrderListItem item) {
        mShowDialog = JHDialogUtils.showLoadingDialog(this);
        WGApplicationRequestUtils.getInstance().loadRebuyOrderRequest(item.id, new WGApplicationRequestUtils.WGOnCompletionInteface() {
            @Override
            public void onSuccessCompletion(WGResponse response) {
                handleRebuySuccess(view, (WGRebuyResponse)response);
            }

            @Override
            public void onFailCompletion(WGResponse response) {
                JHDialogUtils.hideLoadingDialog(mShowDialog);
            }
        });
    }

    void handleRebuySuccess(View view, WGRebuyResponse response) {
        JHDialogUtils.hideLoadingDialog(mShowDialog);
        if (response.success() || response.outStock()) {
            WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(response.data.goodCount);
        }
        if (!response.success()) {
            showWarning(response.message);
        }
        //动画
        int[] distance = {0,0};
        WGApplicationAnimationUtils.add(this, mLayout, view,
                null, R.drawable.common_add_cart, mNavigationBar.getShopCartView(), distance);

    }

    void handleGoodItem(WGOrderGoodItem item) {
        Intent intent = new Intent(this, WGGoodDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void loadOrderList(final boolean refresh, final boolean pulling) {
        final WGOrderListRequest request = new WGOrderListRequest();
        request.type = mType;
        request.pageId = refresh ? 0 : mArray.size();
        if (pulling) {
            request.showsLoadingView = false;
        }
        this.postAsyn(request, WGOrderListResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleOrderListResponse((WGOrderListResponse)response, refresh, pulling);
            }

            @Override
            public void onFailure(JHRequestError error) {
                handleOrderListResponse(null, refresh, pulling);
            }
        });
    }

    void handleOrderListResponse(WGOrderListResponse response, boolean refresh, boolean pulling) {
        mRefreshLayout.finishRefreshing();
        if (response == null) {
            showWarning(R.string.Request_Fail_Tip);
            return;
        }
        if (response.success()) {
            if (mArray == null) {
                mArray = new ArrayList();
            }
            if (refresh) {
                mArray.clear();
            }
            mArray.addAll(response.data);
            mAdapter.setData(mArray);
        }
        else {
            showWarning(response.message);
        }
    }
}
