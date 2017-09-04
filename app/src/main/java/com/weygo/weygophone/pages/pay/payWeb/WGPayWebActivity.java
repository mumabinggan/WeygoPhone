package com.weygo.weygophone.pages.pay.payWeb;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.JHUriUtils;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.base.WGWebActivity;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderData;
import com.weygo.weygophone.pages.pay.paySuccess.WGPaySuccessActivity;

import java.io.Serializable;

/**
 * Created by muma on 2017/8/27.
 */

public class WGPayWebActivity extends WGWebActivity {

    WGCommitOrderData mData;

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp != null && temp instanceof WGCommitOrderData) {
                mData = (WGCommitOrderData)temp;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        if (mData!= null) {
            mWebView.loadUrl(WGApplicationRequestUtils.getInstance().payUrl(mData.action));
            mWebView.setWebViewClient(new PayWebViewClient());
        }
    }

    private class PayWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!JHStringUtils.isNullOrEmpty(url) && url.contains(mData.actionSuccess)) {
                Intent intent = new Intent(WGPayWebActivity.this, WGPaySuccessActivity.class);
                startActivity(intent);
                return false;
            }
            return true;
        }
    }
}
