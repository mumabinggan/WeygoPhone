package com.weygo.weygophone.pages.address.edit.model;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by muma on 2017/8/19.
 */

public class WGAddress extends WGObject {

    public long addressId;

    public String fullName;

    public String firstName;

    public String lastName;

    public String country = JHResourceUtils.getInstance().getString(R.string.Address_Italia);
    public String countryId = "IT";

    public String phone;

    public String cap;

    public String city;
    public String cityId;

    public String address;

    public String streetNumber;

    public String citofono;         //门铃

    public String scala;            //楼座

    public int ascensore = 0;           //是否有电梯, 1：有， 0：没有

    public String piano;            //楼层

    //是否默认
    public boolean isDefault;

    //for self
    public List<Integer> ascensores = Arrays.asList(R.string.AddressList_NoLift,
            R.string.AddressList_HaveLift);

    public int currentAscensore() {
        if (ascensore == 0) {
            return ascensores.get(0);
        }
        else {
            return ascensores.get(1);
        }
    }

    public boolean showUse;
}
