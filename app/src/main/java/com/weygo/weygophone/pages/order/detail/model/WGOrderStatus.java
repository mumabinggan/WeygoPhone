package com.weygo.weygophone.pages.order.detail.model;

import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderStatus extends WGObject {

    public List<WGOrderStatusItem> statusArray;

    public int currentStatus;
}
