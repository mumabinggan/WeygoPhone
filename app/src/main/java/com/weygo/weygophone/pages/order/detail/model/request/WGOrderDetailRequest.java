package com.weygo.weygophone.pages.order.detail.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/9/3.
 */

public class WGOrderDetailRequest extends WGGuestRequest {
    public long id;
    public String api() {
        return "orders/detail?";
    }
}
