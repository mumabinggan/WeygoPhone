package com.weygo.weygophone.base;

import android.os.Bundle;
import android.widget.Toast;

import com.weygo.common.base.JHActivity;
import com.weygo.common.widget.JHNavigationBar;
import com.weygo.weygophone.R;
import com.weygo.weygophone.logic.WGChangeAppLanguageLogic;
import com.weygo.weygophone.logic.WGChangeLanguageCallBack;

import java.util.Locale;

/**
 * Created by muma on 2016/12/14.
 */

public class WGBaseActivity extends JHActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //change app language
        WGChangeAppLanguageLogic.resetAppLanguage(this, new WGChangeLanguageCallBack() {
            @Override
            public void onCompletion(Locale currentLocal, boolean changed) {

            }
        });
    }

    @Override
    public boolean useActivityCollector() {
        return false;
    }

    public void showWarning(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }
}
