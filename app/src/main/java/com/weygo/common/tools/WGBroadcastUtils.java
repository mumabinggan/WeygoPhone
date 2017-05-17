package com.weygo.common.tools;

import android.content.Context;
import android.content.Intent;

/**
 * Created by muma on 2017/5/17.
 */

public class WGBroadcastUtils {
    static void sendBroadcast(Context context, String broadcast) {
        Intent intent = new Intent(broadcast);
        context.sendBroadcast(intent);
    }
}
