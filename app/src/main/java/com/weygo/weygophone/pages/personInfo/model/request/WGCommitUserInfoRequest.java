package com.weygo.weygophone.pages.personInfo.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/11/21.
 */

public class WGCommitUserInfoRequest extends WGGuestRequest {

    public String firstName;

    public String lastName;

    public String phone;

    public String email;

    public int sex;     //1:男 2：女

    public String birth;    //1990-12-23

    public String tax;

    public String api() {
        return "customer/savecustomerInfo?";
    }
}
