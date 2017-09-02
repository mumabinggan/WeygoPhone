package com.weygo.weygophone.pages.tabs.home.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/9/1.
 */

public class WGLogoutRequest extends WGGuestRequest {
    public String api() {
        return "customer/logout?";
    }
}
