package com.weygo.weygophone.pages.tabs.home.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/7/2.
 */

public class WGHomeTabContentRequest extends WGRequest {

    public long menuId;

    public String api() {
        return "pages/content?";
    }

}
