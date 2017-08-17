package com.weygo.weygophone.pages.common.model.request;

import com.weygo.weygophone.base.WGRequest;
import com.weygo.weygophone.common.WGApplicationUserUtils;

/**
 * Created by muma on 2017/8/17.
 */

public class WGSetPostCodeRequest extends WGRequest {

    public String cap;

    public String api() {
        return "customer/saveCap?";
    }

}
