package com.weygo.weygophone.pages.classifyDetail.model.request;

import com.weygo.weygophone.base.WGRequest;

/**
 * Created by muma on 2017/8/2.
 */

public class WGClassifyDetailRequest extends WGRequest {

    public long classifyId;

    public String brandIds;

    public String subClassifyIds;

    public int pageId;

    public int pageSize;

    public int order;    //0:默认, 1:品牌 2：价格从低到高 3:价格从高到低

    public String api() {
        return "products/categoryProductlist?";
    }
}
