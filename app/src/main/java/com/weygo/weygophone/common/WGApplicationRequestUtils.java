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
import com.weygo.weygophone.pages.goodDetail.model.request.WGAddGoodToCartRequest;
import com.weygo.weygophone.pages.goodDetail.model.response.WGAddGoodToCartResponse;
import com.weygo.weygophone.pages.order.detail.model.request.WGRebuyRequest;
import com.weygo.weygophone.pages.order.detail.model.response.WGRebuyResponse;
import com.weygo.weygophone.pages.register.model.request.WGGetVerificationCodeRequest;
import com.weygo.weygophone.pages.register.model.response.WGGetVerificationCodeResponse;
import com.weygo.weygophone.pages.tabs.home.model.request.WGLogoutRequest;
import com.weygo.weygophone.pages.tabs.home.model.request.WGShopCartCountRequest;
import com.weygo.weygophone.pages.tabs.home.model.response.WGLogoutResponse;
import com.weygo.weygophone.pages.tabs.home.model.response.WGShopCartCountResponse;
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
        String storeValue = JHResourceUtils.getInstance().getString(R.string.request_StoreValue);
//        storeValue = "mobilecn";
        buffer.append(storeValue);
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
//        Log.e("----buffer----", buffer.toString());
        sortMap.clear();
//        String baseString = "sign=" + JHStringUtils.md5(buffer.toString());
        String baseString = "sign=" + JHStringUtils.md5(buffer.toString()) + "&___store=" + storeValue;

        return baseString;
    }

    public String paySign() {
        StringBuffer buffer = new StringBuffer(this.signPrefix());
        buffer.append("app");
        buffer.append(WGConstants.WGBundlerId);
        buffer.append("os");
        buffer.append("Android");
        String sessionKey = WGApplicationUserUtils.getInstance().sessionKey();
        if (!JHStringUtils.isNullOrEmpty(sessionKey)) {
            buffer.append("sessionKey");
            buffer.append(sessionKey);
        }
        String paySign = "sign=" + JHStringUtils.md5(buffer.toString());
        return paySign;
    }
    public String payUrl(String url) {
        return url + paySign() +
                "&sessionKey=" +
                WGApplicationUserUtils.getInstance().sessionKey() +
                "&app=" + WGConstants.WGBundlerId + "&os=Android";
    }

    void showWarning(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    void showWarning(int resId) {
        showWarning(JHResourceUtils.getInstance().getString(resId));
    }

    public void loadUserInfoOnCompletion(Context context, final WGOnUserInfoCompletionInteface inteface) {
        WGUserInfoRequest request = new WGUserInfoRequest();
        request.showsLoadingView = false;
        JHNetworkUtils.getInstance().postAsyn(request, WGUserInfoResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                WGUserInfoResponse response = (WGUserInfoResponse) result;
                handleUserInfo(response);
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

    public void loadLogoutRequest(final WGOnCompletionInteface inteface) {
        WGLogoutRequest request = new WGLogoutRequest();
        JHNetworkUtils.getInstance().postAsyn(request, WGLogoutResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
                WGLogoutResponse response = (WGLogoutResponse) result;
                handleLogoutResponse(response);
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

    void handleLogoutResponse(WGLogoutResponse response) {
        if (response.success()) {
            WGApplicationUserUtils.getInstance().reset();
        }
    }

    public void loadRebuyOrderRequest(long orderId, final WGOnCompletionInteface inteface) {
        WGRebuyRequest request = new WGRebuyRequest();
        request.orderId = orderId;
        JHNetworkUtils.getInstance().postAsyn(request, WGRebuyResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                WGRebuyResponse response = (WGRebuyResponse) result;
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

    public void loadAddGoodToCart(long goodId, long count, final WGOnCompletionInteface inteface) {
        WGAddGoodToCartRequest request = new WGAddGoodToCartRequest();
        request.goodId = goodId;
        request.count = count;
        JHNetworkUtils.getInstance().postAsyn(request, WGAddGoodToCartResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                WGAddGoodToCartResponse response = (WGAddGoodToCartResponse) result;
                if (response.success()) {
                    if (inteface != null) {
                        inteface.onSuccessCompletion(response);
                    }
                }
                else {
                    showWarning(response.message);
                }
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    public void loadShopCartCount(final WGOnCompletionInteface inteface) {
        WGShopCartCountRequest request = new WGShopCartCountRequest();
        request.showsLoadingView = false;
        JHNetworkUtils.getInstance().postAsyn(request, WGShopCartCountResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                WGShopCartCountResponse response = (WGShopCartCountResponse) result;
                handleShopCartCount(response);
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

    void handleShopCartCount(WGShopCartCountResponse response) {
        if (response != null && response.success()) {
            WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(response.data.goodCount);
        }
    }
}
