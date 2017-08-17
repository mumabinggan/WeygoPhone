package com.weygo.weygophone.pages.base.model;

import java.util.List;

/**
 * Created by muma on 2017/4/24.
 */

public class WGBaseService {
    public List<String> postcodes;

    public boolean contain(String cap) {
        if (postcodes != null) {
            return postcodes.contains(cap);
        }
        return false;
    }
}
