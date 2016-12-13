package com.weygo.weygophone.request;

/**
 * Created by muma on 2016/11/30.
 */

public class WGCityListRequest extends WGRequest {

    @Override
    public String  api() {
        return "/apistore/weatherservice/citylist";
    };
}
