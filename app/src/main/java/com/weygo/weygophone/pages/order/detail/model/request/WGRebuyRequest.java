package com.weygo.weygophone.pages.order.detail.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/9/3.
 */

public class WGRebuyRequest extends WGGuestRequest {
    public long orderId;
    public String api() {
        return "orders/rebuy?";
    }
}
