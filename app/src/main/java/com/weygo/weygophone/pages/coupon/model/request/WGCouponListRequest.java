package com.weygo.weygophone.pages.coupon.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/8/25.
 */

public class WGCouponListRequest extends WGGuestRequest {
    public String api() {
        return "customer/getCoupons?";
    }
}
