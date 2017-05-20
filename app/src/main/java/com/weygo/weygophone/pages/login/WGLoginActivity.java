package com.weygo.weygophone.pages.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.login.model.request.WGLoginRequest;
import com.weygo.weygophone.pages.login.model.response.WGLoginResponse;
import com.weygo.weygophone.pages.register.WGRegisterActivity;

/**
 * Created by muma on 2017/5/17.
 */

public class WGLoginActivity extends WGBaseActivity {

    EditText mPhoneEditText;

    EditText mPasswordEditText;

    TextView mForgetPWBtn;

    Button mRegisterBtn;

    Button mLoginBtn;

    TextView mRightRegisterTextView;

    ImageView mReturnImageView;

    View mWechatView;

    View mFacebookView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wglogin_activity);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initSubView() {
        super.initSubView();

        mPhoneEditText = (EditText) findViewById(R.id.usernameEditText);

        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);

        mForgetPWBtn = (TextView) findViewById(R.id.forgetPWTextView);

        mWechatView = findViewById(R.id.wechatView);
        mWechatView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleWechat();
            }
        });

        mFacebookView = findViewById(R.id.facebookView);
        mFacebookView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleFacebook();
            }
        });

        mRegisterBtn = (Button) findViewById(R.id.registerBtn);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegister();
            }
        });

        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadLogin();
            }
        });

        mRightRegisterTextView = (TextView) findViewById(R.id.registerTextView);
        mRightRegisterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegister();
            }
        });

        mReturnImageView = (ImageView) findViewById(R.id.returnImageView);
        mReturnImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleReturn();
            }
        });
    }

    void handleRegister() {
        Intent intent = new Intent(WGLoginActivity.this, WGRegisterActivity.class);
        startActivity(intent);
    }

    void handleWechat() {

    }

    void handleFacebook() {

    }

    void loadLogin() {
        WGLoginRequest request = new WGLoginRequest();
        this.postAsyn(request, WGLoginResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleLoginSuccessResponse((WGLoginResponse )response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleLoginSuccessResponse(WGLoginResponse response) {
        if (response.success()) {
            WGApplicationUserUtils.getInstance().reset();
            WGApplicationUserUtils.getInstance().setmUser(response.data);
            sendRefreshNotification(WGConstants.WGRefreshNotificationTypeLogin);
            sendNotification(WGConstants.WGNotificationTypeLogin);
            finish();
        }
        else {
            showWarning(response.message);
        }
    }
}
