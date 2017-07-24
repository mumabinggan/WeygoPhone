package com.weygo.weygophone.common;

/**
 * Created by muma on 2017/4/24.
 */

public class WGConstants {
    //Tab Enum
    public static final int WGTabHome = 0;
    public static final int WGTabClassify = 1;
    public static final int WGTabShopCart = 2;
    public static final int WGTabBenefit = 3;
    public static final int WGTabMine = 4;

    //Request Sign
    public static final String WGAppIdKey = "appid";
    public static final String WGAppIdValue = "MOBILE_APPS";
    public static final String WGAppkeyKey = "appkey";
    public static final String WGAppkeyValue = "JXEENBJCB8A3SEBZA";

    //LocalSetting
    public static final String WGLocalSettingUser = "WGLocalSettingUser";
    public static final String WGLocalSettingUnLoginPostCode = "WGLocalSettingUnLoginPostCode";
    public static final String WGLocalSettingHomeCache = "WGLocalSettingHomeCache";
    public static final String WGLocalSettingClassifyCache = "WGLocalSettingClassifyCache";

    //RefreshNotification
    public static final int WGRefreshNotificationTypeLogin = 1;
    public static final int WGRefreshNotificationTypeLogout = 2;

    //CacheType
    public static final int WGCacheTypeBoth = 0;
    public static final int WGCacheTypeCachePrior = 1;
    public static final int WGCacheTypeNetwork = 2;

    //Notification
    public static final int WGNotificationTypeLogin = 1;
    public static final int WGNotificationTypeLogout = 2;

    //Intent Data key
    public static final String WGIntentDataKey = "WGIntentDataKey";

    //跳转类型
    public static final int WGAppJumpTypeNone = 0;
    public static final int WGAppJumpTypeRegister = 1;          //注册
    public static final int WGAppJumpTypeGoodDetail = 3;        //商品详情
    public static final int WGAppJumpTypeInvitation = 4;        //邀请
    public static final int WGAppJumpTypeClassifyDetail = 5;    //分类详情
    public static final int WGAppJumpTypeSpecailClassifyHomeTab = 6;        //特殊专题:Home Tab
    public static final int WGAppJumpTypeSpecailClassifyGoodBenefitTab = 7;     //特殊专题（只显示商品）：Benefit Tab
    public static final int WGAppJumpTypeSpecailClassifyNoTab = 8;              //没有 Tab 的特殊专题
    public static final int WGAppJumpTypeSpecailClassifyGoodNoTab = 9;          //没有 Tab 的特殊专题（只显示商品）

    //Home floor数据类型
    public static final int WGHomeFloorItemTypeNone = 0;
    public static final int WGHomeFloorItemTypeGoodList = 1;
    public static final int WGHomeFloorItemTypeGoodColumn = 2;
    public static final int WGHomeFloorItemTypeGoodGrid = 3;
    public static final int WGHomeFloorItemTypeClassifyList = 4;
    public static final int WGHomeFloorItemTypeClassifyColumn = 5;
    public static final int WGHomeFloorItemTypeClassifyGrid = 6;
    public static final int WGHomeFloorItemTypeCountryColumn = 7;
}
