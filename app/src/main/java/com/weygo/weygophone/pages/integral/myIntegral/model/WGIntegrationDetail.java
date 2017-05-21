package com.weygo.weygophone.pages.integral.myIntegral.model;

import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/5/21.
 */

public class WGIntegrationDetail extends WGObject {

    public long totalCount;

    public String tip;

    public List<WGIntegrationHistoryItem> history;

    //for self use
    public boolean hasHistory() {
        return  (history != null && history.size() > 0);
    }

    public int historyCount() {
        if (hasHistory()) {
            return history.size();
        }
        return 0;
    }
}
