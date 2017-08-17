package com.weygo.weygophone.pages.slider;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.slider.adapter.WGSliderAdapter;
import com.weygo.weygophone.pages.slider.model.SliderOnItemClickListener;
import com.weygo.weygophone.pages.slider.model.WGHomeSlider;
import com.weygo.weygophone.pages.slider.model.request.WGHomeSliderRequest;
import com.weygo.weygophone.pages.slider.model.response.WGHomeSliderResponse;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;

/**
 * Created by muma on 2017/5/7.
 */

public class WGSliderFragmet extends WGFragment {

    WGHomeSlider mData;

    RecyclerView mRecyclerView;
    WGSliderAdapter mAdapter;

    @Override
    public int fragmentResId() {
        return R.layout.wgslider_fragment;
    }

    @Override
    public void initSubView(View view) {
        initRecyclerView(view);
    }

    @Override
    public void initData() {
        super.initData();
    }

    public void refresh() {
        mAdapter.notifyDataSetChanged();
    }

    void initRecyclerView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.sliderRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new WGSliderAdapter(getContext(), mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new SliderOnItemClickListener() {
            @Override
            public void onLoginClick(View view) {
                handleSliderLoginClick(view);
            }

            @Override
            public void onPersonInfoClick(View view) {

            }

            @Override
            public void onScanClick(View view) {

            }

            @Override
            public void onPostCodeClick(View view) {

            }

            @Override
            public void onOrderClick(View view) {

            }

            @Override
            public void onCouponClick(View view) {

            }

            @Override
            public void onMessageCenterClick(View view) {

            }

            @Override
            public void onFootPrintsClick(View view) {

            }

            @Override
            public void onTopicItemClick(View view, WGTopicItem item) {

            }

            @Override
            public void onSubClassifyItemClick(View view, WGClassifyItem item) {

            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
    }

    void handleSliderLoginClick(View view) {
        WGPostPopView popupView = (WGPostPopView) getActivity().getLayoutInflater().inflate(R.layout.common_cap_pop, null);
//        JHBasePopupWindow window = new JHBasePopupWindow(popupView,
//                JHAdaptScreenUtils.deviceDpWidth(getContext()),
//                JHAdaptScreenUtils.deviceDpHeight(getContext()));
        JHBasePopupWindow window = new JHBasePopupWindow(popupView,
                JHAdaptScreenUtils.devicePixelWidth(getContext()),
                JHAdaptScreenUtils.devicePixelHeight(getContext()));
        popupView.setPopupWindow(window);
        window.showAtLocation(view, Gravity.CENTER, 0, 0);
        //window.showAsDropDown(view, 50, 100);
        //window.showAtLocation();
//        WGMainActivity activity = (WGMainActivity)getActivity();
//        activity.closeSlider();
//        Intent intent = new Intent(getContext(), WGLoginActivity.class);
//        startActivity(intent);
    }

    void handleSliderPersonInfoClick(View view) {

    }

    void handleSliderScanClick(View view) {

    }

    void handleSliderPostCodeClick(View view) {

    }

    void handleSliderOrderClick(View view) {

    }

    void handleSliderCouponClick(View view) {

    }

    void handleSliderMessageCenterClick(View view) {

    }

    void handleSliderFootPrintsClick(View view) {

    }

    void handleSliderTopicItemClick(View view, WGTopicItem item) {

    }

    void handleSliderSubClassifyItemClick(View view, WGClassifyItem item) {

    }

    @Override
    public void loadRequest() {
        super.loadRequest();
        loadHomeSlider();
    }

    void loadHomeSlider() {
        WGHomeSliderRequest request = new WGHomeSliderRequest();
        this.postAsyn(request, WGHomeSliderResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessHomeSlider((WGHomeSliderResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessHomeSlider(WGHomeSliderResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mData = response.data;
            mAdapter.setData(mData);
        }
        else {
            showWarning(response.message);
        }
    }
}
