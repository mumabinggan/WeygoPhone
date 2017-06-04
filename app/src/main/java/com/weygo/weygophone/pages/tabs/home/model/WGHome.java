package com.weygo.weygophone.pages.tabs.home.model;

import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHome extends WGObject {

    public List<WGCarouselFigureItem> carouselFigures;

    public List<WGTopicItem> topics;

    public List<WGHomeFloorItem> floors;

    //for self use
    public List<String> carouselFiguresPictureArray() {
        if (carouselFigures == null) {
            return null;
        }
        List list = new ArrayList();
        for (WGCarouselFigureItem item : carouselFigures) {
            list.add(item.pictureURL);
        }
        return list;
    }
}
