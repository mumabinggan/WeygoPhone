package com.weygo.weygophone.pages.order.commit;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.pages.order.commit.adapter.WGCommitOrderAdapter;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDeliverTime;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDetail;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderPay;
import com.weygo.weygophone.pages.order.commit.model.WGOrderExpireGood;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightDetail;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightGoodItem;
import com.weygo.weygophone.pages.order.commit.model.request.WGCommitOrderDeleteExpireGoodRequest;
import com.weygo.weygophone.pages.order.commit.model.request.WGCommitOrderRequest;
import com.weygo.weygophone.pages.order.commit.model.request.WGCommitOrderUpdateTimeRequest;
import com.weygo.weygophone.pages.order.commit.model.request.WGOverHeightDeleteRequest;
import com.weygo.weygophone.pages.order.commit.model.request.WGOverHeightRequest;
import com.weygo.weygophone.pages.order.commit.model.request.WGOverHeightResetRequest;
import com.weygo.weygophone.pages.order.commit.model.request.WGSettlementResultRequest;
import com.weygo.weygophone.pages.order.commit.model.response.WGCommitOrderDeleteExpireGoodResponse;
import com.weygo.weygophone.pages.order.commit.model.response.WGCommitOrderResponse;
import com.weygo.weygophone.pages.order.commit.model.response.WGCommitOrderUpdateTimeResponse;
import com.weygo.weygophone.pages.order.commit.model.response.WGOverHeightDeleteResponse;
import com.weygo.weygophone.pages.order.commit.model.response.WGOverHeightResetResponse;
import com.weygo.weygophone.pages.order.commit.model.response.WGOverHeightResponse;
import com.weygo.weygophone.pages.order.commit.model.response.WGSettlementResultResponse;
import com.weygo.weygophone.pages.order.commit.widget.WGCommitOrderExpirePopView;
import com.weygo.weygophone.pages.order.commit.widget.WGCommitOrderFooterView;
import com.weygo.weygophone.pages.order.commit.widget.WGCommitOrderOverWeightPopView;
import com.weygo.weygophone.pages.receipt.model.WGReceipt;

import java.util.List;

/**
 * Created by muma on 2017/5/22.
 */

public class WGCommitOrderActivity extends WGTitleActivity {

    RecyclerView mRecyclerView;

    WGCommitOrderAdapter mAdapter;

    WGCommitOrderDetail mData;

    WGCommitOrderFooterView mFooterView;

    WGCommitOrderExpirePopView mExpireView;

