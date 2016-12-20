package com.weygo.weygophone.pages.tabs.home.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHFragment;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeNavigationBar;

/**
 * Created by muma on 2016/12/19.
 */

public class WGHomeFragment extends JHFragment {

    WGHomeNavigationBar mNavigationBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);
        mNavigationBar = (WGHomeNavigationBar) view.findViewById(R.id.home_navigationBar);
        mNavigationBar.setOnClickListener(new WGHomeNavigationBar.OnClickHomeNavigationBarListener() {
            @Override
            public void onClickBriefIntro(View var1) {

            }

            @Override
            public void onClickSearch(View var1) {

            }

            @Override
            public void onClickCart(View var1) {

            }

            @Override
            public void onClickTitle(View var1) {

            }
        });
        return view;
    }
}
