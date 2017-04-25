package com.weygo.weygophone.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.weygo.common.base.JHRequest;
import com.weygo.weygophone.common.WGApplicationRequestUtils;

import java.util.Map;
import java.util.Set;

/**
 * Created by muma on 2016/12/14.
 */

public class WGRequest extends JHRequest {

    // eg. www.example.com, default ""
    public String host() {
        return "m.delong6688.develop.weygo.com";
    }

    // eg. app/ default ""
    public String userPath() {
        return "appservice";
    };

    // no need to override, except your url is not format with: scheme://domain/path/api
    public String url() {
        return this.scheme() + "://" + this.host() + "/" + this.userPath() + "/" + this.api() + this.apiSuffix();
    }

//    public String apiSuffix() {
//        return "sign=56b6b083e9469fc08a1c186eb153f5a0&___store=mobilecn";
//    }

    public String apiSuffix() {
        String jsonString = JSON.toJSONString(this);
        Map<String, Object> map = JSON.parseObject(jsonString,new TypeReference<Map<String, Object>>(){} );
        return WGApplicationRequestUtils.getInstance().sign(map);
//        String jsonString = JSON.toJSONString(this);
//        JSON.parseObject(
//                jsonStr,new TypeReference<Map<String, Object>>(){} );
//        return WGApplicationRequestUtils.getInstance().sign(JSON.par);
    }
}
