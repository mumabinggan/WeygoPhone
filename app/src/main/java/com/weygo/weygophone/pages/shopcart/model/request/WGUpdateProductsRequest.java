package com.weygo.weygophone.pages.shopcart.model.request;

import com.weygo.weygophone.base.WGGuestRequest;
import com.weygo.weygophone.common.WGApplicationUserUtils;

/**
 * Created by muma on 2017/8/18.
 */

public class WGUpdateProductsRequest extends WGGuestRequest {

    public long id;

    public long count;

    public String cap = WGApplicationUserUtils.getInstance().currentPostCode();

    public String api() {
        return "cart/updateQty?";
    }
}
