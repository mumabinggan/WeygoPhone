package com.weygo.weygophone.pages.order.commit.model;

import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/8/19.
 */

public class WGSettlementResult extends WGObject {

    public long id;

    public WGAddress address;

    public WGCoupon coupon;

    public List<WGSettlementDate> deliverTimes;

    public List<WGSettlementPayMethod> payMethods;

    public WGSettlementConsumePrice price;

    public int useIntegral;        //1， 使用 0：不使用
    public long integral;
    public String integralDescription;

    public List<WGOrderGoodItem> goods;

    public WGSettlementTips tip;

    public long goodsCount;
    public long getGoodsCount() {
        if (goodsCount == 0) {
            long count = 0;
            for (int num = 0; num < goods.size(); ++num) {
                WGOrderGoodItem item = goods.get(num);
                count += item.goodCount;
            }
            goodsCount = count;
        }
        return goodsCount;
    }

    public List<WGOverHeightDetail> overHeightDetail;

    public WGOrderExpireGood expireGood;

    public String minPriceTips;
}
