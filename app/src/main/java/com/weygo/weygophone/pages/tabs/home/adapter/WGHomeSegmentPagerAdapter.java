package com.weygo.weygophone.pages.tabs.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by muma on 2017/4/26.
 */

public class WGHomeSegmentPagerAdapter extends FragmentPagerAdapter {
    //public static final String[] TITLES = new String[] {  };
    public static final String[] TITLES = new String[] { "业界", "移动", "研发", "程序员杂志", "云计算" };


    public WGHomeSegmentPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }


    @Override
    public Fragment getItem(int arg0)
    {
        Fragment fragment = new Fragment();
        return fragment;
    }


    @Override
    public CharSequence getPageTitle(int position)
    {
        return TITLES[position % TITLES.length];
    }


    @Override
    public int getCount()
    {
        return TITLES.length;
    }
}
