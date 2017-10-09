package com.weygo.weygophone.pages.setting;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHDeviceUtils;
import com.weygo.common.tools.JHLanguageUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGCellStyle4View;
import com.weygo.weygophone.common.widget.WGOptionPickerView;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.logic.WGChangeAppLanguageLogic;
import com.weygo.weygophone.logic.WGChangeLanguageCallBack;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementPayMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by muma on 2017/5/18.
 */

public class WGSettingActivity extends WGTitleActivity {

    WGCellStyle4View mPostCapView;

    WGCellStyle4View mLanguageView;

    WGCellStyle4View mVersionView;

    WGCellStyle4View mCleanCacheView;

    TextView mLogoutBtn;

    int m = 1;

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
        handleSelectedLanguage();
        mLanguageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleLanguage();
            }
        });

        mVersionView = (WGCellStyle4View) findViewById(R.id.versionView);
        mVersionView.setTitle(R.string.Setting_Version);
        mVersionView.setDetailTitle(JHDeviceUtils.getInstance().getVersionName());
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
        WGPostPopView popupView = (WGPostPopView) getLayoutInflater().inflate(R.layout.common_cap_pop, null);
        popupView.setListener(new WGPostPopView.OnItemListener() {
            @Override
            public void onSuccess() {
                mPostCapView.setDetailTitle(WGApplicationUserUtils.getInstance().currentPostCode());
            }
        });
        JHBasePopupWindow window = new JHBasePopupWindow(popupView,
                JHAdaptScreenUtils.devicePixelWidth(this),
                JHAdaptScreenUtils.devicePixelHeight(this));
        popupView.setPopupWindow(window);
        window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    void handleSelectedLanguage() {
        if (WGChangeAppLanguageLogic.isItalin()) {
            mLanguageView.setDetailTitle(R.string.Setting_Italiano);
        }
        else {
            mLanguageView.setDetailTitle(R.string.Setting_China);
        }
    }

    void handleLanguage() {
        List<String> list = new ArrayList();
        list.add(JHResourceUtils.getInstance().getString(R.string.Setting_China));
        list.add(JHResourceUtils.getInstance().getString(R.string.Setting_Italiano));
        if (list.size() > 0) {
            WGOptionPickerView picker = new WGOptionPickerView(this, list);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    handleSelectedLanguage(index, item);
                }
            });
            picker.show();
        }
    }

    void handleSelectedLanguage(int index, String title) {
        int language = 0;
        if (index == 1) {
            language = WGChangeAppLanguageLogic.WGAppLanguageItalin;
        }
        else {
            language = WGChangeAppLanguageLogic.WGAppLanguageChiness;
        }
        WGChangeAppLanguageLogic.changeAppLanguage(this, language,
                new WGChangeLanguageCallBack() {
                    @Override
                    public void onCompletion(Locale currentLocal, boolean changed) {
                        restartApplication();
                    }
                });
    }

    private void restartApplication() {
        handleSelectedLanguage();
        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    void handleCleanCache() {

    }

    void handleLogout() {
        sendNotification(WGConstants.WGNotificationTypeLogout);
        sendRefreshNotification(WGConstants.WGRefreshNotificationTypeLogout);
        finish();
    }
}
