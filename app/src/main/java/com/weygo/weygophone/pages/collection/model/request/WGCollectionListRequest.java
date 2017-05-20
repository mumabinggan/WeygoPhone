package com.weygo.weygophone.pages.collection.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/5/19.
 */

public class WGCollectionListRequest extends WGGuestRequest {
    public String api() {
        return "wishlist/list?";
    }
}
