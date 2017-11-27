package com.weygo.weygophone.pages.shopcart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHDrawableUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.SwipeItemLayout;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGCommonAlertView;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.login.WGLoginActivity;
import com.weygo.weygophone.pages.order.commit.WGCommitOrderActivity;
import com.weygo.weygophone.pages.register.WGRegisterActivity;
import com.weygo.weygophone.pages.shopcart.adapter.WGShopCartListAdater;
import com.weygo.weygophone.pages.shopcart.model.WGShopCart;
import com.weygo.weygophone.pages.shopcart.model.WGShopCartGoodItem;
import com.weygo.weygophone.pages.shopcart.model.request.WGCheckFailureProductsRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGCleanShopCartRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGDealFailureProductsRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGDealShopCartGiftRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGDeleteGoodFromShopCartRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGShopCartGiftRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGShopCartListRequest;
import com.weygo.weygophone.pages.shopcart.model.request.WGUpdateProductsRequest;
import com.weygo.weygophone.pages.shopcart.model.response.WGCheckFailureProductsResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGCleanShopCartResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGDealFailureProductsResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGDealShopCartGiftResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGDeleteGoodFromShopCartResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGShopCartGiftResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGShopCartListResponse;
import com.weygo.weygophone.pages.shopcart.model.response.WGUpdateProductsResponse;
import com.weygo.weygophone.pages.shopcart.widget.WGShopCartFailurePopView;
import com.weygo.weygophone.pages.shopcart.widget.WGShopCartGiftPopView;
import com.weygo.weygophone.pages.shopcart.widget.WGShopCartItemView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/8/18.
 */

public class WGShopCartListActivity extends WGTitleActivity {

    RecyclerView mRecyclerView;
    WGShopCart mData;
    WGShopCartListAdater mAdapter;

    LinearLayout mCleanLayout;
    TextView mDeliverPriceTV;
    TextView mTotalPriceTV;

    Button mCommitOrderBtn;

    View mContentView;
    View mEmptyView;

