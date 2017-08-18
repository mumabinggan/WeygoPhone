package com.weygo.weygophone.base;

import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.common.WGApplicationUserUtils;

/**
 * Created by muma on 2017/4/27.
 */

public class WGGuestRequest extends WGRequest {
    String sessionKey = WGApplicationUserUtils.getInstance().sessionKey();
}
