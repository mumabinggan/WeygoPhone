package com.weygo.weygophone.common;

import android.content.Context;

import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.mine.model.WGUser;

/**
 * Created by muma on 2017/4/24.
 */

public class WGApplicationUserUtils {

    private Context mContext;

    //for Singleton
    private static final WGApplicationUserUtils ourInstance = new WGApplicationUserUtils();

    public static WGApplicationUserUtils getInstance(Context context) {
        ourInstance.mContext = context;
        return ourInstance;
    }

    public static WGApplicationUserUtils getInstance() {
        return ourInstance;
    }

    private WGApplicationUserUtils() {
    }

    //user
    private WGUser mUser;
    public WGUser getmUser() {
        if (mUser == null) {
            WGUser user = (WGUser) JHLocalSettingUtils.getLocalSetting(mContext, WGConstants.WGLocalSettingUser, WGUser.class);
            mUser = user;
        }
        return mUser;
    }
    public void setmUser(WGUser user) {
        mUser = user;
        if (user == null) {
            JHLocalSettingUtils.removeLocalSetting(mContext, WGConstants.WGLocalSettingUser);
        }
        else {
            JHLocalSettingUtils.setLocalSetting(mContext, WGConstants.WGLocalSettingUser, WGUser.class);
        }
    }

    public String sessionKey() {
        WGUser user = this.getmUser();
        if (user != null) {
            return user.sessionKey;
        }
        return null;
    }

    public boolean isLogined() {
        WGUser user = this.getmUser();
        if (user != null) {
            return true;
        }
        return false;
    }

    public boolean isBoy() {
        WGUser user = this.getmUser();
        if (user != null) {
            return user.isBoy();
        }
        return false;
    }

    public boolean isGirl() {
        WGUser user = this.getmUser();
        if (user != null) {
            return user.isGirl();
        }
        return false;
    }

    public int userAvatar() {
        WGUser user = this.getmUser();
        if (user != null) {
            return user.userAvatar();
        }
        return R.drawable.mine_unknown_icon;
    }

    public String fullName() {
        WGUser user = getmUser();
        if (user != null) {
            return user.fullName;
        }
        return null;
    }

    //member postCode
    private String mCurrentPostCode;
    public void setCurrentPostCode(String postCode) {
        if (isLogined()) {
            mUser.cap = postCode;
            JHLocalSettingUtils.setLocalSetting(mContext, WGConstants.WGLocalSettingUser, WGUser.class);
        }
        else {
            JHLocalSettingUtils.setLocalSetting(mContext, WGConstants.WGLocalSettingUnLoginPostCode, String.class);
        }
        mCurrentPostCode = postCode;
    }
    public String currentPostCode() {
        if (JHStringUtils.isNullOrEmpty(mCurrentPostCode)) {
            if (isLogined()) {
                mCurrentPostCode = getmUser().cap;
            }
            else {
                mCurrentPostCode = (String) JHLocalSettingUtils.getLocalSetting(mContext, WGConstants.WGLocalSettingUnLoginPostCode, String.class);
            }
        }
        return mCurrentPostCode;
    }

    public int orderCount() {
        WGUser user = getmUser();
        if (user != null) {
            return user.orderCount;
        }
        return 0;
    }

    public int deliverOrderCount() {
        WGUser user = getmUser();
        if (user != null) {
            return user.deliveringCount;
        }
        return 0;
    }

    public void reset() {
        this.setmUser(null);
        this.setCurrentPostCode(null);
        JHLocalSettingUtils.removeLocalSetting(mContext, WGConstants.WGLocalSettingUnLoginPostCode);
        JHLocalSettingUtils.removeLocalSetting(mContext, WGConstants.WGLocalSettingUser);
    }
}
