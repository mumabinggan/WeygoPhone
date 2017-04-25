package com.weygo.weygophone.common;

/**
 * Created by muma on 2017/4/24.
 */

public class WGApplicationUserUtils {
    //for Singleton
    private static final WGApplicationUserUtils ourInstance = new WGApplicationUserUtils();

    public static WGApplicationUserUtils getInstance() {
        return ourInstance;
    }

    private WGApplicationUserUtils() {
    }

    //member

}
