package com.weygo.weygophone.base;

/**
 * Created by muma on 2017/5/20.
 */

public class WGGuestPageRequest extends WGGuestRequest {
    public int pageId;

    public int pageSize;

    public WGGuestPageRequest() {
        super();
        pageSize = 15;
    }
}
