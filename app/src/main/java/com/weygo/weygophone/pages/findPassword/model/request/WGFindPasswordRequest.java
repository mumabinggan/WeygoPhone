package com.weygo.weygophone.pages.findPassword.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/5/18.
 */

public class WGFindPasswordRequest extends WGRequest {

    public String username;

    public String verifyCode;

    public String password;

    public String confirmPassword;

    public String api() {
        return "customer/changePassword?";
    }
}
