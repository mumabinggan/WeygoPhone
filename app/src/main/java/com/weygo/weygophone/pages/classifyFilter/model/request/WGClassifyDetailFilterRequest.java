package com.weygo.weygophone.pages.classifyFilter.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/8/12.
 */

public class WGClassifyDetailFilterRequest extends WGRequest {

    public long classifyId;

    public String api() {
        return "products/getFilter?";
    }

}
