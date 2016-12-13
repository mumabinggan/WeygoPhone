package com.weygo.common.tools.network;

import com.weygo.common.base.JHObject;

/**
 * Created by muma on 2016/11/30.
 */

public class JHRequestError extends JHObject {

    int errorCode = -1;

    String message;

    public JHRequestError() {

    }

    public JHRequestError(String message) {
        this.message = message;
    }
}
