package com.weygo.weygophone.pages.order.commit.model.response;

import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderData;

/**
 * Created by muma on 2017/8/19.
 */

public class WGCommitOrderResponse extends WGResponse {

    public WGCommitOrderData data;

    public boolean overWeight() {
        return code == 3;
    }

    public boolean belowMinPrice() {
        return code == 4;
    }

    public boolean hasExpireGood() {
        return code == 5;
    }
}
