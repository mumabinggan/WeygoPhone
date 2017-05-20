package com.weygo.weygophone.pages.collection.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/5/19.
 */

public class WGCancelCollectionGoodRequest extends WGGuestRequest {

    public long id;     //favoriteId

    public String api() {
        return "wishlist/delete?";
    }
}
