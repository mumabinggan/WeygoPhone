package com.weygo.weygophone.pages.shopcart.model;

import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/8/18.
 */

public class WGShopCart extends WGObject {

    public WGShopCartPrice shopCartPrice;

    public List<WGShopCartGoodItem> goods;

    public String deliverPriceDescription;

    public String minPriceTips;

    public boolean hasInvalidGood() {
        for (WGShopCartGoodItem item : goods) {
            if (item.inStock == 0) {
                return true;
            }
        }
        return false;
    }

    public boolean moreThanMinPrice() {
        return JHStringUtils.isNullOrEmpty(minPriceTips);
    }
}
