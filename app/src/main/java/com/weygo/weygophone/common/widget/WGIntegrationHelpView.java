package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weygo.common.widget.JHPopView;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/11/16.
 */

public class WGIntegrationHelpView extends JHPopView {

    TextView mContentTV;

    Button mCloseBtn;

    public WGIntegrationHelpView(Context context) {
        super(context);
    }

    public WGIntegrationHelpView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGIntegrationHelpView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void initConfig(Context context) {
        super.initConfig(context);
        mCanClose = false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mContentTV = (TextView) findViewById(R.id.contentTV);
        mCloseBtn = (Button) findViewById(R.id.confirmBtn);
        mCloseBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClose();
            }
        });
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClose();
            }
        });
        mContentTV.setMovementMethod(new ScrollingMovementMethod());
    }

    void handleClose() {
        mPopupWindow.dismiss();;
    }

    public void setHelpContent(String content) {
        mContentTV.setText(content);
    }
}
