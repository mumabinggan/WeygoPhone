package com.weygo.weygophone.common;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHActivity;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHDialogUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageLoaderEntity;
import com.weygo.common.tools.network.JHNetworkUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.base.WGInterface;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.pages.collection.model.request.WGCancelCollectionGoodRequest;
import com.weygo.weygophone.pages.collection.model.response.WGCancelCollectionGoodResponse;
import com.weygo.weygophone.pages.common.model.request.WGSetPostCodeRequest;
import com.weygo.weygophone.pages.common.model.response.WGSetPostCodeResponse;
import com.weygo.weygophone.pages.register.model.request.WGGetVerificationCodeRequest;
import com.weygo.weygophone.pages.register.model.response.WGGetVerificationCodeResponse;
import com.weygo.weygophone.pages.tabs.mine.model.request.WGUserInfoRequest;
import com.weygo.weygophone.pages.tabs.mine.model.response.WGUserInfoResponse;

import org.parceler.apache.commons.collections.map.HashedMap;

import java.net.URLEncoder;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by muma on 2017/4/24.
 */

public class WGApplicationRequestUtils {

    Context mContext;

    Dialog mShowDialog;

    private static final WGApplicationRequestUtils ourInstance = new WGApplicationRequestUtils();

    public static WGApplicationRequestUtils getInstance(Context context) {
        ourInstance.mContext = context;
        return ourInstance;
    }

    public static WGApplicationRequestUtils getInstance() {
        return ourInstance;
    }

    private WGApplicationRequestUtils() {
    }

    private String signPrefix() {
        return WGConstants.WGAppIdKey + WGConstants.WGAppIdValue +
                WGConstants.WGAppkeyKey + JHStringUtils.md5(WGConstants.WGAppkeyValue);
    }

    public Map<String, Object> addModifiedMap(Map<String, Object> map) {
        Map<String, Object> newMap = new HashedMap(map);
        newMap.put("os", "Android");
        newMap.put("app", "com.weygo.test");
        return newMap;
    }

    public Map<String, Object> versionMap() {
        Map<String, Object> map = new HashedMap();
        map.put("os", "Android");
        map.put("app", "com.weygo.test");
        return map;
    }

    public String sign(Map<String, Object> map) {
        StringBuffer buffer = new StringBuffer(this.signPrefix());
        buffer.append("___store");
        Log.e("___store", "___store");
        String storeValue = "mobilecn";
        //String storeValue = "mobilecn";
        //String storeValue = getResources().getString(R.string.request_StoreValue);
        Log.e("storeValue", storeValue);
        buffer.append(storeValue);

        //map.putAll(versionMap());

//        Map<String, Object> sortMap = new TreeMap<String, Object>(
//                new Comparator<String>() {
//                    public int compare(String obj1, String obj2) {
//                        // 降序排序
//                        return obj1.compareTo(obj2);
//                    }
//                });
        TreeMap<String, Object> sortMap = new TreeMap<>(map);
        for (Map.Entry<String, Object> m :sortMap.entrySet())  {
            buffer.append(m.getKey());
            if (m.getValue() instanceof Number) {
                buffer.append(m.getValue() + "");
            }
            else {
                buffer.append(m.getValue());
            }
        }
        Log.e("----buffer----", buffer.toString());
        sortMap.clear();
        String baseString = "sign=" + JHStringUtils.md5(buffer.toString()) + "&___store=" + storeValue;
        return baseString;
//        sortMap.putAll(versionMap());
//        StringBuffer versionString = new StringBuffer(baseString);
//        for (Map.Entry<String, Object> m :sortMap.entrySet())  {
//            versionString.append("&" + m.getKey() + "=" + m.getValue());
//        }
//        return versionString.toString();
        //return "sign=" + JHStringUtils.md5(buffer.toString()) + "&___store=" + storeValue + "&app=" + "";
    }

    void showWarning(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG);
    }

    void showWarning(int resId) {
        showWarning(JHResourceUtils.getInstance().getString(resId));
    }

    public void loadUserInfoOnCompletion(Context context, final WGOnUserInfoCompletionInteface inteface) {
        WGUserInfoRequest request = new WGUserInfoRequest();
//        final boolean showLoading = request.showsLoadingView;
//        if (showLoading) {
//            mShowDialog = JHDialogUtils.showLoadingDialog(context);
//        }
        JHNetworkUtils.getInstance().postAsyn(request, WGUserInfoResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                WGUserInfoResponse response = (WGUserInfoResponse) result;
                handleUserInfo(response);
//                if (showLoading) {
//                    JHDialogUtils.hideLoadingDialog(mShowDialog);
//                }
                if (inteface != null) {
                    inteface.onUserInfoCompletion(response);
                }
            }

            @Override
            public void onFailure(JHRequestError error) {
//                if (showLoading) {
//                    JHDialogUtils.hideLoadingDialog(mShowDialog);
//                }
            }
        });
    }

    void handleUserInfo(WGUserInfoResponse response) {
        if (response.success()) {
            WGApplicationUserUtils.getInstance().setUser(response.data);
        }
    }

    public interface WGOnSendVerificationCodeCompletionInteface extends WGInterface {
        void onSendCompletion(WGGetVerificationCodeResponse response);
    }


    public void loadSendVerificationCode(Context context, String  phone, String countryCode, final WGOnSendVerificationCodeCompletionInteface inteface) {
        WGGetVerificationCodeRequest request = new WGGetVerificationCodeRequest();
        request.username = phone;
        JHNetworkUtils.getInstance().postAsyn(request, WGGetVerificationCodeResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                WGGetVerificationCodeResponse response = (WGGetVerificationCodeResponse) result;
                handleGetVerificationCode(response);
                if (inteface != null) {
                    inteface.onSendCompletion(response);
                }
            }

            @Override
            public void onFailure(JHRequestError error) {
            }
        });
    }

    void handleGetVerificationCode(WGGetVerificationCodeResponse response) {
        if (response.success()) {
            showWarning(response.message);
        }
    }

    public interface WGOnCancelCollectGoodCompletionInteface extends WGInterface {
        void onCancelCompletion(WGCancelCollectionGoodResponse response, long goodId);
    }

    public interface WGOnCompletionInteface extends WGInterface {

        void onSuccessCompletion(WGResponse response);

        void onFailCompletion(WGResponse response);
    }

    public void loadCancelCollectionRequest(final long goodId, final WGOnCancelCollectGoodCompletionInteface inteface) {
        WGCancelCollectionGoodRequest request = new WGCancelCollectionGoodRequest();
        request.id = goodId;
        JHNetworkUtils.getInstance().postAsyn(request, WGCancelCollectionGoodResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                WGCancelCollectionGoodResponse response = (WGCancelCollectionGoodResponse) result;
                if (inteface != null) {
                    inteface.onCancelCompletion(response, goodId);
                }
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    public void loadSetPostCodeRequest(final String cap, final WGOnCompletionInteface inteface) {
        WGSetPostCodeRequest request = new WGSetPostCodeRequest();
        request.cap = cap;
        JHNetworkUtils.getInstance().postAsyn(request, WGSetPostCodeResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                WGSetPostCodeResponse response = (WGSetPostCodeResponse) result;
                handleSetPostCodeResponse(response, cap);
                if (inteface != null) {
                    inteface.onSuccessCompletion(response);
                }
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleSetPostCodeResponse(WGSetPostCodeResponse response, String cap) {
        if (response.success()) {
            WGApplicationUserUtils.getInstance().setCurrentPostCode(cap);
        }
    }
}
