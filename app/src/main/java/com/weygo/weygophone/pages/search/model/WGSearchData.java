package com.weygo.weygophone.pages.search.model;

import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/9/2.
 */

public class WGSearchData extends WGObject {

    public List<WGHomeFloorContentGoodItem> goods;

    public String name;

    public List<WGSearchKeywordItem> keywords;

    //for self use
    public boolean isGrid;

}
