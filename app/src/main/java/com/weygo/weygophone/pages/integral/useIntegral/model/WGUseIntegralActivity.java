package com.weygo.weygophone.pages.integral.useIntegral.model;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.pages.integral.myIntegral.model.WGIntegration;

/**
 * Created by muma on 2017/5/20.
 */

public class WGUseIntegralActivity extends WGTitleActivity {

    TextView mIntergalInfoTextView;

    Button mUseBtn;

    WGIntegration mIntegration;

    @Override
    public void initContentView() {
        setContentView(R.layout.wguseintegral_activity);
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

            }
        });
    }

    void handleUseIntergal() {
        if (mIntegration.hasUsed()) {

        }
        else {

        }
    }


}
