package com.weygo.weygophone.pages.bind.fragment;

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
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.base.WGInterface;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.bind.model.WGBindData;
import com.weygo.weygophone.pages.bind.model.request.WGBindUnRegisteredRequest;
import com.weygo.weygophone.pages.bind.model.response.WGBindUnRegisteredResponse;
import com.weygo.weygophone.pages.register.model.request.WGRegisterRequest;
import com.weygo.weygophone.pages.register.model.response.WGRegisterResponse;
import com.weygo.weygophone.pages.search.fragment.WGSearchResultFragment;

/**
 * Created by muma on 2017/10/10.
 */

public class WGBindUnRegisterFragment extends WGFragment {

    EditText mPhoneEditText;

    EditText mCodeEditText;

    EditText mLastNameEditText;

    EditText mFirstNameEditText;

    EditText mEmailEditText;

    EditText mPasswordEditText;

    EditText mConfirmPWEditText;

    TextView mCodeBtn;

    Button mLoginBtn;

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

    public interface OnListener extends WGInterface {
        void onBind(WGBindUnRegisteredRequest request);
    }
    OnListener mListener;
    public void setListener(OnListener listener) {
        mListener = listener;
    }

    @Override
    public int fragmentResId() {
        return R.layout.bind_register_fragment;
    }

    @Override
    public void initSubView(View view) {

        super.initSubView(view);

        mPhoneEditText = (EditText) view.findViewById(R.id.phoneEditText);

        mCodeEditText = (EditText) view.findViewById(R.id.codeEditText);

        mLastNameEditText = (EditText) view.findViewById(R.id.lastNameEditText);

        mFirstNameEditText = (EditText) view.findViewById(R.id.firstNameEditText);

        mEmailEditText = (EditText) view.findViewById(R.id.emailEditText);

        mPasswordEditText = (EditText) view.findViewById(R.id.passwordEditText);

        mConfirmPWEditText = (EditText) view.findViewById(R.id.confirmPasswordEditText);

        mCodeBtn = (TextView) view.findViewById(R.id.codeTextView);
        mCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleSendCode();
            }
        });

        mLoginBtn = (Button) view.findViewById(R.id.loginBtn);
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleBind();
            }
        });
    }

    void handleBind() {
        if (mListener != null) {
            WGBindUnRegisteredRequest request = new WGBindUnRegisteredRequest();
            request.username = mPhoneEditText.getText().toString();
            request.verifyCode = mCodeEditText.getText().toString();
            request.password = mPasswordEditText.getText().toString();
            request.confirmPassword = mConfirmPWEditText.getText().toString();
            request.firstname = mFirstNameEditText.getText().toString();
            request.lastname = mLastNameEditText.getText().toString();
            request.email = mEmailEditText.getText().toString();
            mListener.onBind(request);
        }
    }

    void handleSendCode() {
        mCurrentSecond = mMaxSeconds;
        mCodeBtn.setEnabled(false);
        mHandler.postDelayed(mRunnable, 1000);
        WGApplicationRequestUtils.getInstance().
                loadSendVerificationCode(getActivity(), mPhoneEditText.getText().toString(),
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
