package com.weygo.weygophone.pages.footprint.model.request;

import com.weygo.weygophone.base.WGGuestPageRequest;
import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/5/20.
 */

public class WGFootPrintRequest extends WGGuestPageRequest {
    public String api() {
        return "customer/viewedlist?";
    }
}
