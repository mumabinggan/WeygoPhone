package com.weygo.weygophone.pages.order.commit.model;

import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/8/19.
 */

public class WGCommitOrderDeliverTime extends WGObject {

    public String currentDateId;

    public String currentTimeId;

    public String defaultTimeId;
    public String getDefaultTimeId() {
        List<WGSettlementTime> times = getCurrentTimes();
        if (times != null && times.size() > 0) {
            WGSettlementTime time = times.get(0);
            return time.id;
        }
        return null;
    }

    //from settlement
    public List<WGSettlementDate> deliverTimes;

    //self use
    public String currentDate;
    public String getCurrentDate() {
        if (JHStringUtils.isNullOrEmpty(currentDateId)) {
            if (deliverTimes != null && deliverTimes.size() > 0) {
                WGSettlementDate item = deliverTimes.get(0);
                currentDateId = item.id;
            }
        }
        for (WGSettlementDate item : deliverTimes) {
            if (item.id.equals(currentDateId)) {
                currentDate = item.week + "  " + item.date;
                //currentDate = item.date;
                break;
            }
        }
        return currentDate;
    }

    public String currentTime;
    public String getCurrentTime() {
        for (WGSettlementDate date : deliverTimes) {
            if (date.id.equals(currentDateId)) {
                if (JHStringUtils.isNullOrEmpty(currentTimeId)) {
                    if (date.times != null && date.times.size() > 0) {
                        WGSettlementTime time = date.times.get(0);
                        currentTimeId = time.id;
                    }
                }
                for (WGSettlementTime time : date.times) {
                    if (time.id.equals(currentTimeId)) {
                        currentTime = time.time;
                        break;
                    }
                }
                break;
            }
        }
        return currentTime;
    }

    public List<WGSettlementTime> currentTimes;
    public List<WGSettlementTime> getCurrentTimes() {
        for (WGSettlementDate date : deliverTimes) {
            if (date.id == currentDateId) {
                return date.times;
            }
        }
        return null;
    }

    public void resetWithTimes(List deliverTimes) {
        this.deliverTimes = deliverTimes;
        currentTimeId = null;
        currentDateId = null;
    }

}
