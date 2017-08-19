package com.weygo.weygophone.pages.order.commit.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/8/19.
 */

public class WGOverHeightResetRequest extends WGGuestRequest {

    public String goodIds;

    public String goodCounts;

    public String api() {
        return "checkout/updateOverHeightProducts?";
    }

}
