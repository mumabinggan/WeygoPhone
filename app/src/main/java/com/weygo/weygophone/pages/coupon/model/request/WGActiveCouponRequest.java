package com.weygo.weygophone.pages.coupon.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/8/25.
 */

public class WGActiveCouponRequest extends WGGuestRequest {

    public String couponCode;

    public int remove;          //0：添加优惠券; 1: 取消优惠券

    public long couponId;

    public String api() {
        return "checkout/discount?";
    }
}
