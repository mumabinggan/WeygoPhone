package com.weygo.weygophone.pages.clientCenter.list.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/5/17.
 */

public class WGClientServiceRequest extends WGGuestRequest {

    public String api() {
        return "customer/customerService?";
    }

}
