package com.weygo.weygophone.pages.register;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.bind.WGBindActivity;
import com.weygo.weygophone.pages.bind.model.WGBindData;
import com.weygo.weygophone.pages.login.WGLoginActivity;
import com.weygo.weygophone.pages.register.model.request.WGRegisterRequest;
import com.weygo.weygophone.pages.register.model.response.WGGetVerificationCodeResponse;
import com.weygo.weygophone.pages.register.model.response.WGRegisterResponse;

import org.w3c.dom.Text;

import java.util.Arrays;

/**
 * Created by muma on 2017/5/17.
 */

public class WGRegisterActivity extends WGBaseActivity {

    EditText mPhoneEditText;

    EditText mCodeEditText;

    EditText mLastNameEditText;

    EditText mFirstNameEditText;

    EditText mEmailEditText;

    EditText mPasswordEditText;

    EditText mConfirmPWEditText;

    TextView mCodeBtn;

    Button mRegisterBtn;

    Button mLoginBtn;

    TextView mRightLoginTextView;

    ImageView mReturnImageView;

    View mWechatView;

    View mFacebookView;

    CallbackManager mCallbackManager;

    Handler mHandler=new Handler();
    Runnable mRunnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            handleTimer();
        }
    };
    int mMaxSeconds = 60;
    int mCurrentSecond = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgregister_activity);
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

        mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);

        mCodeEditText = (EditText) findViewById(R.id.codeEditText);

        mLastNameEditText = (EditText) findViewById(R.id.lastNameEditText);

        mFirstNameEditText = (EditText) findViewById(R.id.firstNameEditText);

        mEmailEditText = (EditText) findViewById(R.id.emailEditText);

        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);

        mConfirmPWEditText = (EditText) findViewById(R.id.confirmPasswordEditText);

        mCodeBtn = (TextView) findViewById(R.id.codeTextView);
        mCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSendCode();
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
                loadRegister();
            }
        });

        mLoginBtn = (Button) findViewById(R.id.loginBtn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
            }
        });

        mRightLoginTextView = (TextView) findViewById(R.id.loginTextView);
        mRightLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogin();
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

    void handleLogin() {
        Intent intent = new Intent(WGRegisterActivity.this, WGLoginActivity.class);
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

    void loadRegister() {
        WGRegisterRequest request = new WGRegisterRequest();
        request.username = mPhoneEditText.getText().toString();
        request.verifyCode = mCodeEditText.getText().toString();
        request.password = mPasswordEditText.getText().toString();
        request.confirmPassword = mConfirmPWEditText.getText().toString();
        request.firstname = mFirstNameEditText.getText().toString();
        request.lastname = mLastNameEditText.getText().toString();
        request.email = mEmailEditText.getText().toString();
        this.postAsyn(request, WGRegisterResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleRegisterSuccessResponse((WGRegisterResponse )response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleRegisterSuccessResponse(WGRegisterResponse response) {
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

    void handleSendCode() {
        mCurrentSecond = mMaxSeconds;
        mCodeBtn.setEnabled(false);
        mHandler.postDelayed(mRunnable, 1000);
        WGApplicationRequestUtils.getInstance().
                loadSendVerificationCode(this, mPhoneEditText.getText().toString(),
                        mCodeEditText.getText().toString(),
                        null);
    }

    void handleTimer() {
        if (mCurrentSecond == 0) {
            mCodeBtn.setEnabled(true);
            mCurrentSecond = 0;
            mCodeBtn.setText(R.string.Register_GetCodeBtn);
            mHandler.removeCallbacks(mRunnable);
        }
        else {
            mCodeBtn.setEnabled(false);
            mCodeBtn.setText(mCurrentSecond + "s");
            mHandler.postDelayed(mRunnable, 1000);
        }
        mCurrentSecond--;
    }
}
