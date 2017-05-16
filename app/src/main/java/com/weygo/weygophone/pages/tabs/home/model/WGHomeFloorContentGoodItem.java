package com.weygo.weygophone.pages.tabs.home.model;

import com.weygo.common.tools.JHStringUtils;

/**
 * Created by muma on 2017/5/15.
 */

public class WGHomeFloorContentGoodItem extends WGHomeFloorBaseContentItem {

    public String chineseName;

    public String briefDescription;

    public String price;

    public String reducePrice;

    public String currentPrice;

    public String specification;

    public String discount;

    public long favoriteId;

    public int inStock;     //有无货(1,有货;0,无货) ;

    public boolean hasDiscount() {
        return !JHStringUtils.isNullOrEmpty(discount);
    }
}
