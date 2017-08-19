package com.weygo.weygophone.pages.order.commit.model;

import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/8/19.
 */

public class WGCommitOrderPay extends WGObject {

    public String currentPayId;

    public List<WGSettlementPayMethod> payMethods;

    public String payName;
    public String getPayName() {
        if (JHStringUtils.isNullOrEmpty(currentPayId)) {
            if (payMethods.size() > 0) {
                WGSettlementPayMethod method = payMethods.get(0);
                currentPayId = method.id;
            }
        }
        for (WGSettlementPayMethod item : payMethods) {
            if (item.id.equals(currentPayId)) {
                return item.name;
            }
        }
        return null;
    }
}
