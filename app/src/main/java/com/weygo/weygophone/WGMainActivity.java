package com.weygo.weygophone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.weygo.common.base.JHFragment;
import com.weygo.common.base.JHObject;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHDeviceUtils;
import com.weygo.common.widget.JHTabBar;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.WGOnUserInfoCompletionInteface;
import com.weygo.weygophone.common.widget.WGDatePickerView;
import com.weygo.weygophone.common.widget.WGOptionPickerView;
import com.weygo.weygophone.models.JHTests;
import com.weygo.weygophone.pages.base.model.request.WGBaseServiceRequest;
import com.weygo.weygophone.pages.base.model.request.WGUpdateData;
import com.weygo.weygophone.pages.base.model.request.WGUpdateRequest;
import com.weygo.weygophone.pages.base.model.response.WGBaseServiceResponse;
import com.weygo.weygophone.pages.base.model.response.WGUpdateResponse;
import com.weygo.weygophone.pages.clientCenter.detail.WGClientServiceDetailActivity;
import com.weygo.weygophone.pages.clientCenter.list.WGClientServiceActivity;
import com.weygo.weygophone.pages.clientCenter.list.model.WGClientServiceItem;
import com.weygo.weygophone.pages.collection.WGCollectionActivity;
import com.weygo.weygophone.pages.findPassword.WGFindPasswordActivity;
import com.weygo.weygophone.pages.footprint.WGFootprintActivity;
import com.weygo.weygophone.pages.goodDetail.WGGoodDetailActivity;
import com.weygo.weygophone.pages.goodDetail.WGTestViewPickerActivity;
import com.weygo.weygophone.pages.integral.myIntegral.WGMyIntegralActivity;
import com.weygo.weygophone.pages.login.WGLoginActivity;
import com.weygo.weygophone.pages.order.detail.WGOrderDetailActivity;
import com.weygo.weygophone.pages.order.list.WGOrderListActivity;
import com.weygo.weygophone.pages.personInfo.WGPersonInfoActivity;
import com.weygo.weygophone.pages.register.WGRegisterActivity;
import com.weygo.weygophone.pages.search.widget.WGShopCartView;
import com.weygo.weygophone.pages.setting.WGSettingActivity;
import com.weygo.weygophone.pages.slider.WGSliderFragmet;
import com.weygo.weygophone.pages.slider.model.SliderOnItemClickListener;
import com.weygo.weygophone.pages.tabs.benefit.fragment.WGTabBenefitFragment;
import com.weygo.weygophone.pages.tabs.classify.fragment.WGTabClassifyFragment;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.classify.model.request.WGClassifyRequest;
import com.weygo.weygophone.pages.tabs.classify.model.response.WGClassifyResponse;
import com.weygo.weygophone.pages.tabs.foreign.fragment.WGTabAsiaFragment;
import com.weygo.weygophone.pages.tabs.home.fragment.WGTabHomeFragment;
import com.weygo.common.tools.loadwebimage.JHImageLoaderEntity;
import com.weygo.common.tools.loadwebimage.JHImageLoaderListener;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;
import com.weygo.weygophone.pages.tabs.mine.fragment.WGTabMineFragment;
import com.weygo.weygophone.pages.tabs.mine.model.response.WGUserInfoResponse;
import com.weygo.weygophone.pages.test.WGTestRequest;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.NumberPicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;

public class WGMainActivity extends WGBaseActivity {

    private JHTabBar mTabBar;

