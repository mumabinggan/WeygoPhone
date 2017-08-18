package com.weygo.weygophone.common;

import android.content.Context;

import com.weygo.weygophone.pages.base.model.WGBaseService;

/**
 * Created by muma on 2017/8/17.
 */

public class WGApplicationGlobalUtils extends Object {

    private Context mContext;

    WGBaseService mBaseService;

    //for Singleton
    private static final WGApplicationGlobalUtils ourInstance = new WGApplicationGlobalUtils();

    public static WGApplicationGlobalUtils getInstance(Context context) {
        ourInstance.mContext = context;
        return ourInstance;
    }

    public static WGApplicationGlobalUtils getInstance() {
        return ourInstance;
    }

    private WGApplicationGlobalUtils() {
    }

    public void setBaseService(WGBaseService baseService) {
        mBaseService = baseService;
    }

    public boolean supportCap(String cap) {
        if (mBaseService != null) {
            return mBaseService.contain(cap);
        }
        return false;
    }

    public void handleShopCartGoodCount(int count) {

    }
}
