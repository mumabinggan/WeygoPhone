package com.weygo.weygophone.pages.pay.paySuccess.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/8/27.
 */

public class WGPaySuccessRequest extends WGGuestRequest {
    public String api() {
        return "checkout/success?";
    }
}
