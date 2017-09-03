package com.weygo.weygophone.pages.order.list.model.request;

import com.weygo.weygophone.base.WGGuestPageRequest;
import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListRequest extends WGGuestPageRequest {

    public int type;

    public String api() {
        return "orders/index?";
    }

}
