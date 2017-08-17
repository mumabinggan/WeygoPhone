package com.weygo.weygophone.pages.classifyDetail.model;

import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;

import java.util.List;

/**
 * Created by muma on 2017/8/9.
 */

public class WGClassifyFilterCondition extends WGObject {

    //品牌 id
    public List<WGClassifyKeyword> brands;

    //种类
    public List<WGClassifyItem> classifys;

    //for self use
    public boolean isSelected() {
        for (WGClassifyKeyword item : brands) {
            if (item.isSelected) {
                return true;
            }
        }
        for (WGClassifyItem item : classifys) {
            if (item.isSelected) {
                return true;
            }
        }
        return false;
    }

}
