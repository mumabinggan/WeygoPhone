package com.weygo.weygophone.common;

import android.content.Context;
import android.content.Intent;

import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.weygophone.pages.base.model.WGBaseService;
import com.weygo.weygophone.pages.search.model.WGSearchKeywordItem;

import java.util.List;

/**
 * Created by muma on 2017/8/17.
 */

public class WGApplicationGlobalUtils extends Object {

    private Context mContext;

    WGBaseService mBaseService;

    public long mShopCartGoodCount;

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

    public void handleShopCartGoodCount(long count) {
        mShopCartGoodCount = count;
        //通知
        Intent intent = new Intent(WGConstants.WGSpecialNotificationTypeShopCartCountAction);
        mContext.sendBroadcast(intent);
    }

    public List<WGSearchKeywordItem> getLocalSettingHistorySearchArray() {
        List<WGSearchKeywordItem> historyList = (List<WGSearchKeywordItem>) JHLocalSettingUtils.
                getLocalSetting(mContext, WGConstants.WGLocalSettingSearchHistory, List.class);
        return historyList;
    }

    public void addLocalSettingHistorySearch(WGSearchKeywordItem data) {
        List<WGSearchKeywordItem> historyList = (List<WGSearchKeywordItem>) JHLocalSettingUtils.
                getLocalSetting(mContext, WGConstants.WGLocalSettingSearchHistory, List.class);
        if (historyList != null) {
            for (WGSearchKeywordItem item : historyList) {
                if (item.id == data.id) {
                    historyList.remove(item);
                    break;
                }
            }
            historyList.add(0, data);
        }
        JHLocalSettingUtils.setLocalSetting(mContext, WGConstants.WGLocalSettingSearchHistory, historyList);
    }

    public void reset() {
        handleShopCartGoodCount(0);
    }
}
