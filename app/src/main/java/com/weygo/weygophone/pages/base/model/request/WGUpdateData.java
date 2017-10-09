package com.weygo.weygophone.pages.base.model.request;

import com.weygo.weygophone.base.WGObject;

/**
 * Created by muma on 2017/9/23.
 */

public class WGUpdateData extends WGObject {

    public int versionStatus;

    public String versionCode;

    public String updateTips;

    public String linkUrl;

    public boolean isForce;
}
