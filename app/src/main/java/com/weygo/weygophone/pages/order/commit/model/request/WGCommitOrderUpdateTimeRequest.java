package com.weygo.weygophone.pages.order.commit.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/8/19.
 */

public class WGCommitOrderUpdateTimeRequest extends WGGuestRequest {
    public String timeId;

    public String api() {
        return "checkout/updateDeliverTimesEvent?";
    }
}
