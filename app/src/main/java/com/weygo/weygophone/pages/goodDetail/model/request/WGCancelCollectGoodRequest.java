package com.weygo.weygophone.pages.goodDetail.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/6/3.
 */

public class WGCancelCollectGoodRequest extends WGGuestRequest {

    public long id;

    public String api() {
        return "wishlist/delete?";
    }
}
