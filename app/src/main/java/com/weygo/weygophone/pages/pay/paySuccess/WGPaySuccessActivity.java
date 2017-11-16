package com.weygo.weygophone.pages.pay.paySuccess;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.pay.paySuccess.model.WGPaySuccessData;
import com.weygo.weygophone.pages.pay.paySuccess.model.request.WGPaySuccessRequest;
import com.weygo.weygophone.pages.pay.paySuccess.model.response.WGPaySuccessResponse;

import java.io.Serializable;

/**
 * Created by muma on 2017/8/27.
 */

public class WGPaySuccessActivity extends WGTitleActivity {

    String mOrderId;

    WGPaySuccessData mPaySuccessData;

    TextView mOrderTitleTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (JHStringUtils.isNullOrEmpty(mOrderId)) {
            loadPaySuccess();
        }
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgpaysuccess_activity);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp != null && temp instanceof String) {
                mOrderId = (String)temp;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mOrderTitleTV = (TextView) findViewById(R.id.orderTitle1TV);
        mOrderTitleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoHome();
            }
        });
        if (!JHStringUtils.isNullOrEmpty(mOrderId)) {
            mOrderTitleTV.setText(JHResourceUtils.getInstance().getString(R.string.PaySuccess_SubTitle2)
                    + mOrderId);
        }
    }

    void loadPaySuccess() {
        WGPaySuccessRequest request = new WGPaySuccessRequest();
        this.postAsyn(request, WGPaySuccessResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                loadPaySuccess((WGPaySuccessResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void loadPaySuccess(WGPaySuccessResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mPaySuccessData = response.data;
            mOrderTitleTV.setText(JHResourceUtils.getInstance().getString(R.string.PaySuccess_SubTitle2)
                    + mPaySuccessData.orderId);
        }
        else {
            showWarning(response.message);
        }
    }
}
