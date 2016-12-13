package com.weygo.weygophone.request;

/**
 * Created by muma on 2016/12/1.
 */

public class WGWeatherRequest extends WGRequest {

    public String city;
    public String ss;
    public String sfs = "ff";

    @Override
    public String  api() {
        return "/now";
    };
}
