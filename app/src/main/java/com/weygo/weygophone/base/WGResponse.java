package com.weygo.weygophone.base;

import com.weygo.common.base.JHResponse;

/**
 * Created by muma on 2016/12/14.
 */

public class WGResponse extends JHResponse {

    public int code;

    public String message;

    public boolean success() {
        return code == 1;
    }

    public boolean reLogin() {
        return code == -1;
    }
}
