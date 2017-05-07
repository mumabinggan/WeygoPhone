package com.weygo.weygophone;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.weygo.common.base.JHFragment;
import com.weygo.common.base.JHResponse;
import com.weygo.common.widget.JHTabBar;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.models.JHTests;
import com.weygo.weygophone.pages.base.model.request.WGBaseServiceRequest;
import com.weygo.weygophone.pages.base.model.response.WGBaseServiceResponse;
import com.weygo.weygophone.pages.slider.WGSliderActivity;
import com.weygo.weygophone.pages.slider.WGSliderFragmet;
import com.weygo.weygophone.pages.tabs.classify.Model.Request.WGClassifyRequest;
import com.weygo.weygophone.pages.tabs.classify.Model.Response.WGClassifyResponse;
import com.weygo.weygophone.pages.tabs.home.fragment.WGHomeFragment;
import com.weygo.common.tools.loadwebimage.JHImageLoaderEntity;
import com.weygo.common.tools.loadwebimage.JHImageLoaderListener;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
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

public class WGMainActivity extends WGBaseActivity {

    private JHTabBar mTabBar;

    private DrawerLayout mDrawerLayout;
    private WGSliderFragmet mSliderFragment;

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
        this.initSlider();

        //this.testPrintDic();
        //this.testModelToMap();
        //this.testPostClassifyGetRequest();
        getWindow();
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
        fragmentClassArray.add(WGHomeFragment.class);
        fragmentClassArray.add(JHFragment.class);
        fragmentClassArray.add(JHFragment.class);
        fragmentClassArray.add(JHFragment.class);
        fragmentClassArray.add(JHFragment.class);
        mTabBar.setFragmentClassArray(fragmentClassArray);
        mTabBar.setSelectIndex(0);
    }

    void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.activity_wgmain);
    }

    void initSlider() {
        mSliderFragment = (WGSliderFragmet) getSupportFragmentManager().
                findFragmentById(R.id.slider_fragment);
    }

    void initDrawerContent() {

    }

    public void testActivity() {
        Log.e("error", "testActivity");
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    void openSliderActivity() {
        Intent item = new Intent(WGMainActivity.this, WGSliderActivity.class);
        startActivity(item);
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
