package com.weygo.weygophone.pages.tabs.home.model;

import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeFloorItem extends WGObject {

    public long id;

    public int type;

    public String name;

    public String breifDescription;

    public String pictureURL;

    public String pictureName;

    public String pictureBriefDescription;

    public String pictureBtnName;

    public List<WGHomeFloorContentItem> content;

    public int jumpType;

    public boolean onlyName() {
        return JHStringUtils.isNullOrEmpty(breifDescription);
    }

    public boolean hasTitle() {
        return !JHStringUtils.isNullOrEmpty(name);
    }

    public boolean hasImageTitle() {
        return !JHStringUtils.isNullOrEmpty(pictureURL);
    }

}
