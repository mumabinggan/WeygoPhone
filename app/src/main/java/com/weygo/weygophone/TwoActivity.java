package com.weygo.weygophone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.weygo.common.tools.JHLanguageUtils;
import com.weygo.weygophone.base.WGBaseActivity;

import java.util.Locale;

/**
 * Created by muma on 2016/12/14.
 */

public class TwoActivity extends WGBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wgmain);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JHLanguageUtils.setAppLanguage(TwoActivity.this, Locale.ITALY);
                TextView textView = (TextView) findViewById(R.id.textView);
                //textView.setText(R.string.hello);
                TwoActivity.this.finish();
                //WGMainActivity.this.recreate();
            }
        });
    }
}
