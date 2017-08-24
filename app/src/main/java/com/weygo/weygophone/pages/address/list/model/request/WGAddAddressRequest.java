package com.weygo.weygophone.pages.address.list.model.request;

import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/8/22.
 */

public class WGAddAddressRequest extends WGGuestRequest {

    public long id;

    public String firstname;

    public String lastname;

    public String address;

    public String street;

    public String city;

    public String getCity() {
        if (JHStringUtils.isNullOrEmpty(city)) {
            return "MILANO";
        }
        return city;
    }

    public String countryId = "IT";

    public String cap;

    public String telephone;

    public String floor;

    public String doorbell;

    public int hasLift;                 //是否是电梯(1：是； 0：不是)

    public String floorHole;

    public String api() {
        return "customer/postAddress?";
    }
}
