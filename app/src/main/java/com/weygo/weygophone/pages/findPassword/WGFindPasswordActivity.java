package com.weygo.weygophone.pages.findPassword;

import android.os.Handler;
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
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.findPassword.model.request.WGFindPasswordRequest;
import com.weygo.weygophone.pages.findPassword.model.response.WGFindPasswordResponse;
import com.weygo.weygophone.pages.register.model.request.WGRegisterRequest;
import com.weygo.weygophone.pages.register.model.response.WGRegisterResponse;

/**
 * Created by muma on 2017/5/18.
 */

public class WGFindPasswordActivity extends WGBaseActivity {

    EditText mPhoneEditText;

    EditText mCodeEditText;

    EditText mPasswordEditText;

    EditText mConfirmPWEditText;

    TextView mCodeBtn;

    Button mFindPWBtn;

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
    public void initContentView() {
        setContentView(R.layout.wgfindpassword_activity);
    }

    @Override
    public void initSubView() {
        super.initSubView();

        mPhoneEditText = (EditText) findViewById(R.id.phoneEditText);

        mCodeEditText = (EditText) findViewById(R.id.codeEditText);

        mPasswordEditText = (EditText) findViewById(R.id.passwordEditText);

        mConfirmPWEditText = (EditText) findViewById(R.id.confirmPasswordEditText);

        mCodeBtn = (TextView) findViewById(R.id.codeTextView);
        mCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSendCode();
            }
        });

        mFindPWBtn = (Button) findViewById(R.id.findPWBtn);
        mFindPWBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFindPassword();
            }
        });
    }

    void loadFindPassword() {
        WGFindPasswordRequest request = new WGFindPasswordRequest();
        request.username = mPhoneEditText.getText().toString();
        request.verifyCode = mCodeEditText.getText().toString();
        request.password = mPasswordEditText.getText().toString();
        request.confirmPassword = mConfirmPWEditText.getText().toString();
        this.postAsyn(request, WGFindPasswordResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleFindPasswordSuccessResponse((WGFindPasswordResponse )response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleFindPasswordSuccessResponse(WGFindPasswordResponse response) {
        if (response.success()) {
            handleReturn();
        }
        else {
            showWarning(response.message);
        }
    }

    void handleSendCode() {
        mCodeBtn.setEnabled(false);
        mHandler.postDelayed(mRunnable, 1000);
        WGApplicationRequestUtils.getInstance().
                loadSendVerificationCode(this, mPhoneEditText.getText().toString(),
                        mCodeEditText.getText().toString(),
                        null);
    }

    void handleTimer() {
        if (mCurrentSecond == mMaxSeconds) {
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
        mCurrentSecond++;
    }
}
