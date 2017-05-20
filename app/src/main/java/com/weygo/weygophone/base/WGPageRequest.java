package com.weygo.weygophone.base;

/**
 * Created by muma on 2017/5/20.
 */

public class WGPageRequest extends WGRequest {
    public int pageId;

    public int pageSize;

    public WGPageRequest() {
        super();
        pageSize = 15;
    }
}
