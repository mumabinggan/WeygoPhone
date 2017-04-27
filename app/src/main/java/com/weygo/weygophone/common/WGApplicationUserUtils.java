package com.weygo.weygophone.common;

import android.content.Context;

import com.weygo.common.tools.JHLocalSettingUtils;
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

    private WGApplicationUserUtils() {
    }

    //member
    public WGUser mUser;
    public WGUser getmUser() {
        if (mUser == null) {
            WGUser user = (WGUser) JHLocalSettingUtils.getLocalSetting(mContext, WGConstants.WGLocalSettingUser, WGUser.class);
            mUser = user;
        }
        return mUser;
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
        return R.drawable.arrow_black;
    }
}
