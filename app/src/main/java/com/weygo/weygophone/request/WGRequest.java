package com.weygo.weygophone.request;

import com.weygo.common.base.JHRequest;
import com.weygo.weygophone.models.JHTest;

import java.util.List;

/**
 * Created by muma on 2016/11/30.
 */

public class WGRequest extends JHRequest {

    public String key = "b27e9cb9b51149dba8eb8d874f238767";

    public JHTest ssf = new JHTest(323, "ASDFASDFASDF");

    @Override
    public String scheme() {
        return "http";
    }

    @Override
    public String host() {
        return "free-api.heweather.com";
    }

    @Override
    public String path() {
        return "/v5";
    }
}
