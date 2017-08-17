package com.weygo.weygophone.pages.tabs.mine.model;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/4/27.
 */

public class WGUser extends WGObject {

    public long userId;

    public String sessionKey;

    public String fullName;

    public String name;

    public String surname;

    public String mobile;

    public String email;

    public int sex;     //1 : 男; 2 : 女

    public String birth;

    public String tax;

    public String cap;

    public int orderCount;

    public int deliveringCount;

    //for self use
    public String sexString() {
        List<String> sexs = this.sexs();
        return sexs.get(sex);
    };

    //for self use
    List<String> mSexList;

    public List<String> sexs() {
        if (mSexList == null) {
            mSexList = new ArrayList();
            mSexList.add(JHResourceUtils.getInstance().getString(R.string.PersonInfo_Sex_Select));
            mSexList.add(JHResourceUtils.getInstance().getString(R.string.PersonInfo_Sex_Man));
            mSexList.add(JHResourceUtils.getInstance().getString(R.string.PersonInfo_Sex_Woman));
        }
        return mSexList;
    };

    //for self use
    public int userAvatar() {
        if (sex == 1) {
            return R.drawable.mine_boy_icon;
        }
        else if (sex == 2) {
            return R.drawable.mine_girl_icon;
        }
        else {
            return R.drawable.mine_unknown_icon;
        }
    }

    //for self use
    public boolean isBoy() {
        return (sex == 1);
    };

    //for self use
    public boolean isGirl() {
        return (sex == 2);
    };

}
