package com.weygo.weygophone.base;

import android.util.Log;

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

    public String app = "com_weygo_test";

    public String os = "iOS";

    // eg. www.example.com, default ""
    //m.delong6688.develop.weygo.com
    public String host() {
        return "m.preview.weygo.com";
    }

    // eg. app/ default ""
    public String userPath() {
        return "appservice";
    };

    // no need to override, except your url is not format with: scheme://domain/path/api
    public String url() {
        String url = this.scheme() + "://" + this.host() + "/" + this.userPath() + "/" + this.api() + this.apiSuffix();
        Log.e("--url--", url);
        return url;
    }

    public String scheme() {
        return "http";
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
