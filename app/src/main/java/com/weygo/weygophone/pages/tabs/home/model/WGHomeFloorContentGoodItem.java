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

    public String specification;        //规格，在首页会grid 里面和分类详情 grid 模式下会显示

    public String discount;

    public long favoriteId;

    public int inStock;     //有无货(1,有货;0,无货) ;

    public String expiredTime;

    public boolean hasDiscount() {
        return !JHStringUtils.isNullOrEmpty(discount);
    }
}