    WGCommitOrderOverWeightPopView mOverWeightView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSettlementResultDetail();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.commitorder_activity);
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.CommitOrder_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGCommitOrderAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mFooterView = (WGCommitOrderFooterView) findViewById(R.id.footerView);
        mFooterView.setVisibility(View.INVISIBLE);
        mFooterView.setListener(new WGCommitOrderFooterView.OnItemListener() {
            @Override
            public void onCommit() {
                touchCommitBtn();
            }
        });
    }

    void touchCommitBtn() {
        if (enableConfirm()) {
            loadCommitOrder();;
        }
    }

    boolean enableConfirm() {
        if (mData != null && !JHStringUtils.isNullOrEmpty(mData.minPriceTips)) {
            return true;
        }
        return false;
    }

    void showExpireGood(WGOrderExpireGood expireGood) {
        mExpireView = (WGCommitOrderExpirePopView) getLayoutInflater()
                .inflate(R.layout.commitorder_expire_good_pop, null);
        mExpireView.setGoods(expireGood.goods);
        JHBasePopupWindow window = new JHBasePopupWindow(mExpireView,
                JHAdaptScreenUtils.devicePixelWidth(this),
                JHAdaptScreenUtils.devicePixelHeight(this));
        mExpireView.setPopupWindow(window);
        mExpireView.setListener(new WGCommitOrderExpirePopView.OnItemListener() {
            @Override
            public void onOk() {
                handleChangeTime();
            }

            @Override
            public void onNo() {
                handleDeleteExpireGood();
            }
        });
        window.showAtLocation(mExpireView, Gravity.CENTER, 0, 0);
    }

    void showOverWeightGood(List<WGOverHeightDetail> overHeightDetail) {
        mOverWeightView = (WGCommitOrderOverWeightPopView) getLayoutInflater()
                .inflate(R.layout.commitorder_overheight_pop, null);
        mOverWeightView.setGoods(overHeightDetail);
        JHBasePopupWindow window = new JHBasePopupWindow(mOverWeightView,
                JHAdaptScreenUtils.devicePixelWidth(this),
                JHAdaptScreenUtils.devicePixelHeight(this));
        mOverWeightView.setPopupWindow(window);
        mOverWeightView.setListener(new WGCommitOrderOverWeightPopView.OnItemListener() {
            @Override
            public void onDeleteAll() {
                loadDeleteOverHeight();
            }

            @Override
            public void onConfirm(List<WGOverHeightDetail> overWeightList) {
                loadOverHeightReset(overWeightList);
            }
        });
        window.showAtLocation(mOverWeightView, Gravity.CENTER, 0, 0);
    }

    void handleChangeTime() {
        mData.deliverTime.currentDateId = null;
        mData.deliverTime.currentTimeId = null;
        refreshUI();
    }

    void handleDeleteExpireGood() {
        loadDeleteExpireGoodRequest();
    }

    void refreshUI() {
        mAdapter.setData(mData);
        mFooterView.showWithData(mData);
        mFooterView.setVisibility(View.VISIBLE);
    }

    void loadSettlementResultDetail() {
        WGSettlementResultRequest request = new WGSettlementResultRequest();
        if (mData != null && mData.address != null) {
            request.addressId = mData.address.addressId;
        }
        this.postAsyn(request, WGSettlementResultResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessSettlementResultDetail((WGSettlementResultResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessSettlementResultDetail(WGSettlementResultResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mData = new WGCommitOrderDetail();
            mData.initWithSettlementResult(response.data);
            refreshUI();
            WGOrderExpireGood expireGood = response.data.expireGood;
            if (expireGood != null) {
                expireGood.canChangeTime = false;
                showExpireGood(expireGood);
            }
            else {
                if (response != null) {
                    if (response.data != null) {
                        if (response.data.overHeightDetail != null &&
                                response.data.overHeightDetail.size() > 0) {
                            showOverWeightGood(response.data.overHeightDetail);
                        }
                    }
                }
            }
        }
        else {
            showWarning(response.message);
        }
    }

    void loadUpdateTimeRequest() {
        WGCommitOrderUpdateTimeRequest request = new WGCommitOrderUpdateTimeRequest();
        request.timeId = mData.deliverTime.currentTimeId;
        this.postAsyn(request, WGCommitOrderUpdateTimeResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessUpdateTime((WGCommitOrderUpdateTimeResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessUpdateTime(WGCommitOrderUpdateTimeResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            if (response.data != null &&
                    response.data.expireGood != null) {
                response.data.expireGood.canChangeTime = true;
                showExpireGood(response.data.expireGood);
            }
        }
        else {
            showWarning(response.message);
        }
    }

    void loadDeleteExpireGoodRequest() {
        WGCommitOrderDeleteExpireGoodRequest request = new WGCommitOrderDeleteExpireGoodRequest();
        request.timeId = mData.deliverTime.currentTimeId;
        this.postAsyn(request, WGCommitOrderDeleteExpireGoodResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessDeleteExpireGood((WGCommitOrderDeleteExpireGoodResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessDeleteExpireGood(WGCommitOrderDeleteExpireGoodResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mExpireView.dismiss();
            loadSettlementResultDetail();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadCommitOrder() {
        WGCommitOrderRequest request = new WGCommitOrderRequest();
        request.addressId = mData.address.addressId;

        WGReceipt receipt = mData.receipt;
        request.useBilling = (receipt == null) ? 0 : 1;
        request.billingCompanyName = receipt.companyName;
        request.billingCountry = receipt.countryId;
        request.billingPhone = receipt.phone;
        request.billingAddress = receipt.address;
        request.billingCivico = receipt.civico;
        request.billingCity = receipt.city;
        request.billingCap = receipt.cap;
        request.billingProvince = receipt.province;
        request.billingTaxCode = receipt.taxCode;

        WGCommitOrderDeliverTime time = mData.deliverTime;
        request.deliverDate = time.currentDateId;
        request.deliverTime = time.currentTimeId;

        WGCommitOrderPay pay = mData.payMothod;
        request.payMethod = pay.currentPayId;

        request.comments = mData.remark;

        this.postAsyn(request, WGCommitOrderResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessCommitOrder((WGCommitOrderResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessCommitOrder(WGCommitOrderResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            if (JHStringUtils.isNullOrEmpty(response.data.action)) {
                if (JHStringUtils.isNullOrEmpty(response.data.orderId)) {
                    Log.e("==commitorder==", "返回的 orderId 为空");
//                    //[self showWarningMessage:response.message onCompletion:^(void) {
//                                [weakSelf.navigationController popToRootViewControllerAnimated:YES];
//                        }];
                }
                else {
                    Log.e("==commitorder==", "跳转到支付成功页面");
                    //PaySuccessViewController
                }
            }
            else {
                //PayWeb
                Log.e("==commitorder==", "跳转到支付Web页面");
            }
        }
        else if (response.overWeight()) {
            loadOverHeightDetail();
        }
        else if (response.hasExpireGood()) {
            loadUpdateTimeRequest();
        }
        else if (response.belowMinPrice()) {
            showWarning(response.message);
        }
        else {
            showWarning(response.message);
        }
    }

    void loadOverHeightDetail() {
        WGOverHeightRequest request = new WGOverHeightRequest();
        request.addressId = "" + mData.address.addressId;
        this.postAsyn(request, WGOverHeightResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessOverHeightDetail((WGOverHeightResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessOverHeightDetail(WGOverHeightResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            if (response.data != null) {
                mData.deliverTime.resetWithTimes(response.data.deliverTimes);
                mData.minPriceTips = response.data.minPriceTips;
                mData.consumePrice = response.data.price;
                refreshUI();
                WGOrderExpireGood expireGood = response.data.expireGood;
                if (expireGood != null) {
                    expireGood.canChangeTime = false;
                    showExpireGood(expireGood);
                }
                else {
                    if (response.data.overWeight != null && response.data.overWeight.size() > 0) {
                        showOverWeightGood(response.data.overWeight);
                    }
                }
            }
        }
        else {
            showWarning(response.message);
        }
    }

    void loadDeleteOverHeight() {
        WGOverHeightDeleteRequest request = new WGOverHeightDeleteRequest();
        this.postAsyn(request, WGOverHeightDeleteResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessDeleteOverHeight((WGOverHeightDeleteResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessDeleteOverHeight(WGOverHeightDeleteResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            loadSettlementResultDetail();
            mOverWeightView.dismiss();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadOverHeightReset(List<WGOverHeightDetail> list) {
        WGOverHeightResetRequest request = new WGOverHeightResetRequest();
        StringBuilder idBuilder = new StringBuilder();
        StringBuilder countBuilder = new StringBuilder();
        WGOverHeightDetail detail = list.get(0);
        for (WGOverHeightGoodItem item : detail.goods) {
            idBuilder.append(item.shopCartId);
            idBuilder.append(",");
            countBuilder.append(item.goodCount);
            countBuilder.append(",");
        }
        if (idBuilder.length() > 0) {
            idBuilder.deleteCharAt(idBuilder.length()-1);
            countBuilder.deleteCharAt(idBuilder.length()-1);
        }
        request.goodIds = idBuilder.toString();
        request.goodCounts = countBuilder.toString();
        this.postAsyn(request, WGOverHeightResetResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessOverHeightReset((WGOverHeightResetResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessOverHeightReset(WGOverHeightResetResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            loadSettlementResultDetail();
            mOverWeightView.dismiss();
        }
        else {
            showWarning(response.message);
        }
    }
}
