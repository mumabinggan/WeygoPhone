package com.weygo.weygophone.pages.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.redbooth.WelcomeCoordinatorLayout;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.base.model.request.WGBaseServiceRequest;
import com.weygo.weygophone.pages.base.model.response.WGBaseServiceResponse;

/**
 * Created by muma on 2017/4/25.
 */

public class WGBaseServiceActivity extends WGBaseActivity {

    WelcomeCoordinatorLayout mLayout;

    boolean mFinishBaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseservice_activity);
        boolean isFirst = JHLocalSettingUtils.getBooleanLocalSetting(this, WGConstants.WGLocalSettingWelcome);
        if (!isFirst) {
            JHLocalSettingUtils.setBooleanLocalSetting(this, WGConstants.WGLocalSettingWelcome, true);
            mLayout = (WelcomeCoordinatorLayout) findViewById(R.id.coordinator);
            mLayout.addPage(R.layout.welcome_00, R.layout.welcome_01, R.layout.welcome_02);
            mLayout.setOnPageScrollListener(new WelcomeCoordinatorLayout.OnPageScrollListener() {
                @Override
                public void onScrollPage(View v, float progress, float maximum) {

                }

                @Override
                public void onPageSelected(View v, int pageSelected) {
                    handlePageSelected(pageSelected);
                }
            });
        }
        this.loadBaseService();
        Intent intent = new Intent(WGBaseServiceActivity.this, WGMainActivity.class);
        //startActivity(intent);
    }

    void handlePageSelected(int pageSelected) {
        if (pageSelected == mLayout.getNumOfPages() - 1) {
            mLayout.setVisibility(View.GONE);
            if (mFinishBaseService) {
                handleToMainActivity();
            }
        }
    }

    void loadBaseService() {
        WGBaseServiceRequest request = new WGBaseServiceRequest();
        request.showsLoadingView = false;
        this.postAsyn(request, WGBaseServiceResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleBaseServiceCompletion((WGBaseServiceResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleBaseServiceCompletion(WGBaseServiceResponse response) {
        if (response != null) {
            mFinishBaseService = true;
            WGApplicationGlobalUtils.getInstance().setBaseService(response.data);
            if (response.success()) {
                Log.e("onSuccess", JSON.toJSONString(response));
                if (mLayout == null || mLayout.getVisibility() == View.GONE) {
                    handleToMainActivity();
                }
            }
        }
    }

    void handleToMainActivity() {
        Intent intent = new Intent(WGBaseServiceActivity.this, WGMainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
