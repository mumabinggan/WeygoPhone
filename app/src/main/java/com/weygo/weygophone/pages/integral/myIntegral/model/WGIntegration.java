package com.weygo.weygophone.pages.integral.myIntegral.model;

import com.weygo.weygophone.base.WGObject;

/**
 * Created by muma on 2017/5/20.
 */

public class WGIntegration extends WGObject {

    public long totalCount;

    public long currentCanUseCount;

    public String money;

    public String tip;

    public int isSelected;

    //for self use
    public boolean hasUsed() {
        return (isSelected == 1);
    }
}
