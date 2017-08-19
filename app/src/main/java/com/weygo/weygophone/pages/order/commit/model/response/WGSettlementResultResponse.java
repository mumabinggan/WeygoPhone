package com.weygo.weygophone.pages.order.commit.model.response;

import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementResult;

/**
 * Created by muma on 2017/8/19.
 */

public class WGSettlementResultResponse extends WGResponse {

    public WGSettlementResult data;

    public boolean  emptyShopCart() {
        return code == 4;
    }
}
