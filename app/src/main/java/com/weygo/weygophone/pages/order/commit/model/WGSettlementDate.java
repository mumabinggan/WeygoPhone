package com.weygo.weygophone.pages.order.commit.model;

import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/8/19.
 */

public class WGSettlementDate extends WGObject {

    public String id;

    public String date;

    public String week;

    public List<WGSettlementTime> times;
}
