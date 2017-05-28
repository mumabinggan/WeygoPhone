package com.weygo.weygophone.pages.order.detail;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHPaddingDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.pages.order.detail.adapter.WGOrderDetailAdapter;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDeliver;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDetail;
import com.weygo.weygophone.pages.order.detail.model.WGOrderFax;
import com.weygo.weygophone.pages.order.detail.model.WGOrderPay;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatus;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatusItem;
import com.weygo.weygophone.pages.order.list.adapter.WGOrderListAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.model.request.WGOrderListRequest;
import com.weygo.weygophone.pages.order.list.model.response.WGOrderListResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderDetailActivity extends WGTitleActivity {

    RecyclerView mRecyclerView;
    WGOrderDetailAdapter mAdapter;

    WGOrderDetail mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadOrderList(true, false);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgorderdetail_activity);
    }

    @Override
    public void initData() {
        super.initData();
        mData = new WGOrderDetail();
        mData.sn = "adfasdfasd";
        WGOrderStatus status = new WGOrderStatus();

        WGOrderStatusItem item = new WGOrderStatusItem();
        item.time = "1900 23-23 23-23";
        item.statusText = "sdfadfasfdasadsasfd";
        item.totalStatusText = "WTSFSFAFS";

        status.status = Arrays.asList(item, item, item);
        status.currentStatus = 0;

        mData.status = status;

        WGOrderDeliver deliver = new WGOrderDeliver();
        deliver.userName = "磁艺术硕士";
        deliver.userAddress = "sfasdfasdf";
        deliver.phone = "fsdfasdfasfasf";
        deliver.deliverTime = "2323232332";

        mData.deliver = deliver;

        mData.pay = Arrays.asList("在艺术硕士 艺术硕士", "fasdfasdfas", "WEFADSFASDFASD");

        WGOrderFax fax = new WGOrderFax();
        fax.companyName = "23232324234";
        fax.taxCode = "3233sfa";
        fax.cap = "23asdfasd";

        mData.fax = fax;

        List list = new ArrayList();
        WGOrderGoodItem goodItem = new WGOrderGoodItem();
        goodItem.name = "郑枯塔顶地";
        goodItem.goodCount = 12;
        goodItem.price = "2332.sf";
        goodItem.currentPrice = "23233";
        goodItem.pictureURL = "https://www.weygo.com/media/catalog/product/1/2/120073.jpg";
        list.add(goodItem);
        list.add(goodItem);
        list.add(goodItem);
        list.add(goodItem);
        list.add(goodItem);
        list.add(goodItem);
        mData.goods = list;
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.OrderDetail_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGOrderDetailAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        //float dividerHeight = JHResourceUtils.getInstance().getDimension(R.dimen.x8);
        //mRecyclerView.addItemDecoration(new JHPaddingDecoration(this, (int)dividerHeight));
    }

    @Override
    public void handleRightBarItem() {
        super.handleRightBarItem();
    }
//
//    void loadOrderDetail() {
//        final WGOrderListRequest request = new WGOrderListRequest();
//        this.postAsyn(request, WGOrderListResponse.class, new JHResponseCallBack() {
//            @Override
//            public void onSuccess(JHResponse response) {
//                handleOrderListResponse((WGOrderListResponse)response, refresh, pulling);
//            }
//
//            @Override
//            public void onFailure(JHRequestError error) {
//                handleOrderListResponse(null, refresh, pulling);
//            }
//        });
//    }
//
//    void handleOrderListResponse(WGOrderListResponse response, boolean refresh, boolean pulling) {
//        mRefreshLayout.finishRefreshing();
//        if (response == null) {
//            showWarning(R.string.Request_Fail_Tip);
//            return;
//        }
//        if (response.success()) {
//            if (mArray == null) {
//                mArray = new ArrayList();
//            }
//            mArray.addAll(response.data);
//            mAdapter.setData(mArray);
//        }
//        else {
//            showWarning(response.message);
//        }
//    }
}