    private DrawerLayout mDrawerLayout;
    private WGSliderFragmet mSliderFragment;

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    WGReLoginBroadcastReceiver mReLoginReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wgmain_main_activity);
        overridePendingTransition(R.anim.trans_main, R.anim.trans_main);
        Log.e("mainActivity", "fssss");
        //this.testFonts();
        //this.getAsyn();
        //printField();
        boolean s = true;
        String ss = s + "";
        //Log.e("fasdfas = ", ss);
        //this.testArrayToJson();
        //this.testProperty();
        //this.testLocalSetting();
        //this.testImageLoader();
        //this.testLanguageSwitch();
        this.initTabBar();
        this.initDrawerLayout();
        this.initReLoginReceiver();
        this.initSlider();

        //this.testPrintDic();
        //this.testModelToMap();
        //this.testPostClassifyGetRequest();
        getWindow();

        Log.e("==versioname==", JHDeviceUtils.getInstance().getVersionName());
        if (WGApplicationUserUtils.getInstance().isLogined()) {
            WGApplicationRequestUtils.getInstance().loadUserInfoOnCompletion(this, new WGOnUserInfoCompletionInteface() {
                @Override
                public void onUserInfoCompletion(WGUserInfoResponse response) {
                    WGApplicationRequestUtils.getInstance().loadShopCartCount(new WGApplicationRequestUtils.WGOnCompletionInteface() {
                        @Override
                        public void onSuccessCompletion(WGResponse response) {
                            handleShopCartCount();
                        }

                        @Override
                        public void onFailCompletion(WGResponse response) {

                        }
                    });
                }
            });
        }

        checkAppUpdate();
    }

    void initTabBar() {
        mTabBar = (JHTabBar) findViewById(R.id.main_frame);
        mTabBar.setActivity(this);
        int[] drawables = { R.drawable.tabbar_home_drawable,
                R.drawable.tabbar_classify_drawable,
                R.drawable.tabbar_benefit_drawable,
                R.drawable.tabbar_foreign_drawable,
                R.drawable.tabbar_mine_drawable};
        mTabBar.setDrawables(drawables);
        mTabBar.setTitleIdArray(Arrays.asList(R.string.main_home,
                R.string.main_classify, R.string.main_benefit,
                R.string.main_foreign, R.string.main_mine));
        List<Class> fragmentClassArray = new ArrayList<>();
        fragmentClassArray.add(WGTabHomeFragment.class);
        fragmentClassArray.add(WGTabClassifyFragment.class);
        fragmentClassArray.add(WGTabBenefitFragment.class);
        fragmentClassArray.add(WGTabAsiaFragment.class);
        fragmentClassArray.add(WGTabMineFragment.class);
        mTabBar.setFragmentClassArray(fragmentClassArray);
        mTabBar.setSelectIndex(0);
    }

    void handleShopCartCount() {
        WGApplicationRequestUtils.getInstance().loadShopCartCount(new WGApplicationRequestUtils.WGOnCompletionInteface() {
            @Override
            public void onSuccessCompletion(WGResponse response) {

            }

            @Override
            public void onFailCompletion(WGResponse response) {

            }
        });
    }

    public void didReceivedNotification(int notification, JHObject data) {
        if (notification == WGConstants.WGNotificationTypeHomeTab) {
            mTabBar.setSelectIndex(0);
            WGTabHomeFragment fragment = (WGTabHomeFragment) mTabBar.getSelectFragement(0);
            if (fragment != null) {
                fragment.handleSwitchHomeItemTab((WGHomeFloorContentClassifyItem)data);
            }
        }
        else if (notification == WGConstants.WGNotificationTypeBenefitTab) {
            mTabBar.setSelectIndex(2);
            WGTabBenefitFragment fragment = (WGTabBenefitFragment) mTabBar.getSelectFragement(2);
            if (fragment != null) {
                fragment.handleSwitchHomeItemTab((WGHomeFloorContentClassifyItem)data);
            }
        }
    }

    void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_wgmain);
    }

    void initSlider() {
        mSliderFragment = (WGSliderFragmet) getSupportFragmentManager().
                findFragmentById(R.id.slider_fragment);
    }

    class WGReLoginBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleReLogin();
        }
    }

    void handleReLogin() {
        WGApplicationUserUtils.getInstance().reset();
        gotoHome();
        mTabBar.setSelectIndex(0);
    }

    void initReLoginReceiver() {
        if (mReLoginReceiver == null) {
            mReLoginReceiver = new WGReLoginBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(WGConstants.WGReLoginAction);
            registerReceiver(mReLoginReceiver, filter);
        }
    }

    void checkAppUpdate() {
        WGUpdateRequest request = new WGUpdateRequest();
        this.postAsyn(request, WGUpdateResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleAppUpdateResponse((WGUpdateResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleAppUpdateResponse(final WGUpdateResponse response) {
        if (response.success() && response.data != null) {
            if (response.data.versionStatus == 1) {
                String [] newArray = response.data.versionCode.split(".");
                if (newArray.length == 3) {
                    int newVersionCode = Integer.parseInt(newArray[0]) * 1000 + Integer.parseInt(newArray[1]) * 100 + Integer.parseInt(newArray[2]);
                    String kAppVersion = JHDeviceUtils.getInstance().getVersionCode();
                    String [] oldArray = kAppVersion.split(".");
                    int oldVersionCode = Integer.parseInt(oldArray[0]) * 1000 + Integer.parseInt(oldArray[1]) * 100 + Integer.parseInt(oldArray[2]);
                    if (newVersionCode > oldVersionCode) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
                        builder.setMessage(response.data.updateTips);
                        builder.setPositiveButton(R.string.Mine_Logout_Ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                handleConfirmUpdate(response.data);
                            }
                        });
                        builder.setNegativeButton(R.string.Mine_Logout_Cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            }
        }
    }

    void handleConfirmUpdate(WGUpdateData data) {
        if (!isHaveMarket(this)) {
            Toast.makeText(this, "您手机中没有安装应用市场！", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(data.linkUrl));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private static boolean isHaveMarket(Context context) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.APP_MARKET");
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
        return infos.size() > 0;
    }

    public void onOptionPicker(View view) {
        OptionPicker picker = new WGOptionPickerView(this, new String[]{
                "第一项", "第二项", "这是一个很", "长很长很长很长", "很长很长很长很长","很长的很长很长的很长很长的项"
        });
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                Log.e("onOptionPicked", "" + index + "item" + "-----------");
                showWarning("index=" + index + ", item=" + item);
            }
        });
        picker.show();
    }

    void setA() {
        final DatePicker picker = new WGDatePickerView(this);
        //picker.getTitleView().setVisibility(View.v);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                showWarning(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                //picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                //picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                //picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    public void openSlider() {
        //setA();
        //sss();
        //onOptionPicker(null);
//        Intent intent = new Intent(WGMainActivity.this, WGGoodDetailActivity.class);
//        startActivity(intent);
//        Intent intent = new Intent(WGMainActivity.this, WGFindPasswordActivity.class);
//        Bundle mBundle = new Bundle();
//        WGClientServiceItem item = new WGClientServiceItem();
//        item.name = "郑渊谦";
//        item.url = "http://baidu.com";
//        mBundle.putSerializable("key", item);
//        intent.putExtras(mBundle);
//        startActivity(intent);
//        Log.e("error", "testActivity");
        mDrawerLayout.openDrawer(GravityCompat.START);
        mSliderFragment.refresh();
    }

    public void closeSlider() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    public void setSelectedTab(int tabIndex) {
        mTabBar.setSelectIndex(tabIndex);
    }

    void testClassifyGetRequest() {
        WGClassifyRequest request = new WGClassifyRequest();
        request.is_hot = 1;
        this.getAsyn(request, WGClassifyResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void testPostClassifyGetRequest() {
        WGClassifyRequest request = new WGClassifyRequest();
        request.is_hot = 1;
        this.postAsyn(request, WGClassifyResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void testPostBaseServiceRequest() {
        WGBaseServiceRequest request = new WGBaseServiceRequest();
        this.postAsyn(request, WGBaseServiceResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void testRequest() {
        WGBaseServiceRequest request = new WGBaseServiceRequest();
        this.getAsyn(request, WGBaseServiceResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                Log.e("onSuccess", JSON.toJSONString(result));
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void testModelToMap() {
        WGTestRequest request = new WGTestRequest();
        request.mm = 23;
        request.ww = "zhengyuan";
        request.dob = 232323.23f;
        request.flo = 23.23f;
        String jsonString = JSON.toJSONString(request);
        Map<String, Object> map = JSON.parseObject(
                jsonString,new TypeReference<Map<String, Object>>(){} );
        Log.e("testModelToMap", map.toString());
        for (Map.Entry<String, Object> m :map.entrySet())  {
            boolean isObject = m.getValue() instanceof Number;
            if (isObject) {
                Log.e("is", "yes");
            }
            else {
                Log.e("not", "no");
            }
            Log.e("key", m.getKey().getClass().toString());
            Log.e("value", m.getValue().getClass().toString());
        }
    }

    void testPrintDic() {
        Map<String, String> map = new TreeMap<String, String>(
                new Comparator<String>() {
                    public int compare(String obj1, String obj2) {
                        // 降序排序
                        return obj1.compareTo(obj2);
                    }
                });

        //map.put("b", "ccccc");
        //map.put("d", "aaaaa");
        //map.put("c", "bbbbb");
        //map.put("a", "ddddd");
        HashMap<String, String> ffMap = new HashMap<String, String>();
        ffMap.put("sfff", JSON.toJSONString(12));
        ffMap.put("key", "b27e9cb9b51149dba8eb8d874f238767");
        ffMap.put("showsLoadingView", JSON.toJSONString(true));
        ffMap.put("timeoutInterval", JSON.toJSONString(0));
        map.putAll(ffMap);
        Log.e("testPrintDic", map.toString());
    }

    void testParcel() {
        JHTests s = new JHTests();
        Parcelable wrapped = Parcels.wrap(s);
        JHTests example = Parcels.unwrap(wrapped);
        Log.e("fdasdf : ", example.mm);
        Log.e("fdasdf : ", example.m + "");
        Log.e("fdasdf : ", example.ss + "");
    }

    void testLog() {
        Log.e("fasd", "f");
    }

    void testLanguageSwitch() {
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        // 应用用户选择语言
        config.locale = Locale.ITALY;
        resources.updateConfiguration(config, dm);
        //TextView textView = (TextView) findViewById(R.id.textView);
        //textView.setText(R.string.hello);

    }

//    void testFonts() {
//        TextView textView = (TextView) findViewById(R.id.textView);
//        Typeface face = JHFontUtils.getTypeface(this, JHFontUtils.JHFontType.JHFontOswaldHeavy);
//        textView.setTypeface(face);
//    }

//    void testLocalSetting() {
//        String name = "testLocalSetting_ss";
//        WGRequest ss = new WGRequest();
//        JHLocalSettingUtils.setLocalSetting(this, name, ss);
//        WGRequest object = (WGRequest) JHLocalSettingUtils.getLocalSetting(this, name, WGRequest.class);
//        System.out.println("---------------------" + object.key);
//    }
//
//    void testProperty() {
//        WGRequest ss = new WGRequest();
//        SFSF ff = new SFSF();
//        ff.m = 54;
//        System.out.println("------------------------");
//        System.out.println(JHClassUtils.getFiledsInfo(ss));
//    }
//
//    void testArrayToJson() {
//        String[] arr = {"asd","dfgd","asd","234"};
//        String jsonString2 = JSON.toJSONString(arr);
//        Log.e("haah", jsonString2);
//        int [] intArr = {1, 3, 34};
//        String jsonString = JSON.toJSONString(intArr);
//        Log.e("haah", jsonString);
//        int m = 12;
//        String jsonString3 = JSON.toJSONString(m);
//        Log.e("haha", jsonString3);
//        WGRequest ss = new WGRequest();
//        SFSF ff = new SFSF();
//        ff.m = 54;
//        SFSF ff1 = new SFSF();
//        ff1.m = 6454;
//        List<SFSF> list = new ArrayList<>();
//        list.add(ff);
//        list.add(ff1);
//        String jsonString4 = JSON.toJSONString(ss);
//        Log.e("hfad", jsonString4);
//        HashMap<String, String> ffMap = new HashMap<String, String>();
//        ffMap.put("sfff", JSON.toJSONString(ff));
//        ffMap.put("key", "b27e9cb9b51149dba8eb8d874f238767");
//        ffMap.put("showsLoadingView", JSON.toJSONString(true));
//        ffMap.put("timeoutInterval", JSON.toJSONString(0));
//        String jsonString5 = JSON.toJSONString(ffMap);
//        Log.e("hfad", jsonString5);
//        Log.e("hfad", JSON.toJSONString(ff));
//        WGRequest newRequest = JSON.parseObject(jsonString4, WGRequest.class);
//        Map<String, Object> userMap =
//                JSON.parseObject(jsonString4, new TypeReference<Map<String, Object>>() {});
//        for(Map.Entry<String, Object> entry:userMap.entrySet()){
//            System.out.println(entry.getKey()+"--->"+(String) entry.getValue());
//        }
//        System.out.println("-----------------------------");
//        System.out.println(userMap);
//    }
//
//    void printField() {
//        Class clazz = WGWeatherRequest.class;
//        Field[] fields = clazz.getFields();//根据Class对象获得属性 私有的也可以获得
//        for(Field f : fields) {
//            if (f.isSynthetic()) {
//                continue;
//            }
//            Log.e(f.getType().getName(), f.getName());//打印每个属性的类型名字
//        }
//    }
//
//    void getAsyn() {
//        WGWeatherRequest request = new WGWeatherRequest();
//        request.city = "beijing";
//        JHNetworkUtils.getAsyn(request, JHResponse.class, new JHResponseCallBack() {
//            @Override
//            public void onSuccess(JHResponse result) {
//
//            }
//
//            @Override
//            public void onFailure(JHRequestError error) {
//
//            }
//        });
//    }

    void testImageLoader() {
        ImageView imageView = null;

        JHImageLoaderEntity entity = new JHImageLoaderEntity(new JHImageLoaderEntity.Builder()
                .imageView(imageView)
                .imageUrl("http://d1l025sgu0zsw9.cloudfront.net/icons/alfabe_ikon/ABC.png")
                .listener(new JHImageLoaderListener() {
                    @Override
                    public void onStarted(String imageUri, View view) {
                        Log.w("haha", "onStarted");
                    }

                    @Override
                    public void onCompletion(String imageUri, View view, Bitmap loadedImage) {
                        Log.w("haha", "onCompletion");
                    }

                    @Override
                    public void onCancelled(String imageUri, View view) {
                        Log.w("haha", "onCancelled");
                    }

                    @Override
                    public void onFailed(String imageUri, View view, JHLoadWebImageFailReson failReason) {
                        Log.w("haha", "onFailed");
                    }
                }));
        JHImageUtils.getInstance().loadImage(getApplicationContext(), entity);
    }
}
