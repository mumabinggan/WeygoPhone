package com.weygo.weygophone.pages.coupon.model;

import com.weygo.weygophone.base.WGObject;

/**
 * Created by muma on 2017/8/19.
 */

public class WGCoupon extends WGObject {

    public long id;

    public long totalCount;

    public long residueCount;

    public String price;

    public String name;

    public String briefDescription;

    public String couponCode;

    public String time;

    //for self use
    public boolean isSelected;
}
