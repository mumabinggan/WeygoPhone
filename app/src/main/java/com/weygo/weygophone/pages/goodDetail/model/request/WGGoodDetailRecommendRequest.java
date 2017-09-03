package com.weygo.weygophone.pages.goodDetail.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/9/3.
 */

public class WGGoodDetailRecommendRequest extends WGRequest {
    public long goodId;

    public String api() {
        return "products/getRecommendProducts?";
    }
}
