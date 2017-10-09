package com.weygo.weygophone.pages.base.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/9/23.
 */

public class WGUpdateRequest extends WGRequest {
    public String api() {
        return "apps/getversioninfo?";
    }
}
