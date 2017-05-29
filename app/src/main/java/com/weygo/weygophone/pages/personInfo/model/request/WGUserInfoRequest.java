package com.weygo.weygophone.pages.personInfo.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/5/29.
 */

public class WGUserInfoRequest extends WGGuestRequest {

    public String api() {
        return "customer/accountinfo?";
    }

}
