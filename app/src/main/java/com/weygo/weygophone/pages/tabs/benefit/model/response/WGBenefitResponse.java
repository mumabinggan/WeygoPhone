package com.weygo.weygophone.pages.tabs.benefit.model.response;

import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.pages.tabs.home.model.WGHome;
import com.weygo.weygophone.pages.tabs.home.model.WGTitleItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by muma on 2017/8/1.
 */

public class WGBenefitResponse extends WGResponse {

    public List<WGTitleItem> data;

    public void setTitles(WGBenefitResponse response) {
        HashMap<String, WGHome> map = new HashMap();
        for (WGTitleItem item : data) {
            if (item.data != null) {
                map.put(item.id + "", item.data);
            }
        }
        data = response.data;
        for (WGTitleItem item : data) {
            item.data = map.get(item.id + "");
        }
    }
}
