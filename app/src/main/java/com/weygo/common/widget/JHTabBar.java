package com.weygo.common.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.common.base.JHActivity;
import com.weygo.common.base.JHFragment;
import com.weygo.common.tools.JHArrayUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.pages.login.WGLoginActivity;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muma on 2016/12/18.
 */

public class JHTabBar extends LinearLayout implements View.OnClickListener {

    // tabbar contentLayout container
    public FrameLayout mFrameLayout;

    // tabbar item id array
    List<Integer> mItemIdArray;

    // tabbar item array
    List<View> mItemArray;

    // main context
    Context mContext;

    // activity for get resources
    JHActivity mActivity;

    // tabbar content container item's Class map
    Map<Integer, Class> mFragmentClassMap;

    // fragment map, key : itemId; value : content container item's value
    Map<Integer, JHFragment> mFragmentMap;

    public JHTabBar(Context context) {
        this(context, null);
    }

    public JHTabBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public JHTabBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        Log.e("----------------", "-----------------");
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mItemIdArray = Arrays.asList(R.id.tabBar_home, R.id.tabBar_classify,
                R.id.tabBar_benefit, R.id.tabBar_foreign, R.id.tabBar_mine);

        mItemArray = new ArrayList();

        for (Integer id : mItemIdArray) {
            View item = findViewById(id);
            item.setOnClickListener(this);
            mItemArray.add(item);
        }

        mFrameLayout = (FrameLayout) findViewById(R.id.tabBar_frameLayout);
        Log.e("+++++++++++++++++", "+++++++++++++++++");
    }

    public void setActivity(JHActivity activity) {
        mActivity = activity;
    }

    public void setDrawables(int[] drawables) {
        for (int num = 0; num < mItemArray.size(); ++num) {
            View item = mItemArray.get(num);
            Drawable drawable = mContext.getResources().getDrawable(drawables[num]);
            if (item instanceof TextView) {
                TextView textView = (TextView) item;
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                textView.setCompoundDrawables(null, drawable, null, null);
                continue;
            }
            if (item instanceof ImageView) {
                ImageView imageView = (ImageView) item;
                imageView.setImageDrawable(drawable);
            }
        }
    }

    public void setTitleIdArray(List<Integer> titleIdArray) {
        for (int num = 0; num < titleIdArray.size(); ++num) {
            View item = mItemArray.get(num);
            if (item instanceof TextView) {
                TextView textView = (TextView) item;
                textView.setText(mContext.getResources().getText(titleIdArray.get(num)));
            }
        }
    }

    public void setFragmentClassArray(List<Class> fragmentClassArray) {
        if (JHArrayUtils.isNullOrEmpty(fragmentClassArray)) {
            return;
        }
        if (mFragmentClassMap == null) {
            mFragmentClassMap = new HashMap();
        }
        for (int num = 0; num < fragmentClassArray.size(); ++num) {
            mFragmentClassMap.put(mItemIdArray.get(num), fragmentClassArray.get(num));
        }
    }

    public void setSelectIndex(Integer index) {
        mItemArray.get(index).performClick();
    }

    public Fragment getSelectFragement(Integer index) {
        Fragment fragment = mFragmentMap.get(mItemIdArray.get(index));
        return fragment;
    }

    @Override
    public void onClick(View view) {
        for (View item : mItemArray) {
            item.setSelected(false);
        }
        if (mFragmentMap == null) {
            mFragmentMap = new HashMap<>();
        }

        int tag = view.getId();
        if (mItemIdArray.indexOf(tag) == mItemArray.size()-1 && !WGApplicationUserUtils.getInstance().isLogined()) {
            Intent intent = new Intent(mContext, WGLoginActivity.class);
            mActivity.startActivity(intent);
            return;
        }
        FragmentTransaction transaction = mActivity.getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);

        JHFragment fragment = mFragmentMap.get(view.getId());
        if (fragment == null) {
            Integer key = view.getId();
            Class<JHFragment> clazz = mFragmentClassMap.get(key);
            try {
                fragment = clazz.newInstance();
                mFragmentMap.put(key, fragment);
                transaction.add(R.id.tabBar_frameLayout, fragment);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        else {
            transaction.show(fragment);
        }

        transaction.commit();
        view.setSelected(true);
    }

    public void hideAllFragment(FragmentTransaction transaction) {
        for(Map.Entry<Integer, JHFragment> entry : mFragmentMap.entrySet()) {
            if (entry.getValue() != null) {
                transaction.hide(entry.getValue());
            }
        }
    }
}
