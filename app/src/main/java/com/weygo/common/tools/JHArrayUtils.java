package com.weygo.common.tools;

import java.util.List;

/**
 * Created by muma on 2016/12/19.
 */

public class JHArrayUtils {
    public static boolean isNullOrEmpty(List array) {
        return (array == null || array.size() <= 0);
    }
}
