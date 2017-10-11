package com.weygo.weygophone.pages.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.bind.WGBindActivity;
import com.weygo.weygophone.pages.bind.model.WGBindData;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.login.model.request.WGLoginRequest;
import com.weygo.weygophone.pages.login.model.response.WGLoginResponse;
import com.weygo.weygophone.pages.register.WGRegisterActivity;

import java.util.Arrays;

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

    CallbackManager mCallbackManager;

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
        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager,new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                String userId = accessToken.getUserId();
                handleBind(userId);
            }
            @Override
            public void onCancel() {
                // App code
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
                if (exception instanceof FacebookAuthorizationException) {
                    LoginManager.getInstance().logOut();
                    // TODO：
                }
            }
        });
    }

    @Override
    public void initSubView() {
        super.initSubView();

        mPhoneEditText = (EditText) findViewById(R.id.usernameEditText);

        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);

        mForgetPWBtn = (TextView) findViewById(R.id.forgetPWTextView);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mCallbackManager != null) {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    void handleFacebook() {
        final String PERMISSION = "public_profile";
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.setDefaultAudience(loginManager.getDefaultAudience());
        loginManager.setLoginBehavior(loginManager.getLoginBehavior());
        loginManager.logInWithReadPermissions(this, Arrays.asList(PERMISSION));
    }

    void handleBind(String id) {
        Intent intent = new Intent(this, WGBindActivity.class);
        Bundle bundle = new Bundle();
        WGBindData item = new WGBindData();
        item.type = WGConstants.WGBindTypeFaceBook;
        item.uniqueId = id;
        bundle.putSerializable(WGConstants.WGIntentDataKey, item);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void loadLogin() {
        WGLoginRequest request = new WGLoginRequest();
        request.username = mPhoneEditText.getText().toString();
        request.password = mPasswordEditText.getText().toString();
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
            WGApplicationUserUtils.getInstance().setUser(response.data);
            sendRefreshNotification(WGConstants.WGRefreshNotificationTypeLogin);
            sendNotification(WGConstants.WGNotificationTypeLogin);
            finish();
        }
        else {
            showWarning(response.message);
        }
    }
}
