package com.weygo.weygophone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.weygo.common.base.JHFragment;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHFontUtils;
import com.weygo.common.tools.JHLanguageUtils;
import com.weygo.common.widget.JHTabBar;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.request.SFSF;
import com.weygo.weygophone.request.WGRequest;
import com.weygo.weygophone.request.WGWeatherRequest;
import com.weygo.common.tools.JHClassUtils;
import com.weygo.common.tools.JHLocalSettingUtils;
import com.weygo.common.tools.loadwebimage.JHImageLoaderEntity;
import com.weygo.common.tools.loadwebimage.JHImageLoaderListener;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.common.tools.network.JHNetworkUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WGMainActivity extends WGBaseActivity {

    private JHTabBar mTabBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wgmain);

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
        this.initTabBar(this);
    }

    void initTabBar(Context context) {
        mTabBar = (JHTabBar) findViewById(R.id.main_frame);
        mTabBar.setActivity(this);
        int[] drawables = { R.drawable.tabbar_home_drawable,
                R.drawable.tabbar_classify_drawable,
                R.drawable.tabbar_benefit_drawable,
                R.drawable.tabbar_foreign_drawable,
                R.drawable.tabbar_mine_drawable};
        mTabBar.setDrawables(drawables);
        mTabBar.setTitleArray(Arrays.asList("首页", "分类", "优惠", "外国", "我的"));
        mTabBar.setFragmentClassArray(Arrays.asList(JHFragment.class,
                JHFragment.class, JHFragment.class,
                JHFragment.class, JHFragment.class));
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

    void testLocalSetting() {
        String name = "testLocalSetting_ss";
        WGRequest ss = new WGRequest();
        JHLocalSettingUtils.setLocalSetting(this, name, ss);
        WGRequest object = (WGRequest) JHLocalSettingUtils.getLocalSetting(this, name, WGRequest.class);
        System.out.println("---------------------" + object.key);
    }

    void testProperty() {
        WGRequest ss = new WGRequest();
        SFSF ff = new SFSF();
        ff.m = 54;
        System.out.println("------------------------");
        System.out.println(JHClassUtils.getFiledsInfo(ss));
    }

    void testArrayToJson() {
        String[] arr = {"asd","dfgd","asd","234"};
        String jsonString2 = JSON.toJSONString(arr);
        Log.e("haah", jsonString2);
        int [] intArr = {1, 3, 34};
        String jsonString = JSON.toJSONString(intArr);
        Log.e("haah", jsonString);
        int m = 12;
        String jsonString3 = JSON.toJSONString(m);
        Log.e("haha", jsonString3);
        WGRequest ss = new WGRequest();
        SFSF ff = new SFSF();
        ff.m = 54;
        SFSF ff1 = new SFSF();
        ff1.m = 6454;
        List<SFSF> list = new ArrayList<>();
        list.add(ff);
        list.add(ff1);
        String jsonString4 = JSON.toJSONString(ss);
        Log.e("hfad", jsonString4);
        HashMap<String, String> ffMap = new HashMap<String, String>();
        ffMap.put("sfff", JSON.toJSONString(ff));
        ffMap.put("key", "b27e9cb9b51149dba8eb8d874f238767");
        ffMap.put("showsLoadingView", JSON.toJSONString(true));
        ffMap.put("timeoutInterval", JSON.toJSONString(0));
        String jsonString5 = JSON.toJSONString(ffMap);
        Log.e("hfad", jsonString5);
        Log.e("hfad", JSON.toJSONString(ff));
        WGRequest newRequest = JSON.parseObject(jsonString4, WGRequest.class);
        Map<String, Object> userMap =
                JSON.parseObject(jsonString4, new TypeReference<Map<String, Object>>() {});
        for(Map.Entry<String, Object> entry:userMap.entrySet()){
            System.out.println(entry.getKey()+"--->"+(String) entry.getValue());
        }
        System.out.println("-----------------------------");
        System.out.println(userMap);
    }

    void printField() {
        Class clazz = WGWeatherRequest.class;
        Field[] fields = clazz.getFields();//根据Class对象获得属性 私有的也可以获得
        for(Field f : fields) {
            if (f.isSynthetic()) {
                continue;
            }
            Log.e(f.getType().getName(), f.getName());//打印每个属性的类型名字
        }
    }

    void getAsyn() {
        WGWeatherRequest request = new WGWeatherRequest();
        request.city = "beijing";
        JHNetworkUtils.getAsyn(request, JHResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {

            }

            @Override
            public void onFailure(JHRequestError error) {

            }
        });
    }

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
