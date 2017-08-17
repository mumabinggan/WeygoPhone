package com.weygo.weygophone.pages.base;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.pages.base.model.request.WGBaseServiceRequest;
import com.weygo.weygophone.pages.base.model.response.WGBaseServiceResponse;

/**
 * Created by muma on 2017/4/25.
 */

public class WGBaseServiceActivity extends WGBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baseservice_activity);
        this.loadBaseService();

        Intent intent = new Intent(WGBaseServiceActivity.this, WGMainActivity.class);
        startActivity(intent);
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
            WGApplicationGlobalUtils.getInstance().setBaseService(response.data);
            if (response.success()) {
                Log.e("onSuccess", JSON.toJSONString(response));
                Intent intent = new Intent(WGBaseServiceActivity.this, WGMainActivity.class);
                //startActivity(intent);
                finish();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
