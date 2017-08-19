package com.weygo.weygophone.pages.order.commit.model;

import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/8/19.
 */

public class WGOrderExpireGood extends WGObject {

    public String tips;

    public List<WGHomeFloorContentGoodItem> goods;

    //self use
    public boolean canChangeTime;

}
