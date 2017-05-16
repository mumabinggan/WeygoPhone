package com.weygo.weygophone.pages.tabs.classify.model;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/4/25.
 */

public class WGClassifyItem extends WGObject {

    public long id;

    public String name;

    public String pictureURL;

    public List<WGClassifyItem> subArray;

    public List<WGClassifyHotGoodItem> goodArray;

    //for self use
    public boolean isSelected;

    //for self use
    public List<WGClassifyItem> allArray() {
        WGClassifyItem totalItem = new WGClassifyItem();
        totalItem.id = id;
        totalItem.name = JHResourceUtils.getInstance().getString(R.string.Slider_All);
        List array = new ArrayList();
        array.add(totalItem);
        array.addAll(subArray);
        return array;
    }

}
