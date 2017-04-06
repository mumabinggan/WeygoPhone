package com.weygo.weygophone.pages.tabs.home.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.weygo.common.base.JHFragment;
import com.weygo.weygophone.R;
import com.weygo.weygophone.models.JHTests;
import com.weygo.weygophone.pages.slider.WGSliderActivity;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeNavigationBar;
import com.weygo.weygophone.pages.test.WGTestActivity;

import org.parceler.Parcels;

/**
 * Created by muma on 2016/12/19.
 */

public class WGHomeFragment extends JHFragment {

    WGHomeNavigationBar mNavigationBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment,container,false);

        TextView textView = (TextView) view.findViewById(R.id.txt_content);
        Animation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(5000);
        textView.startAnimation(alphaAnimation);

        mNavigationBar = (WGHomeNavigationBar) view.findViewById(R.id.home_navigationBar);
        mNavigationBar.setOnClickListener(new WGHomeNavigationBar.OnClickHomeNavigationBarListener() {
            @Override
            public void onClickBriefIntro(View var1) {
                Intent intent = new Intent(getActivity(), WGSliderActivity.class);
                startActivity(intent);
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
