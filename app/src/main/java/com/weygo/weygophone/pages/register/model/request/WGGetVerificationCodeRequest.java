package com.weygo.weygophone.pages.register.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/5/17.
 */

public class WGGetVerificationCodeRequest extends WGRequest {

    public String username;

    public String countryCode = "+39";
}
