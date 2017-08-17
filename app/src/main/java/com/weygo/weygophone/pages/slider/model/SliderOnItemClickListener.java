package com.weygo.weygophone.pages.slider.model;

import android.view.View;

import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;

/**
 * Created by muma on 2017/8/16.
 */

public interface SliderOnItemClickListener extends JHRecyclerViewAdapter.OnBaseItemClickListener {
    void onLoginClick(View view);

    void onPersonInfoClick(View view);

    void onScanClick(View view);

    void onPostCodeClick(View view);

    void onOrderClick(View view);

    void onCouponClick(View view);

    void onMessageCenterClick(View view);

    void onFootPrintsClick(View view);

    void onTopicItemClick(View view, WGTopicItem item);

    void onSubClassifyItemClick(View view, WGClassifyItem item);
}
