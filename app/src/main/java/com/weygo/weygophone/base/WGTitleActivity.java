package com.weygo.weygophone.base;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.weygo.common.widget.JHNavigationBar;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2016/12/17.
 */

public class WGTitleActivity extends WGBaseActivity {
    protected JHNavigationBar mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        this.initNavigationBar();
    }

    protected void initNavigationBar() {
        mNavigationBar = (JHNavigationBar) findViewById(R.id.base_titlebar);
        if (mNavigationBar == null) return;
        mNavigationBar.setLeftBarListener(new JHNavigationBar.OnClickLeftBarListener() {
            @Override
            public void onClick(View var1) {
                finish();
            }
        });
        mNavigationBar.setRightBarListener(new JHNavigationBar.OnClickRightBarListener() {
            @Override
            public void onClick(View var1) {
                setNavigationRightBarAction();
            }
        });
    }

    public void setNavigationRightBarAction() {
        System.out.println("-----------------");
        Log.e("adsfas", "哎呀哎呀");
    }
}
