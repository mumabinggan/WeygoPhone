package com.weygo.weygophone.pages.slider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGBaseActivity;

/**
 * Created by muma on 2017/4/6.
 */

public class WGSliderActivity extends WGBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wg_slider_main);
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_main);
        Log.e("asdfasdfad", "asdfasdfasd");
        View blackView = (View) findViewById(R.id.slider_blankView);
        blackView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overridePendingTransition(R.anim.trans_main, R.anim.trans_left_out);
                Intent intent = new Intent(WGSliderActivity.this, WGMainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        this.handleHiddenActivity();
    }

    private void handleHiddenActivity() {
        overridePendingTransition(R.anim.trans_main, R.anim.trans_left_out);
    }
}
