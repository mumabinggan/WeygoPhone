package com.weygo.weygophone.pages.search.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/9/2.
 */

public class WGSearchRequest extends WGRequest {
    public String name;

    public int pageId;

    public int pageSize = 10;

    public String api() {
        return "catalogSearch/index?";
    }
}
