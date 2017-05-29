package com.weygo.weygophone.pages.integral.useIntegral;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.integral.myIntegral.model.WGIntegration;
import com.weygo.weygophone.pages.integral.useIntegral.model.request.WGIntegrationRequest;
import com.weygo.weygophone.pages.integral.useIntegral.model.request.WGUseIntegrationRequest;
import com.weygo.weygophone.pages.integral.useIntegral.model.response.WGIntegrationResponse;
import com.weygo.weygophone.pages.integral.useIntegral.model.response.WGUseIntegrationData;
import com.weygo.weygophone.pages.integral.useIntegral.model.response.WGUseIntegrationResponse;

/**
 * Created by muma on 2017/5/20.
 */

public class WGUseIntegrationActivity extends WGTitleActivity {

    TextView mIntergalInfoTextView;

    Button mUseBtn;

    WGIntegration mIntegration;

    @Override
    public void initContentView() {
        setContentView(R.layout.wguseintegration_activity);
        loadIntegration();
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.UseIntegral_Title);

        mIntergalInfoTextView = (TextView) findViewById(R.id.integralCountTextView);

        mUseBtn = (Button) findViewById(R.id.useBtn);
        mUseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUseIntegration();
            }
        });
    }

    void refreshUI() {
        String number1 = JHResourceUtils.getInstance().getString(R.string.UseIntegral_Count_1);
        String number2 = JHResourceUtils.getInstance().getString(R.string.UseIntegral_Count_2);
        String number3 = JHResourceUtils.getInstance().getString(R.string.UseIntegral_Count_3);
        String number4 = JHResourceUtils.getInstance().getString(R.string.UseIntegral_Count_4);
        mIntergalInfoTextView.setText(number1 + mIntegration.totalCount +
                number2 + mIntegration.currentCanUseCount + number3 + mIntegration.money + number4);
        if (mIntegration.hasUsed()) {
            mUseBtn.setText(R.string.UseIntegral_Cancel);
            mUseBtn.setBackgroundResource(R.color.WGAppBaseColor);
        }
        else {
            mUseBtn.setText(R.string.UseIntegral_OK);
            mUseBtn.setBackgroundResource(R.color.WGAppBlueButtonColor);
        }
    }

    void loadIntegration() {
        final WGIntegrationRequest request = new WGIntegrationRequest();
        this.postAsyn(request, WGUseIntegrationResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleIntegrationResponse((WGIntegrationResponse) response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleIntegrationResponse(WGIntegrationResponse response) {
        if (response.success()) {
            mIntegration = response.data;
            refreshUI();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadUseIntegration() {
        final int remove = mIntegration.hasUsed() ? 1 : 0;
        final WGUseIntegrationRequest request = new WGUseIntegrationRequest();
        request.remove = remove;
        this.postAsyn(request, WGUseIntegrationResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleUseIntegrationResponse((WGUseIntegrationResponse)response, mIntegration.hasUsed() ? 0 : 1);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleUseIntegrationResponse(WGUseIntegrationResponse response, int use) {
        if (response.success()) {
            WGUseIntegrationData data = new WGUseIntegrationData();
            data.price = response.data.price;
            data.use = use;
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable(WGConstants.WGIntentDataKey, data);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
        else {
            showWarning(response.message);
        }
    }
}
