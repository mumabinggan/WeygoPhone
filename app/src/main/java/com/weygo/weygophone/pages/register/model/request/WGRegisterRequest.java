package com.weygo.weygophone.pages.register.model.request;

import com.weygo.weygophone.base.WGRequest;
import com.weygo.weygophone.common.WGApplicationUserUtils;

/**
 * Created by muma on 2017/5/17.
 */

public class WGRegisterRequest extends WGRequest {

    public String username;

    public String password;

    public String cap;

    public String verifyCode;

    public String confirmPassword;

    public String firstname;

    public String lastname;

    public String email;

    public String countryCode;      ////国家code(+39默认, +86)

    public WGRegisterRequest() {
        super();
        if (WGApplicationUserUtils.getInstance().isLogined()) {
            cap = WGApplicationUserUtils.getInstance().currentPostCode();
            countryCode = "+39";
        }
    }

    public String api() {
        return "customer/register?";
    }

}
