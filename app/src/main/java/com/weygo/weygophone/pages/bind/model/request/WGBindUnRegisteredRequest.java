package com.weygo.weygophone.pages.bind.model.request;

import com.weygo.weygophone.base.WGRequest;
import com.weygo.weygophone.pages.register.model.request.WGRegisterRequest;

/**
 * Created by muma on 2017/10/11.
 */

public class WGBindUnRegisteredRequest extends WGRegisterRequest {

    public String uniqueId;

    public int type;

    public String api() {
        return "customer/bindcustomer?";
    }
}
