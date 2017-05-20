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
    public void initContentView() {
        setContentView(R.layout.title_activity);
    }

    @Override
    public void initSubView() {
        super.initSubView();
        Log.e("titel","------------------");
        initNavigationBar();
    }

    protected void initNavigationBar() {
        mNavigationBar = (JHNavigationBar) findViewById(R.id.base_titlebar);
        mNavigationBar.setLeftBarListener(new JHNavigationBar.OnClickLeftBarListener() {
            @Override
            public void onClick(View var1) {
                handleReturn();
            }
        });
        mNavigationBar.setRightBarListener(new JHNavigationBar.OnClickRightBarListener() {
            @Override
            public void onClick(View var1) {
                handleRightBarItem();
            }
        });
    }

    public void handleRightBarItem() {

    }
}
