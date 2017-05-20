package com.weygo.common.tools.network;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.weygo.common.base.JHObject;
import com.weygo.common.base.JHRequest;

import okhttp3.Call;

/**
 * Created by muma on 2016/11/30.
 */

public class JHNetworkUtils extends JHObject {

    private static final JHNetworkUtils ourInstance = new JHNetworkUtils();

    public static JHNetworkUtils getInstance() {
        return ourInstance;
    }

    JHBaseNetworkInterface network = null;

    private JHNetworkUtils() {
        super();
        network = new JHOKHTTPNewwork();
    }

    public Call getAsyn(JHRequest request, Class clazz, JHResponseCallBack callBack) {
        return network.getAsync(request, clazz, callBack);
    }

    public Call postAsyn(JHRequest request, Class clazz, JHResponseCallBack callBack) {
        return network.postAsync(request, clazz, callBack);
    }

    /**
     * 判断是不是wifi网络状态
     *
     * @param paramContext
     * @return
     */
    public boolean isWifi(Context paramContext) {
        return "2".equals(getNetType(paramContext)[0]);
    }

    /**
     * 判断是不是2/3G网络状态
     *
     * @param paramContext
     * @return
     */
    public boolean isMobile(Context paramContext) {
        return "1".equals(getNetType(paramContext)[0]);
    }

    /**
     * 网络是否可用
     *
     * @param paramContext
     * @return
     */
    public boolean isNetAvailable(Context paramContext) {
        if ("1".equals(getNetType(paramContext)[0]) || "2".equals(getNetType(paramContext)[0])) {
            return true;
        }
        return false;
    }

    /**
     * 获取当前网络状态 返回2代表wifi,1代表2G/3G
     *
     * @param paramContext
     * @return
     */
    public String[] getNetType(Context paramContext) {
        String[] arrayOfString = {"Unknown", "Unknown"};
        PackageManager localPackageManager = paramContext.getPackageManager();
        if (localPackageManager.checkPermission("android.permission.ACCESS_NETWORK_STATE", paramContext.getPackageName()) != 0) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }

        ConnectivityManager localConnectivityManager = (ConnectivityManager) paramContext.getSystemService("connectivity");
        if (localConnectivityManager == null) {
            arrayOfString[0] = "Unknown";
            return arrayOfString;
        }

        NetworkInfo localNetworkInfo1 = localConnectivityManager.getNetworkInfo(1);
        if (localNetworkInfo1 != null && localNetworkInfo1.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "2";
            return arrayOfString;
        }

        NetworkInfo localNetworkInfo2 = localConnectivityManager.getNetworkInfo(0);
        if (localNetworkInfo2 != null && localNetworkInfo2.getState() == NetworkInfo.State.CONNECTED) {
            arrayOfString[0] = "1";
            arrayOfString[1] = localNetworkInfo2.getSubtypeName();
            return arrayOfString;
        }

        return arrayOfString;
    }

}
