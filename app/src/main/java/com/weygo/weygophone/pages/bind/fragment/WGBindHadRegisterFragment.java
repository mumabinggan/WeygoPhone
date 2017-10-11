package com.weygo.weygophone.pages.bind.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.base.WGInterface;
import com.weygo.weygophone.pages.bind.model.request.WGBindRegisteredRequest;
import com.weygo.weygophone.pages.bind.model.request.WGBindUnRegisteredRequest;

/**
 * Created by muma on 2017/10/10.
 */

public class WGBindHadRegisterFragment extends WGFragment {

    EditText mPhoneEditText;

    EditText mPasswordEditText;

    Button mLoginBtn;

    public interface OnListener extends WGInterface {
        void onBind(WGBindRegisteredRequest request);
    }
    OnListener mListener;
    public void setListener(OnListener listener) {
        mListener = listener;
    }

    @Override
    public int fragmentResId() {
        return R.layout.bind_login_fragment;
    }

    @Override
    public void initSubView(View view) {
        super.initSubView(view);
        mPhoneEditText = (EditText) view.findViewById(R.id.usernameEditText);
        mPasswordEditText = (EditText) view.findViewById(R.id.passwordEditText);
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
            WGBindRegisteredRequest request = new WGBindRegisteredRequest();
            request.username = mPhoneEditText.getText().toString();
            request.password = mPasswordEditText.getText().toString();
            mListener.onBind(request);
        }
    }
}