    @Override
    public void initContentView() {
        setContentView(R.layout.wgshopcart_list);
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.ShopCart_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new WGShopCartListAdater(this, null);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(this,
                JHDividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mAdapter.setListener(new WGShopCartListAdater.OnListener() {

            @Override
            public void onGoodItemClick(View view, WGShopCartGoodItem item) {
                Intent intent = new Intent(WGShopCartListActivity.this, WGGoodDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onDeleteGoodItem(View view, WGShopCartGoodItem item) {
                handleDelete(item);
            }

            @Override
            public void onUpdateGoodItem(WGShopCartGoodItem item) {

            }

            @Override
            public void onAddGoodItem(View view, WGShopCartGoodItem item) {
                loadUpdateGood(item, item.goodCount + 1);
            }

            @Override
            public void onSubGoodItem(View view, WGShopCartGoodItem item) {
                loadUpdateGood(item, item.goodCount - 1);
            }

            @Override
            public void onLongTouchItem(View view, WGShopCartGoodItem item) {
                handleDelete(item);
            }
        });
        mCleanLayout = (LinearLayout) findViewById(R.id.cleanLayout);
        mCleanLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCleanBtn();
            }
        });
        mDeliverPriceTV = (TextView) findViewById(R.id.deliverPriceTV);
        mTotalPriceTV = (TextView) findViewById(R.id.totalPriceTV);
        mCommitOrderBtn = (Button) findViewById(R.id.commitOrderBtn);
        mCommitOrderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleInCommitOrder();
            }
        });
        mEmptyView = findViewById(R.id.emptyView);
        mContentView = findViewById(R.id.dataContentView);
        mEmptyView.setVisibility(View.INVISIBLE);
    }

    void handleDelete(final WGShopCartGoodItem item) {
        WGCommonAlertView builder = new WGCommonAlertView(this, R.style.MyDialogTheme)
                .setCustomMessage(R.string.ShopCart_Clean_One_Tip)
                .setCustomCancelEnable(true)
                .setCustomPositiveButton(R.string.Collection_Delete_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handleConfirmDelete(item);
                    }
                })
                .setCustomNegativeButton(R.string.Collection_Delete_Cancel, null)
                .showAlert();
    }

    void handleConfirmDelete(WGShopCartGoodItem item) {
        loadDeleteShopCartItem(item);
    }

    void handleCleanBtn() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setMessage(R.string.ShopCart_Clean_Tip);
        builder.setPositiveButton(R.string.Collection_Delete_OK, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                loadCleanShopCart();
            }
        });
        builder.setNegativeButton(R.string.Collection_Delete_Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    void handleInCommitOrder() {
        if (!enableConfirm()) {
            return;
        }
        else {
            if (WGApplicationUserUtils.getInstance().isLogined()) {
                loadCheckFailureGood();
            }
            else {
                Intent intent = new Intent(WGShopCartListActivity.this, WGLoginActivity.class);
                startActivity(intent);
            }
        }
    }

    public void didReceivedRefreshNotification(int notification) {
        if (notification == WGConstants.WGRefreshNotificationTypeLogin) {
            loadShopCartList();
        }
        else if (notification == WGConstants.WGRefreshNotificationTypeLogout) {
            loadShopCartList();
        }
    }

    @Override
    public void didReceivedNotification(int notification) {
        super.didReceivedNotification(notification);
//        Log.e("====", "======================================");
//        if (notification == WGConstants.WGNotificationTypeShopCartCount) {
//
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadShopCartList();
    }

    void refreshFooter() {
        if (mData != null) {
            mDeliverPriceTV.setText(mData.shopCartPrice.deliveryPrice);
            mTotalPriceTV.setText(JHResourceUtils.getInstance().getString(R.string.ShopCart_Totle) +
                    mData.shopCartPrice.totalePrice);
            if (enableConfirm()) {
                mCommitOrderBtn.setBackground(JHResourceUtils.getInstance().getDrawable(R.drawable.shopcart_footer_bg));
            }
            else {
                mCommitOrderBtn.setBackground(JHResourceUtils.getInstance().getDrawable(R.drawable.shopcart_footer_disable_bg));
            }
        }
    }

    void refreshUI() {
        mAdapter.notifyDataSetChanged();
        refreshFooter();
    }

    boolean enableConfirm() {
        return JHStringUtils.isNullOrEmpty(mData.minPriceTips);
    }

    void openCommitOrder() {
        Intent intent = new Intent(WGShopCartListActivity.this, WGCommitOrderActivity.class);
        startActivity(intent);
    }

    void loadShopCartList() {
        WGShopCartListRequest request = new WGShopCartListRequest();
        request.cap = WGApplicationUserUtils.getInstance().currentPostCode();
        this.postAsyn(request, WGShopCartListResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessShopCartResponse((WGShopCartListResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessShopCartResponse(WGShopCartListResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(1);
        if (response.success()) {
            mData = response.data;
            mAdapter.setData(mData);
            refreshFooter();
            if (mData != null && mData.goods != null) {
                int goodCount = 0;
                for (WGShopCartGoodItem item : mData.goods) {
                    goodCount += item.goodCount;
                }
                WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(goodCount);
            }
        }
        else {
            showWarning(response.message);
        }
        if (mData == null || mData.goods == null || mData.goods.size() == 0) {
            mContentView.setVisibility(View.INVISIBLE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
        else {
            mContentView.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.INVISIBLE);
        }
    }

    void loadCleanShopCart() {
        WGCleanShopCartRequest request = new WGCleanShopCartRequest();
        this.postAsyn(request, WGCleanShopCartResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessCleanShopCartResponse((WGCleanShopCartResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessCleanShopCartResponse(WGCleanShopCartResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            loadShopCartList();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadDeleteShopCartItem(final WGShopCartGoodItem item) {
        WGDeleteGoodFromShopCartRequest request = new WGDeleteGoodFromShopCartRequest();
        request.id = item.shopCartId;
        this.postAsyn(request, WGDeleteGoodFromShopCartResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessDeleteGoodShopCartResponse((WGDeleteGoodFromShopCartResponse) result, item.id);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessDeleteGoodShopCartResponse(WGDeleteGoodFromShopCartResponse response, long goodId) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mData.shopCartPrice = response.data.shopCartPrice;
            mData.minPriceTips = response.data.minPriceTips;
            mData.deliverPriceDescription = response.data.deliverPriceDescription;
            List<WGShopCartGoodItem> list = new ArrayList<>();
            list.addAll(mData.goods);
            for (WGShopCartGoodItem item : list) {
                if (item.id == goodId) {
                    list.remove(item);
                    break;
                }
            }
            mData.goods = list;
            int count = 0;
            for (WGShopCartGoodItem item : list) {
                count += item.goodCount;
            }
            WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(count);
            refreshUI();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadCheckFailureGood() {
        WGCheckFailureProductsRequest request = new WGCheckFailureProductsRequest();
        this.postAsyn(request, WGCheckFailureProductsResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessCheckFailureGoodResponse((WGCheckFailureProductsResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessCheckFailureGoodResponse(WGCheckFailureProductsResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.code == 100) {
            //有失效产品
            WGShopCartFailurePopView popupView =
                    (WGShopCartFailurePopView)getLayoutInflater()
                    .inflate(R.layout.shopcart_failure_pop, null);
            popupView.setFailureTips(response.data.tip);
            popupView.setListener(new WGShopCartFailurePopView.OnItemListener() {
                @Override
                public void onRemove() {
                    loadDealFailureGood(1);
                }

                @Override
                public void onChange() {
                    loadDealFailureGood(2);
                }
            });
            JHBasePopupWindow window = new JHBasePopupWindow(popupView,
                    JHAdaptScreenUtils.devicePixelWidth(this),
                    JHAdaptScreenUtils.devicePixelHeight(this));
            popupView.setPopupWindow(window);
            window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
        }
        else if (response.code == 101) {
            //无失效产品
            loadGiftGood();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadDealFailureGood(int type) {
        WGDealFailureProductsRequest request = new WGDealFailureProductsRequest();
        request.type = type;
        this.postAsyn(request, WGDealFailureProductsResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessDealFailureGoodResponse((WGDealFailureProductsResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessDealFailureGoodResponse(WGDealFailureProductsResponse response) {
        if (response.success()) {
            loadGiftGood();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadGiftGood() {
        WGShopCartGiftRequest request = new WGShopCartGiftRequest();
        this.postAsyn(request, WGShopCartGiftResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessGiftGood((WGShopCartGiftResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessGiftGood(WGShopCartGiftResponse response) {
        if (response.success()) {
            if (response.data != null &&
                    response.data.goods != null &&
                    response.data.goods.size() > 0) {
                WGShopCartGiftPopView popupView = (WGShopCartGiftPopView)
                        getLayoutInflater()
                                .inflate(R.layout.shopcart_gift_pop, null);
                popupView.setGoods(response.data.goods);
                popupView.setListener(new WGShopCartGiftPopView.OnItemListener() {
                    @Override
                    public void onOk() {
                        loadDealGiftGood(1);
                    }

                    @Override
                    public void onNo() {
                        loadDealGiftGood(0);
                    }
                });
                JHBasePopupWindow window = new JHBasePopupWindow(popupView,
                        JHAdaptScreenUtils.devicePixelWidth(this),
                        JHAdaptScreenUtils.devicePixelHeight(this));
                popupView.setPopupWindow(window);
                window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            }
            else {
                openCommitOrder();
            }
        }
        else {
            showWarning(response.message);
        }
    }

    void loadDealGiftGood(int type) {
        WGDealShopCartGiftRequest request = new WGDealShopCartGiftRequest();
        request.type = type;
        this.postAsyn(request, WGDealShopCartGiftResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessDealGiftGoodResponse((WGDealShopCartGiftResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessDealGiftGoodResponse(WGDealShopCartGiftResponse response) {
        if (response.success()) {
            openCommitOrder();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadUpdateGood(final WGShopCartGoodItem item, final int count) {
        WGUpdateProductsRequest request = new WGUpdateProductsRequest();
        request.count = count;
        request.id = item.shopCartId;
        this.postAsyn(request, WGUpdateProductsResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessUpdateGood((WGUpdateProductsResponse) result, item.id, count);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessUpdateGood(WGUpdateProductsResponse response, long goodId, int goodCount) {
        if (response.success()) {
            mData.shopCartPrice = response.data.shopCartPrice;
            mData.minPriceTips = response.data.minPriceTips;
            mData.deliverPriceDescription = response.data.deliverPriceDescription;
            List<WGShopCartGoodItem> list = new ArrayList<>();
            list.addAll(mData.goods);
            for (WGShopCartGoodItem item : list) {
                if (item.id == goodId) {
                    item.goodCount = goodCount;
                    break;
                }
            }
            mData.goods = list;
            int count = 0;
            for (WGShopCartGoodItem item : list) {
                count += item.goodCount;
            }
            WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(count);
            refreshUI();
        }
        else {
            showWarning(response.message);
        }
    }
}
