package com.weygo.weygophone.pages.shopcart.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/8/18.
 */

public class WGShopCartGiftRequest extends WGGuestRequest {
    public String api() {
        return "cart/giftslist?";
    }
}
