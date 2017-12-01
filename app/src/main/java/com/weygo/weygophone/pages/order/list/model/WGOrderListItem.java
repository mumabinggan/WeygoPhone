package com.weygo.weygophone.pages.order.list.model;

import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListItem extends WGObject {

    public long id;

    public String sn;

    public List<WGOrderGoodItem> goods;

    public String status;

    public String deliverPrice;

    public String totalPrice;

    public boolean hasGoods() {
        if (goods != null && goods.size() > 0) {
            return true;
        }
        return false;
    }

    //for self use
    int goodsCount = 0;
    public int getGoodsCount() {
        if (goodsCount != 0) {
            return goodsCount;
        }
        for (WGOrderGoodItem item : goods) {
            goodsCount += item.goodCount;
        }
        return goodsCount;
    }
}
