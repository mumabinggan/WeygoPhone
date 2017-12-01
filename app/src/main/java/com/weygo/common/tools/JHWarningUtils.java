package com.weygo.common.tools;

import android.content.Context;
import android.widget.Toast;

import com.weygo.common.base.JHObject;
import com.weygo.weygophone.WGApplication;

/**
 * Created by muma on 2016/11/29.
 */

public class JHWarningUtils extends JHObject {

    public static void showToast(Context ctx, String message, boolean shortTime) {
        Toast.makeText(ctx, message, shortTime ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context ctx, int messageId) {
        String message = ctx.getResources().getString(messageId);
        showToast(ctx, message, false);
    }

    public static void showToast(Context ctx, String message) {
        showToast(ctx, message, false);
    }

    public static void showToast(String message) {
        showToast(WGApplication.getContext(), message);
    }


//    public static Request.Builder addCommonCookie(Request.Builder builder){    //添加http请求字段
//        Map<String,String> cookieMap=new HashMap<>();    //添加sessionId
//        String sessionId= SharedPreferencesUtil.getInstance(BaseApplication.getMyApplicationContext())
//                .getString(ConstantsOpenSdk.LOGIN_SESSION_ID);
//        if(sessionId!=null&&!sessionId.equals("")){
//            cookieMap.put("Cookie",sessionId);
//            Config.newInstance().setProperty(cookieMap);
//        }
//
//        if (mConfig != null && mConfig.property != null
//                && !mConfig.property.isEmpty()) {
//            for (Map.Entry<String, String> map : mConfig.property.entrySet()) {
//                if(!TextUtils.isEmpty(map.getValue())) {
//                    builder.header(map.getKey(), map.getValue());
//                }
//            }
//        }
//        return builder;
//    }
}
