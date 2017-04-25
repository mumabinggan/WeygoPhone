package com.weygo.weygophone.pages.tabs.classify.Model;

import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/4/25.
 */

public class WGClassifyItem extends WGObject {

    public long id;

    public String name;

    public String pictureURL;

    public List<WGClassifyItem> subArray;
}
