package com.weygo.weygophone.common;

import android.os.Bundle;
import android.util.Log;

import com.weygo.common.base.JHActivity;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageLoaderEntity;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by muma on 2017/4/24.
 */

public class WGApplicationRequestUtils {
    private static final WGApplicationRequestUtils ourInstance = new WGApplicationRequestUtils();

    public static WGApplicationRequestUtils getInstance() {
        return ourInstance;
    }

    private WGApplicationRequestUtils() {
    }

    private String signPrefix() {
        return WGConstants.WGAppIdKey + WGConstants.WGAppIdValue +
                WGConstants.WGAppkeyKey + JHStringUtils.md5(WGConstants.WGAppkeyValue);
    }

    public String sign(Map<String, Object> map) {
        StringBuffer buffer = new StringBuffer(this.signPrefix());
        buffer.append("___store");
        Log.e("___store", "___store");
        String storeValue = "mobilecn";
        //String storeValue = getResources().getString(R.string.request_StoreValue);
        Log.e("storeValue", storeValue);
        buffer.append(storeValue);
        Map<String, Object> sortMap = new TreeMap<String, Object>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj1.compareTo(obj2);
                    }
                });
        sortMap.putAll(map);
        for (Map.Entry<String, Object> m :map.entrySet())  {
            buffer.append(m.getKey());
            if (m.getValue() instanceof Number) {
                buffer.append(m.getValue() + "");
            }
            else {
                buffer.append(m.getValue());
            }
        }
        return "sign=" + JHStringUtils.md5(buffer.toString()) + "&___store=" + storeValue;
    }
}
