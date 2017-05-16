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

    /*

        WGAppJumpTypeNone,
    WGAppJumpTypeRegister,          //注册
    WGAppJumpTypeGoodDetail,        //商品详情
    WGAppJumpTypeInvitation,        //邀请
    WGAppJumpTypeClassifyDetail,    //分类详情
    WGAppJumpTypeSpecailClassifyHomeTab,    //特殊专题:Home Tab
    WGAppJumpTypeSpecailClassifyGoodBenefitTab, //特殊专题（只显示商品）：Benefit Tab
    WGAppJumpTypeSpecailClassifyNoTab,          //没有 Tab 的特殊专题
    WGAppJumpTypeSpecailClassifyGoodNoTab,      //没有 Tab 的特殊专题（只显示商品）
     */
    public static final int WGAppJumpTypeNone = 0;
    public static final int WGAppJumpTypeRegister = 1;          //注册
    public static final int WGAppJumpTypeGoodDetail = 3;        //商品详情
    public static final int WGAppJumpTypeInvitation = 4;        //邀请
    public static final int WGAppJumpTypeClassifyDetail = 5;    //分类详情
    public static final int WGAppJumpTypeSpecailClassifyHomeTab = 6;        //特殊专题:Home Tab
    public static final int WGAppJumpTypeSpecailClassifyGoodBenefitTab = 7;     //特殊专题（只显示商品）：Benefit Tab
    public static final int WGAppJumpTypeSpecailClassifyNoTab = 8;              //没有 Tab 的特殊专题
    public static final int WGAppJumpTypeSpecailClassifyGoodNoTab = 9;          //没有 Tab 的特殊专题（只显示商品）
}
