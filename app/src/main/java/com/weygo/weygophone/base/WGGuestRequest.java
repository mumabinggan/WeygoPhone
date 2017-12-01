package com.weygo.weygophone.base;

import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.common.WGApplicationUserUtils;

/**
 * Created by muma on 2017/4/27.
 */

public class WGGuestRequest extends WGRequest {
    public String sessionKey = WGApplicationUserUtils.getInstance().sessionKey();
    //public String sessionKey = "app_d6eaeb1f7aec39172d6f491250a91ce0";
}
