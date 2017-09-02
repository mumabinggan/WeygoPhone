package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.weygo.common.widget.JHPopView;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;

/**
 * Created by muma on 2017/8/17.
 */

public class WGPostPopView extends JHPopView {

    EditText mCapET;

    TextView mCapErrorTV;

    Button mBtn;

    public interface OnItemListener {
        void onSuccess();
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGPostPopView(Context context) {
        super(context);
    }

    public WGPostPopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGPostPopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCapErrorTV = (TextView) findViewById(R.id.capErrorTV);
        mCapErrorTV.setVisibility(INVISIBLE);
        mCapET = (EditText) findViewById(R.id.inputCapET);
        mBtn = (Button) findViewById(R.id.confirmBtn);
        mBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCap();
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClose();
            }
        });
    }

    void handleClose() {
        mPopupWindow.dismiss();;
    }

    void handleCap() {
        String cap = mCapET.getText().toString();
        if (WGApplicationGlobalUtils.getInstance().supportCap(cap)) {
            if (WGApplicationUserUtils.getInstance().isLogined()) {
                WGApplicationRequestUtils.getInstance().loadSetPostCodeRequest(cap, new WGApplicationRequestUtils.WGOnCompletionInteface() {
                    @Override
                    public void onSuccessCompletion(WGResponse response) {
                        mPopupWindow.dismiss();
                    }

                    @Override
                    public void onFailCompletion(WGResponse response) {

                    }
                });
            }
            else {
                WGApplicationUserUtils.getInstance().setCurrentPostCode(mCapET.getText().toString());
                mCapErrorTV.setVisibility(INVISIBLE);
                mPopupWindow.dismiss();
            }
        }
        else {
            mCapErrorTV.setVisibility(VISIBLE);
        }
    }
}
