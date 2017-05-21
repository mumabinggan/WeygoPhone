package com.weygo.weygophone.pages.integral.useIntegral.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/5/21.
 */

public class WGUseIntegrationRequest extends WGGuestRequest {

    public int remove; //1, 取消  0, 使用

    public String api() {
        return "checkout/reward?";
    }
}
