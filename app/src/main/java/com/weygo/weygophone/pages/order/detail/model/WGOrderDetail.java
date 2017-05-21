package com.weygo.weygophone.pages.order.detail.model;

import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderDetail extends WGObject {

    public int id;

    public String sn;

    public WGOrderStatus status;

    public WGOrderDeliver deliver;

    public List<WGOrderGoodItem> goods;

    public WGOrderFax fax;

    public List<String> pay;
}
