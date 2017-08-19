package com.weygo.weygophone.pages.order.commit.model;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.receipt.model.WGReceipt;

import java.util.List;

/**
 * Created by muma on 2017/8/19.
 */

public class WGCommitOrderDetail extends WGObject {

    public long id;

    public WGAddress address;

    public WGReceipt receipt;

    public WGCoupon coupon;

    public String remark;

    public int useIntegration;

    public long integration;

    //some from settlement
    public WGCommitOrderDeliverTime deliverTime;

    //some from settlement
    public WGCommitOrderPay payMothod;

    //from settlement
    public WGSettlementConsumePrice consumePrice;

    //from settlement
    public WGSettlementTips tip;

    public List<WGOrderGoodItem> goods;

    public String minPriceTips;

    String useIntegrationString;
    public String getUseIntegrationString() {
        return JHResourceUtils.getInstance().getString(
                (useIntegration == 1) ? R.string.CommitOrder_Use_Score_YES :
                        R.string.CommitOrder_No_Use_Score);
    }

    public WGCommitOrderDetail initWithSettlementResult(WGSettlementResult settlement) {
        //地址
        address = settlement.address;

        //时间
        deliverTime = new WGCommitOrderDeliverTime();
        deliverTime.deliverTimes = settlement.deliverTimes;

        //goods
        goods = settlement.goods;

        //payMethods
        WGCommitOrderPay pay = new WGCommitOrderPay();
        pay.payMethods = settlement.payMethods;
        payMothod = pay;

        //consumePrice
        consumePrice = settlement.price;

        //tips
        tip = settlement.tip;

        coupon = settlement.coupon;

        integration = settlement.integral;

        useIntegration = settlement.useIntegral;

        minPriceTips = settlement.minPriceTips;

        return this;
    }
}
