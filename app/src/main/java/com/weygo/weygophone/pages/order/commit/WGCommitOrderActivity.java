package com.weygo.weygophone.pages.order.commit;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDeliverTime;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDetail;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderPay;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightDetail;
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
import com.weygo.weygophone.pages.receipt.model.WGReceipt;

import java.util.List;

/**
 * Created by muma on 2017/5/22.
 */

public class WGCommitOrderActivity extends WGTitleActivity {

    WGCommitOrderDetail mData;


    void refreshUI() {

    }

    void loadSettlementResultDetail() {
        WGSettlementResultRequest request = new WGSettlementResultRequest();
        request.addressId = mData.address.addressId;
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

        }
        else {
            showWarning(response.message);
        }
    }

    void loadOverHeightReset(List<WGOverHeightDetail> list) {
        WGOverHeightResetRequest request = new WGOverHeightResetRequest();
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

        }
        else {
            showWarning(response.message);
        }
    }
}
