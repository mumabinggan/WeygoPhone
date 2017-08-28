package com.weygo.weygophone.base;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.coupon.adapter.WGCouponlistAdapter;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.coupon.widget.WGCouponListInputView;

import java.io.Serializable;

/**
 * Created by muma on 2017/8/27.
 */

public class WGWebActivity extends WGTitleActivity {

    public WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.webview_activity);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mWebView = (WebView) findViewById(R.id.webView);
    }

}
