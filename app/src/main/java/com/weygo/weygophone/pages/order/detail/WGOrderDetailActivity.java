package com.weygo.weygophone.pages.order.detail;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.fastjson.JSON;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.weygo.common.base.JHPaddingDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHDialogUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.goodDetail.model.response.WGAddGoodToCartResponse;
import com.weygo.weygophone.pages.order.detail.adapter.WGOrderDetailAdapter;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDeliver;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDetail;
import com.weygo.weygophone.pages.order.detail.model.WGOrderFax;
import com.weygo.weygophone.pages.order.detail.model.WGOrderPay;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatus;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatusItem;
import com.weygo.weygophone.pages.order.detail.model.request.WGOrderDetailRequest;
import com.weygo.weygophone.pages.order.detail.model.response.WGOrderDetailResponse;
import com.weygo.weygophone.pages.order.detail.model.response.WGRebuyResponse;
import com.weygo.weygophone.pages.order.detail.widget.WGOrderDetailRebuyView;
import com.weygo.weygophone.pages.order.list.WGOrderListActivity;
import com.weygo.weygophone.pages.order.list.adapter.WGOrderListAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.model.request.WGOrderListRequest;
import com.weygo.weygophone.pages.order.list.model.response.WGOrderListResponse;
import com.weygo.weygophone.pages.order.list.widget.WGShopCartNavigationView;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderDetailActivity extends WGBaseActivity {

    LinearLayout mLayout;

    WGShopCartNavigationView mNavigationBar;

    RecyclerView mRecyclerView;
    WGOrderDetailAdapter mAdapter;
    WGOrderDetail mData;

    WGOrderDetailRebuyView mRebuyView;

    long mOrderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadOrderDetail();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgorderdetail_activity);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp instanceof Long) {
                mOrderId = (long)temp;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mLayout = (LinearLayout) findViewById(R.id.layout);
        mNavigationBar = (WGShopCartNavigationView) findViewById(R.id.titlebar);
        mNavigationBar.setTitle(R.string.OrderDetail_Title);
        mNavigationBar.setListener(new WGShopCartNavigationView.OnItemListener() {
            @Override
            public void onLeft() {
                finish();
            }

            @Override
            public void onRight() {
                Intent intent = new Intent(WGOrderDetailActivity.this, WGShopCartListActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGOrderDetailAdapter(this, mData);
        mAdapter.setListener(new WGOrderDetailAdapter.OnItemListener() {
            @Override
            public void onPurchase(WGHomeFloorContentGoodItem item, View view, Point fromPoint) {
                handlePurchase(item, view, fromPoint);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRebuyView = (WGOrderDetailRebuyView) findViewById(R.id.rebuyView);
        mRebuyView.setListener(new WGOrderDetailRebuyView.OnRebuyListener() {
            @Override
            public void onTouchRebuy(View view) {
                loadRebuyOrder(view, mData);
            }
        });
    }

    void handlePurchase(WGHomeFloorContentGoodItem item, View view, Point fromPoint) {
        if (JHStringUtils.isNullOrEmpty(WGApplicationUserUtils.getInstance().currentPostCode())) {
            WGPostPopView popupView = (WGPostPopView) getLayoutInflater().inflate(R.layout.common_cap_pop, null);
            popupView.setListener(new WGPostPopView.OnItemListener() {
                @Override
                public void onSuccess() {

                }
            });
            JHBasePopupWindow window = new JHBasePopupWindow(popupView,
                    JHAdaptScreenUtils.devicePixelWidth(this),
                    JHAdaptScreenUtils.devicePixelHeight(this));
            popupView.setPopupWindow(window);
            window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            return;
        }
        WGApplicationRequestUtils.getInstance().loadAddGoodToCart(item.id, 1, new WGApplicationRequestUtils.WGOnCompletionInteface() {
            @Override
            public void onSuccessCompletion(WGResponse response) {
                handleShopCartCount((WGAddGoodToCartResponse) response);
            }

            @Override
            public void onFailCompletion(WGResponse response) {

            }
        });

        int[] distance = {0,0};
        int[] endPoint = mNavigationBar.getShopCartViewPoint();
        WGApplicationAnimationUtils.add(this, mLayout, fromPoint,
                item.pictureURL, R.drawable.common_add_cart, endPoint, distance);
    }

    void handleShopCartCount(WGAddGoodToCartResponse response) {
        if (response.success()) {
            WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(response.data.goodCount);
        }
        else {
            showWarning(response.message);
        }
    }

    void loadOrderDetail() {
        final WGOrderDetailRequest request = new WGOrderDetailRequest();
        request.id = mOrderId;
        this.postAsyn(request, WGOrderDetailResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleOrderDetailResponse((WGOrderDetailResponse)response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                handleOrderDetailResponse(null);
            }
        });
    }

    void handleOrderDetailResponse(WGOrderDetailResponse response) {
        if (response == null) {
            showWarning(R.string.Request_Fail_Tip);
            return;
        }
        if (response.success()) {
            Log.e("onSuccess", JSON.toJSONString(response));
            mData = response.data;
            mAdapter.setData(mData);
        }
        else {
            showWarning(response.message);
        }
    }

    void loadRebuyOrder(final View view, WGOrderDetail item) {
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
        int[] startPoint = new int[2];
        view.getLocationInWindow(startPoint);
        Point point = new Point();
        point.x = startPoint[0] + view.getWidth()/2;
        point.y = startPoint[1] - view.getHeight();
        WGApplicationAnimationUtils.add(this, mLayout, point,
                null, R.drawable.common_add_cart, mNavigationBar.getShopCartViewPoint(), distance);

    }
}
