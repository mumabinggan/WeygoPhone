package com.weygo.weygophone.pages.order.commit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGIntegrationHelpView;
import com.weygo.weygophone.common.widget.WGOptionPickerView;
import com.weygo.weygophone.common.widget.WGWeightFloatView;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.address.list.WGAddressListActivity;
import com.weygo.weygophone.pages.address.list.model.WGAddressListData;
import com.weygo.weygophone.pages.coupon.WGCouponListActivity;
import com.weygo.weygophone.pages.coupon.model.WGActiveCouponData;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.integral.useIntegral.WGUseIntegrationActivity;
import com.weygo.weygophone.pages.integral.useIntegral.model.response.WGUseIntegrationData;
import com.weygo.weygophone.pages.order.commit.adapter.WGCommitOrderAdapter;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDeliverTime;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDetail;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderPay;
import com.weygo.weygophone.pages.order.commit.model.WGOrderExpireGood;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightDetail;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightGoodItem;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementDate;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementPayMethod;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementTime;
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
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.pay.paySuccess.WGPaySuccessActivity;
import com.weygo.weygophone.pages.pay.paySuccess.model.WGPaySuccessData;
import com.weygo.weygophone.pages.pay.payWeb.WGPayWebActivity;
import com.weygo.weygophone.pages.receipt.WGReceiptActivity;
import com.weygo.weygophone.pages.receipt.model.WGReceipt;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

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

    WGWeightFloatView mWeightFloatPopView;

    JHBasePopupWindow mWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSettlementResultDetail();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (resultCode == WGConstants.WGCommitOrderReceiptCommitReturn) {
                    WGReceipt receipt = (WGReceipt) bundle.getSerializable(WGConstants.WGIntentDataKey);
                    mData.receipt = receipt;
                    refreshUI();
                }
                else if (resultCode == WGConstants.WGCommitOrderCouponReturn) {
                    WGActiveCouponData couponData = (WGActiveCouponData) bundle.getSerializable(WGConstants.WGIntentDataKey);
                    mData.coupon = couponData.coupon;
                    mData.consumePrice = couponData.price;
                    refreshUI();
                }
                else if (resultCode == WGConstants.WGCommitOrderAddressReturn) {
                    WGAddress address = (WGAddress) bundle.getSerializable(WGConstants.WGIntentDataKey);
                    mData.address = address;
                    loadOverHeightDetail();
                }
                else if (resultCode == WGConstants.WGCommitOrderIntegralReturn) {
                    WGUseIntegrationData integral = (WGUseIntegrationData) bundle.getSerializable(WGConstants.WGIntentDataKey);
                    mData.useIntegration = integral.use;
                    mData.consumePrice = integral.price;
                    refreshUI();
                }
            }
            else {
                if (resultCode == WGConstants.WGCommitOrderReceiptCancelReturn) {
                    mData.receipt = null;
                    refreshUI();
                }
            }
        }
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
        mAdapter.setListener(new WGCommitOrderAdapter.OnItemListener() {
            @Override
            public void onAddress(WGAddress address) {
                handleAddress();
            }

            @Override
            public void onReceipt(WGReceipt receipt) {
                handleReceipt();
            }

            @Override
            public void onDeliverDate(WGCommitOrderDeliverTime data) {
                handleDeliverDate();
            }

            @Override
            public void onDeliverTime(WGCommitOrderDeliverTime data) {
                handleDeliverTime();
            }

            @Override
            public void onPayMethod(WGCommitOrderPay data) {
                handlePayMethod();
            }

            @Override
            public void onIntegral(WGCommitOrderDetail data) {
                handleIntegral();
            }

            @Override
            public void onCoupon(WGCoupon data) {
                handleCoupon();
            }

            @Override
            public void onGoodItem(WGOrderGoodItem item) {
                handleGood(item);
            }

            @Override
            public void onLookMore(WGCommitOrderDetail data) {
                handleLookMore();
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
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

    public void touchCommitBtn() {
        if (enableConfirm()) {
            loadCommitOrder();
        }
    }

    boolean enableConfirm() {
        if (mData != null && JHStringUtils.isNullOrEmpty(mData.minPriceTips)) {
            return true;
        }
        return false;
    }

    void showExpireGood(WGOrderExpireGood expireGood) {
        mExpireView = (WGCommitOrderExpirePopView) getLayoutInflater()
                .inflate(R.layout.commitorder_expire_good_pop, null);
        mExpireView.setGoods(expireGood.goods);
        mWindow = new JHBasePopupWindow(mExpireView,
                JHAdaptScreenUtils.devicePixelWidth(this),
                JHAdaptScreenUtils.devicePixelHeight(this));
        mExpireView.setPopupWindow(mWindow);
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
        mWindow.setFocusable(false);
        mWindow.setOutsideTouchable(false);
        mWindow.showAtLocation(mExpireView, Gravity.CENTER, 0, 0);
    }

    void showOverWeightGood(List<WGOverHeightDetail> overHeightDetail) {
        mOverWeightView = (WGCommitOrderOverWeightPopView) getLayoutInflater()
                .inflate(R.layout.commitorder_overheight_pop, null);
        mOverWeightView.setGoods(overHeightDetail);
        mWindow = new JHBasePopupWindow(mOverWeightView,
                JHAdaptScreenUtils.devicePixelWidth(this),
                JHAdaptScreenUtils.devicePixelHeight(this));
        mOverWeightView.setPopupWindow(mWindow);
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
        mWindow.setFocusable(false);
        mWindow.setOutsideTouchable(false);
        mWindow.showAtLocation(mOverWeightView, Gravity.CENTER, 0, 0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if(mWindow!=null&&mWindow.isShowing()){
            return false;
        }
        return super.dispatchTouchEvent(event);
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

    void handleAddress() {
        Intent intent = new Intent(WGCommitOrderActivity.this, WGAddressListActivity.class);
        if (mData != null && mData.address != null) {
            Bundle bundle = new Bundle();
            WGAddressListData item = new WGAddressListData();
            item.addressId = mData.address.addressId;
            item.canUse = true;
            bundle.putSerializable(WGConstants.WGIntentDataKey, item);
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, 0);
    }

    void handleDeliverDate() {
        List<String> list = new ArrayList();
        List<WGSettlementDate> times = mData.deliverTime.deliverTimes;
        int index = 0;
        for (int num = 0; num < times.size(); ++num) {
            WGSettlementDate item = times.get(num);
            list.add(item.week + "  " + item.date);
            if (mData.deliverTime.currentDateId != null) {
                if (item.id.equals(mData.deliverTime.currentDateId)) {
                    index = num;
                }
            }
        }
        if (list.size() > 0) {
            WGOptionPickerView picker = new WGOptionPickerView(this, list);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    handleSelectDeliverDate(index, item);
                }
            });
            picker.setSelectedIndex(index);
            picker.show();
        }
    }

    void handleSelectDeliverDate(int index, String title) {
        WGSettlementDate item = mData.deliverTime.deliverTimes.get(index);
        mData.deliverTime.currentDateId = item.id;
        mData.deliverTime.currentTimeId = mData.deliverTime.getDefaultTimeId();
        loadUpdateTimeRequest();
        refreshUI();
    }

    void handleDeliverTime() {
        List<WGSettlementTime> times = mData.deliverTime.getCurrentTimes();
        int index = 0;
        List<String> list = new ArrayList();
        for (int num = 0; num < times.size(); ++num) {
            WGSettlementTime item = times.get(num);
            list.add(item.time);
            if (mData.deliverTime.currentTimeId != null) {
                if (item.id.equals(mData.deliverTime.currentTimeId)) {
                    index = num;
                }
            }
        }
        if (list.size() > 0) {
            WGOptionPickerView picker = new WGOptionPickerView(this, list);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    handleSelectDeliverTime(index, item);
                }
            });
            picker.setSelectedIndex(index);
            picker.show();
        }
    }

    void handleSelectDeliverTime(int index, String title) {
        WGSettlementTime item = mData.deliverTime.getCurrentTimes().get(index);
        mData.deliverTime.currentTimeId = item.id;
        loadUpdateTimeRequest();
        refreshUI();
    }

    void handlePayMethod() {
        int index = 0;
        List<String> list = new ArrayList();
        List<WGSettlementPayMethod> payMethods = mData.payMothod.payMethods;
        for (int num = 0; num < payMethods.size(); ++num) {
            WGSettlementPayMethod item = payMethods.get(num);
            list.add(item.name);
            if (item.id.equals(mData.payMothod.currentPayId)) {
                index = num;
            }
        }
        if (list.size() > 0) {
            WGOptionPickerView picker = new WGOptionPickerView(this, list);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    handleSelectPayMethod(index, item);
                }
            });
            picker.setSelectedIndex(index);
            picker.show();
        }
    }

    void handleSelectPayMethod(int index, String title) {
        WGSettlementPayMethod item = mData.payMothod.payMethods.get(index);
        mData.payMothod.currentPayId = item.id;
        refreshUI();
    }

    void handleIntegral() {
        Intent intent = new Intent(WGCommitOrderActivity.this, WGUseIntegrationActivity.class);
        startActivityForResult(intent, 0);
    }

    void handleLookMore() {
        mWeightFloatPopView = (WGWeightFloatView) getLayoutInflater()
                .inflate(R.layout.commitorder_weight_float_pop, null);
        mWindow = new JHBasePopupWindow(mWeightFloatPopView,
                JHAdaptScreenUtils.devicePixelWidth(this),
                JHAdaptScreenUtils.devicePixelHeight(this));
        mWeightFloatPopView.setPopupWindow(mWindow);
        mWeightFloatPopView.showWithData(mData.tip);
        mWindow.showAtLocation(mWeightFloatPopView, Gravity.CENTER, 0, 0);
    }

    void handleGood(WGOrderGoodItem item) {
        Intent intent = new Intent(this, WGGoodDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void handleCoupon() {
        Intent intent = new Intent(WGCommitOrderActivity.this, WGCouponListActivity.class);
        if (mData != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(WGConstants.WGIntentDataKey1, true);
            if (mData.coupon != null) {
                bundle.putSerializable(WGConstants.WGIntentDataKey, mData.coupon);
            }
            intent.putExtras(bundle);
            startActivityForResult(intent, 0);
        }
    }

    void handleReceipt() {
        Intent intent = new Intent(WGCommitOrderActivity.this, WGReceiptActivity.class);
        if (mData != null && mData.receipt != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(WGConstants.WGIntentDataKey, mData.receipt);
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, 0);
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
                refreshUI();
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
        if (receipt != null) {
            request.useBilling = 1;
            request.billingCompanyName = receipt.companyName;
            request.billingCountry = receipt.countryId;
            request.billingPhone = receipt.phone;
            request.billingAddress = receipt.address;
            request.billingCivico = receipt.civico;
            request.billingCity = receipt.city;
            request.billingCap = receipt.cap;
            request.billingProvince = receipt.province;
            request.billingTaxCode = receipt.taxCode;
        }
        request.useBilling = 0;

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
                    Toast.makeText(getBaseContext(), response.message, Toast.LENGTH_LONG);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
                else {
                    Log.e("==commitorder==", "跳转到支付成功页面");
                    Intent intent = new Intent(WGCommitOrderActivity.this, WGPaySuccessActivity.class);
                    if (response != null && response.data != null) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(WGConstants.WGIntentDataKey, response.data.orderId);
                        intent.putExtras(bundle);
                    }
                    startActivity(intent);
                }
            }
            else {
                //PayWeb
                Intent intent = new Intent(WGCommitOrderActivity.this, WGPayWebActivity.class);
                if (response != null && response.data != null) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(WGConstants.WGIntentDataKey, response.data);
                    intent.putExtras(bundle);
                }
                startActivity(intent);
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
            countBuilder.deleteCharAt(countBuilder.length()-1);
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
