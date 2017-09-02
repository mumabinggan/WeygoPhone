package com.weygo.weygophone.pages.search.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/9/2.
 */

public class WGHotSearchRequest extends WGRequest {
    public String api() {
        return "catalogSearch/getrecommendsearchwrods?";
    }
}
