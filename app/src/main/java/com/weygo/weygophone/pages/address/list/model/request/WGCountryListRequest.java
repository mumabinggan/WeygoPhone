package com.weygo.weygophone.pages.address.list.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/8/22.
 */

public class WGCountryListRequest extends WGGuestRequest {
    public String api() {
        return "apps/getCountries?";
    }
}
