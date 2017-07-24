package com.weygo.weygophone.pages.tabs.home.model.response;

import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.pages.tabs.home.model.WGHome;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeTitleItem;

import java.util.HashMap;
import java.util.List;

/**
 * Created by muma on 2017/4/27.
 */

public class WGHomeTitlesResponse extends WGResponse {

    public List<WGHomeTitleItem> data;

    public void setTitles(WGHomeTitlesResponse response) {
        HashMap<String, WGHome> map = new HashMap();
        for (WGHomeTitleItem item : data) {
            if (item.data != null) {
                map.put(item.id + "", item.data);
            }
        }
        data = response.data;
        for (WGHomeTitleItem item : data) {
            item.data = map.get(item.id + "");
        }
    }
}
