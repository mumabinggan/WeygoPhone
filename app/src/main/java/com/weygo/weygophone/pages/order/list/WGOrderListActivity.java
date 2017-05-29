package com.weygo.weygophone.pages.order.list;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHPaddingDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.pages.order.list.adapter.WGOrderListAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.model.request.WGOrderListRequest;
import com.weygo.weygophone.pages.order.list.model.response.WGOrderListResponse;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodsView;

import java.util.ArrayList;
import java.util.List;

import static com.weygo.common.base.JHDividerItemDecoration.VERTICAL_LIST;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListActivity extends WGTitleActivity {

    TwinklingRefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    WGOrderListAdapter mAdapter;

    List mArray;

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
        List list = new ArrayList();
        WGOrderGoodItem item = new WGOrderGoodItem();
        item.name = "郑枯塔顶地";
        item.goodCount = 12;
        item.price = "2332.sf";
        item.currentPrice = "23233";
        item.pictureURL = "https://www.weygo.com/media/catalog/product/1/2/120073.jpg";
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        list.add(item);
        WGOrderListItem orderItem = new WGOrderListItem();
        orderItem.goods = list;
        orderItem.sn = "郑艺术硕士塔顶";
        orderItem.deliverPrice = "132.23";
        orderItem.status = "在源代码要";
        orderItem.totalPrice = "5434.2";

        mArray.add(orderItem);

        mArray.add(orderItem);
        mArray.add(orderItem);
        mArray.add(orderItem);
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.OrderList_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGOrderListAdapter(this, mArray);
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

    @Override
    public void handleRightBarItem() {
        super.handleRightBarItem();
    }

    void loadOrderList(final boolean refresh, final boolean pulling) {
        final WGOrderListRequest request = new WGOrderListRequest();
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
            mArray.addAll(response.data);
            mAdapter.setData(mArray);
        }
        else {
            showWarning(response.message);
        }
    }
}
