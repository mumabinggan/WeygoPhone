package com.weygo.weygophone.pages.bind.model.request;

import com.weygo.weygophone.pages.login.model.request.WGLoginRequest;

/**
 * Created by muma on 2017/10/11.
 */

public class WGBindRegisteredRequest extends WGLoginRequest {

    public String uniqueId;

    public int type;

    public String account;

    public String api() {
        return "customer/bindcustomer?";
    }
}
