package com.weygo.weygophone.pages.setting;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.tools.JHDeviceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.widget.WGCellStyle4View;
import com.weygo.weygophone.logic.WGChangeAppLanguageLogic;
import com.weygo.weygophone.logic.WGChangeLanguageCallBack;

import java.util.Locale;

/**
 * Created by muma on 2017/5/18.
 */

public class WGSettingActivity extends WGTitleActivity {

    WGCellStyle4View mPostCapView;

    WGCellStyle4View mLanguageView;

    WGCellStyle4View mVersionView;

    WGCellStyle4View mCleanCacheView;

    TextView mLogoutBtn;

    @Override
    public void initContentView() {
        setContentView(R.layout.wgsetting_activity);
    }

    @Override
    public void initSubView() {
        super.initSubView();

        mNavigationBar.setTitle(R.string.Setting_Title);

        mPostCapView = (WGCellStyle4View) findViewById(R.id.postCodeView);
        mPostCapView.setTitle(R.string.Setting_UserInfo);
        mPostCapView.setStyle(WGCellStyle4View.WGCellStyleTextAndDetailText);
        mPostCapView.setDetailTitle(WGApplicationUserUtils.getInstance().currentPostCode());
        mPostCapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePostCap();
            }
        });

        mLanguageView = (WGCellStyle4View) findViewById(R.id.languageView);
        mLanguageView.setTitle(R.string.Setting_SetLanguage);
        mLanguageView.setStyle(WGCellStyle4View.WGCellStyleTextAndDetailAndArr);
        mLanguageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLanguage();
            }
        });

        mVersionView = (WGCellStyle4View) findViewById(R.id.versionView);
        mVersionView.setTitle(R.string.Setting_Version);
        mVersionView.setDetailTitle(JHDeviceUtils.getInstance().getVersion());
        mVersionView.setStyle(WGCellStyle4View.WGCellStyleTextAndDetailAndArr);

        mCleanCacheView = (WGCellStyle4View) findViewById(R.id.cleanCacheView);
        mCleanCacheView.setTitle(R.string.Setting_CleanCache);
        mCleanCacheView.setStyle(WGCellStyle4View.WGCellStyleOnlyText);
        mCleanCacheView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCleanCache();
            }
        });

        mLogoutBtn = (TextView) findViewById(R.id.loginBtn);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLogout();
            }
        });
    }

    void handlePostCap() {

    }

    void handleLanguage() {
        WGChangeAppLanguageLogic.changeAppLanguage(this, WGChangeAppLanguageLogic.WGAppLanguageItalin,
                new WGChangeLanguageCallBack() {
                    @Override
                    public void onCompletion(Locale currentLocal, boolean changed) {
                        restartApplication();
                    }
                });
    }

    private void restartApplication() {
        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    void handleCleanCache() {

    }

    void handleLogout() {

    }
}
