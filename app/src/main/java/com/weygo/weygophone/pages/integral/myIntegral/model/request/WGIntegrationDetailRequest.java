package com.weygo.weygophone.pages.integral.myIntegral.model.request;

import com.weygo.weygophone.base.WGGuestPageRequest;
import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/5/21.
 */

public class WGIntegrationDetailRequest extends WGGuestPageRequest {

    public String api() {
        return "customer/points?";
    }

}
