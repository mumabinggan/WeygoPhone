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
    public static final String WGLocalSettingAsiaCache = "WGLocalSettingAsiaCache";
    public static final String WGLocalSettingBenefitCache = "WGLocalSettingBenefitCache";
    public static final String WGLocalSettingWelcome = "WGLocalSettingWelcome";
    public static final String WGLocalSettingLanguage = "WGLocalSettingLanguage";
    public static final String WGLocalSettingSearchHistory = "WGLocalSettingSearchHistory";

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
    public static final int WGNotificationTypeHomeTab = 3;
    public static final int WGNotificationTypeBenefitTab = 4;

    //Special Notification
    public static final String WGSpecialNotificationTypeShopCartCountAction = "WGSpecialNotificationTypeShopCartCountAction";
    public static final String WGReLoginAction = "WGReLoginAction";

    //Intent Data key
    public static final String WGIntentDataKey = "WGIntentDataKey";
    public static final String WGIntentDataKey1 = "WGIntentDataKey1";

    //跳转类型
    public static final int WGAppJumpTypeNone = 0;
    public static final int WGAppJumpTypeRegister = 1;          //注册
    public static final int WGAppJumpTypeGoodDetail = 2;        //商品详情
    public static final int WGAppJumpTypeInvitation = 3;        //邀请
    public static final int WGAppJumpTypeClassifyDetail = 4;    //分类详情
    public static final int WGAppJumpTypeSpecailClassifyHomeTab = 5;        //特殊专题:Home Tab
    public static final int WGAppJumpTypeSpecailClassifyGoodBenefitTab = 6;     //特殊专题（只显示商品）：Benefit Tab
    public static final int WGAppJumpTypeSpecailClassifyNoTab = 7;              //没有 Tab 的特殊专题
    public static final int WGAppJumpTypeSpecailClassifyGoodNoTab = 8;          //没有 Tab 的特殊专题（只显示商品）

    //Home floor数据类型
    public static final int WGHomeFloorItemTypeNone = 0;
    public static final int WGHomeFloorItemTypeGoodList = 1;
    public static final int WGHomeFloorItemTypeGoodColumn = 2;
    public static final int WGHomeFloorItemTypeGoodGrid = 3;
    public static final int WGHomeFloorItemTypeClassifyList = 4;
    public static final int WGHomeFloorItemTypeClassifyColumn = 5;
    public static final int WGHomeFloorItemTypeClassifyGrid = 6;
    public static final int WGHomeFloorItemTypeCountryColumn = 7;

    //分类详情排序类型
    public static final int WGClassifySortTypeDefault = 0;
    public static final int WGClassifySortTypeBranch = 1;
    public static final int WGClassifySortTypePriceUp = 2;
    public static final int WGClassifySortTypePriceDown = 3;

    //Intent Result
    public static final int WGCommitOrderReceiptCommitReturn = 10;
    public static final int WGCommitOrderReceiptCancelReturn = 14;
    public static final int WGCommitOrderAddressReturn = 11;
    public static final int WGCommitOrderCouponReturn = 12;
    public static final int WGCommitOrderIntegralReturn = 13;

    public static final int WGGoodAddViewFromGoodDetail = 1;
    public static final int WGGoodAddViewFromCart = 2;


    public static final int WGOrderListTypeAll = 1;
    public static final int WGOrderListTypeDelivering = 2;

    public static final String WGBundlerId  = "com.weygo.test";

    //绑定类型
    public static final int WGBindTypeFaceBook = 1;
    public static final int WGBindTypeWechat = 2;
}

