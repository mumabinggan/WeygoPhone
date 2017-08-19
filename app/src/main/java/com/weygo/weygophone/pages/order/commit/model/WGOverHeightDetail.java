package com.weygo.weygophone.pages.order.commit.model;

import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/8/19.
 */

public class WGOverHeightDetail extends WGObject {

    public String title;

    public String maxRise;

    public List<WGOverHeightGoodItem> goods;

    public float currentRise() {
        float totalRise = 0.0f;
        for (WGOverHeightGoodItem item : goods) {
            totalRise += item.rise * item.goodCount;
        }
        return totalRise;
    }
}
