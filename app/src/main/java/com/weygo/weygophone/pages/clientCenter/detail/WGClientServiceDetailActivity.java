package com.weygo.weygophone.pages.clientCenter.detail;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.pages.clientCenter.list.model.WGClientServiceItem;

/**
 * Created by muma on 2017/5/18.
 */

public class WGClientServiceDetailActivity extends WGTitleActivity {

    WebView mWebView;

    ProgressBar mProgressBar;

    WGClientServiceItem mItem;

    RollPagerView mPagerView;

    @Override
    public void initContentView() {
        setContentView(R.layout.wgclientservice_detail_activity);
    }

    @Override
    public void initData() {
        super.initData();
        mItem = (WGClientServiceItem) getIntent().getSerializableExtra("key");
    }

    @Override
    public void initSubView() {
        super.initSubView();

        mNavigationBar.setTitle(R.string.ClientService_Title);
        mNavigationBar.setTitle(mItem.name);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.loadUrl(mItem.url);
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient() {//监听网页加载
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    mProgressBar.setVisibility(View.GONE);
                } else {
                    // 加载中
                    mProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });

        mPagerView = (RollPagerView) findViewById(R.id.banner);
        mPagerView.setAdapter(new TestLoopAdapter(mPagerView));
    }

    private class TestLoopAdapter extends LoopPagerAdapter {
        private int[] imgs = {
                R.drawable.tab_mine_head_bg,
                R.drawable.common_facebook,
                R.drawable.common_wechat,
                R.drawable.common_facebook,
        };

        public TestLoopAdapter(RollPagerView viewPager) {
            super(viewPager);
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getRealCount() {
            return imgs.length;
        }
    }
}
