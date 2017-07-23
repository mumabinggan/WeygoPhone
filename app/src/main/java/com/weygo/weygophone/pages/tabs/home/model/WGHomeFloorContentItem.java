package com.weygo.weygophone.pages.tabs.home.model;

import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.common.WGConstants;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeFloorContentItem extends WGObject {

    public long id;

    public String name;

    public String chineseName;

    public String pictureURL;

    public String briefDescription;

    public String price;

    public String reducePrice;

    public String currentPrice;

    public String specification;

    public String expiredTime;

    public String discount;

    public int jumpType;

    public int inStock;

    //for self use
    WGHomeFloorBaseContentItem mContentItem;
    public WGHomeFloorBaseContentItem contentItemWithType(int type) {
        if (mContentItem != null) {
            return mContentItem;
        }
        if (type == WGConstants.WGHomeFloorItemTypeCountryColumn) {
            WGHomeFloorContentCountryItem countryItem = new WGHomeFloorContentCountryItem();
            countryItem.id = id;
            countryItem.pictureURL = pictureURL;
            countryItem.name = name;
            mContentItem = countryItem;
        }
        else if (type == WGConstants.WGHomeFloorItemTypeGoodList ||
                type == WGConstants.WGHomeFloorItemTypeGoodColumn ||
                type == WGConstants.WGHomeFloorItemTypeGoodGrid) {
            WGHomeFloorContentGoodItem goodItem = new WGHomeFloorContentGoodItem();
            goodItem.chineseName = chineseName;
            goodItem.briefDescription = briefDescription;
            goodItem.price = price;
            goodItem.reducePrice = reducePrice;
            goodItem.currentPrice = currentPrice;
            goodItem.discount = discount;
            goodItem.inStock = inStock;
            goodItem.specification = specification;
            goodItem.expiredTime = expiredTime;
            goodItem.id = id;
            goodItem.pictureURL = pictureURL;
            goodItem.name = name;
            mContentItem = goodItem;
        }
        else if (type == WGConstants.WGHomeFloorItemTypeClassifyList ||
                type == WGConstants.WGHomeFloorItemTypeClassifyColumn ||
                type == WGConstants.WGHomeFloorItemTypeClassifyGrid) {
            WGHomeFloorContentClassifyItem classifyItem = new WGHomeFloorContentClassifyItem();
            classifyItem.id = id;
            classifyItem.pictureURL = pictureURL;
            classifyItem.name = name;
            mContentItem = classifyItem;
        }
        return mContentItem;
    }
}
