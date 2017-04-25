package com.weygo.weygophone.pages.tabs.classify.Model.Request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/4/25.
 */

public class WGClassifyRequest extends WGRequest {

    public int is_hot;

    public String api() {
        return "catalogSearch/getCategories?";
    }
}
