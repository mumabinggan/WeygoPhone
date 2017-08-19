package com.weygo.weygophone.pages.order.commit.model;

import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/8/19.
 */

public class WGAddressBussiness extends WGObject {

    public List<WGOverHeightDetail> overWeight;

    public List<WGSettlementDate> deliverTimes;

    public String minPriceTips;

    public WGSettlementConsumePrice price;

    public WGOrderExpireGood expireGood;
}
