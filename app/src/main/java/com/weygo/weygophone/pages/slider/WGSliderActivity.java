package com.weygo.weygophone.pages.slider;

import android.os.Bundle;

import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;

/**
 * Created by muma on 2017/4/6.
 */

public class WGSliderActivity extends WGBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_main);
    }

    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.trans_main, R.anim.trans_left_out);
    }
}
