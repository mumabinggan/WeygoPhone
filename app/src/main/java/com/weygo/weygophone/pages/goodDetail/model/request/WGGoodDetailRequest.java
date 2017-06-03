package com.weygo.weygophone.pages.goodDetail.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/6/3.
 */

public class WGGoodDetailRequest extends WGRequest {

    public long id;

    public String api() {
        return "products/detail?";
    }

}
