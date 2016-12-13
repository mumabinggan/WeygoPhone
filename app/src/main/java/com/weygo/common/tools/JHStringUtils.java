package com.weygo.common.tools;

import com.weygo.common.base.JHObject;

/**
 * Created by muma on 2016/11/30.
 */

public class JHStringUtils extends JHObject {

    public static boolean isNullOrEmpty(String string) {
        return (string == null || string == "");
    }
}
