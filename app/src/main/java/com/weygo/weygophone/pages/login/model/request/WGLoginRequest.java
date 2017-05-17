package com.weygo.weygophone.pages.login.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/5/17.
 */

public class WGLoginRequest extends WGRequest {

    public String username;

    public String password;

    public String api() {
        return "customer/login?";
    }
}
