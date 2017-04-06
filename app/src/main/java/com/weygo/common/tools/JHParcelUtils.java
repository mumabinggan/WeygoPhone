package com.weygo.common.tools;

import android.os.Parcel;
import android.os.Parcelable;

import com.weygo.common.base.JHObject;

import org.parceler.Parcels;

/**
 * Created by muma on 2016/12/21.
 */

public class JHParcelUtils {

    public static Parcelable wrap(JHObject object) {
        return Parcels.wrap(object);
    }

    public static JHObject unWrap(Parcelable parcelable) {
        return Parcels.unwrap(parcelable);
    }
}
