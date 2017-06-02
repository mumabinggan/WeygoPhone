package com.weygo.weygophone.pages.goodDetail.model;

import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/5/30.
 */

public class WGGoodDetail extends WGObject {

    public long id;

    public List<WGCarouselFigureItem> carouselFigures;

    public String name;

    public String currentPrice;

    public String price;

    public String specification;

    public String expiredTime;

    public int inStock;

    public List<WGGoodDetailDesItem> productDes;

    public String purchaseTip;      //购买需知

    public String deliveryInfo;     //送货需知

    public String productInfo;      //商品描述

    public List<WGHomeFloorContentGoodItem> recommendProduce;

    public long hasFavorited;   //0为没有收藏，>0为收藏并且为收藏 id

    //for self use
    public List<String> carouselFiguresPictureArray() {
        return null;
    }

    //for self use
    public long favoritedId;

    //for self use
    public int selectedIndex;       //详情页面描述选择 tab

    public static class WGGoodDetailDesItem extends WGObject {

        public String name;

        public String value;
    }
}
