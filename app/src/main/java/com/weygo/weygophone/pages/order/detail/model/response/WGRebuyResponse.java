package com.weygo.weygophone.pages.order.detail.model.response;

import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.pages.order.detail.model.WGAddGoodToCartData;

/**
 * Created by muma on 2017/9/3.
 */

public class WGRebuyResponse extends WGResponse {
    public WGAddGoodToCartData data;

    public boolean outStock() {
        return this.code == 2;
    }
}
