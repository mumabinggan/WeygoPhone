package com.weygo.weygophone.pages.goodDetail.model.request;

import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.base.WGGuestRequest;
import com.weygo.weygophone.base.WGRequest;
import com.weygo.weygophone.common.WGApplicationUserUtils;

/**
 * Created by muma on 2017/9/3.
 */

public class WGAddGoodToCartRequest extends WGGuestRequest {

    public long goodId;

    public long count;

    public String cap;

    public WGAddGoodToCartRequest() {
        super();
        if (WGApplicationUserUtils.getInstance().isLogined() &&
                JHStringUtils.isNullOrEmpty(WGApplicationUserUtils.getInstance().currentPostCode())) {
            cap = WGApplicationUserUtils.getInstance().currentPostCode();
        }
    }

    public String api() {
        return "cart/add?";
    }


}
