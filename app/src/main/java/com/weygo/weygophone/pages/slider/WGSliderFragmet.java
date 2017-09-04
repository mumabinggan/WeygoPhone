package com.weygo.weygophone.pages.slider;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.common.WGCommonAlertView;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.login.WGLoginActivity;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightDetail;
import com.weygo.weygophone.pages.order.commit.model.WGOverHeightGoodItem;
import com.weygo.weygophone.pages.order.commit.widget.WGCommitOrderExpirePopView;
import com.weygo.weygophone.pages.order.commit.widget.WGCommitOrderOverWeightPopView;
import com.weygo.weygophone.pages.shopcart.model.WGShopCartGoodItem;
import com.weygo.weygophone.pages.shopcart.widget.WGShopCartFailurePopView;
import com.weygo.weygophone.pages.shopcart.widget.WGShopCartGiftPopView;
import com.weygo.weygophone.pages.slider.adapter.WGSliderAdapter;
import com.weygo.weygophone.pages.slider.model.SliderOnItemClickListener;
import com.weygo.weygophone.pages.slider.model.WGHomeSlider;
import com.weygo.weygophone.pages.slider.model.request.WGHomeSliderRequest;
import com.weygo.weygophone.pages.slider.model.response.WGHomeSliderResponse;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.util.ArrayList;
import java.util.List;

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
                handleSliderPersonInfoClick(view);
            }

            @Override
            public void onScanClick(View view) {
                handleSliderScanClick(view);
            }

            @Override
            public void onPostCodeClick(View view) {
                handleSliderPostCodeClick(view);
            }

            @Override
            public void onOrderClick(View view) {

            }

            @Override
            public void onCouponClick(View view) {

            }

            @Override
            public void onMessageCenterClick(View view) {
                Log.e("========", "00000");
                startActivity(new Intent(getActivity(), ZopimChatActivity.class));
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

    void tests() {
        WGCommonAlertView builder = new WGCommonAlertView(getActivity(), R.style.MyDialogTheme)
                .setCustomMessage("看看什么情况")
                .setCustomCancelEnable(true)
                .setCustomPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "确认",Toast.LENGTH_SHORT).show();
                    }
                })
                .setCustomNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "取消",Toast.LENGTH_SHORT).show();            }
                })
                .showAlert();
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.MyDialogTheme);
//        builder.setMessage("看看什么情况");
//        //监听下方button点击事件
//        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getActivity(), "确认",Toast.LENGTH_SHORT).show();
//            }
//        });
//        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                Toast.makeText(getActivity(), "取消",Toast.LENGTH_SHORT).show();            }
//        });
//
//        //设置对话框是可取消的
//        builder.setCancelable(true);
//        AlertDialog dialog=builder.create();
//        dialog.show();
    }

    void  test() {
        WGPostPopView popupView = (WGPostPopView) getActivity().getLayoutInflater().inflate(R.layout.common_cap_pop, null);
//        JHBasePopupWindow window = new JHBasePopupWindow(popupView,
//                JHAdaptScreenUtils.deviceDpWidth(getContext()),
//                JHAdaptScreenUtils.deviceDpHeight(getContext()));
        JHBasePopupWindow window = new JHBasePopupWindow(popupView,
                JHAdaptScreenUtils.devicePixelWidth(getContext()),
                JHAdaptScreenUtils.devicePixelHeight(getContext()));
        popupView.setPopupWindow(window);
        window.showAtLocation(popupView, Gravity.CENTER, 0, 0);

//        WGOverHeightDetail item = new WGOverHeightDetail();
//        item.maxRise = "100.0";
//        WGOverHeightGoodItem good = new WGOverHeightGoodItem();
//        good.name= "郑要苛要";
//        good.briefDescription = "asdfasdfasdfasdiuhewoifsdalkf;a;sldfkalksdfjkasl;kdfkjaksldflka";
//        good.pictureURL = "http://m.preview.weygo.com//media//wysiwyg//app//Italy//App__1.jpg";
//        good.goodCount = 2;
//        good.rise = 23;
//        List list = new ArrayList<WGOverHeightGoodItem>();
//        list.add(good);
//        list.add(good);
//        list.add(good);
//        item.goods = list;
//        List goods = new ArrayList<WGOverHeightDetail>();
//        goods.add(item);
//        WGCommitOrderOverWeightPopView popupView = (WGCommitOrderOverWeightPopView) getActivity()
//                .getLayoutInflater()
//                .inflate(R.layout.commitorder_overheight_pop, null);
//        popupView.setGoods(goods);
//        JHBasePopupWindow window = new JHBasePopupWindow(popupView,
//                JHAdaptScreenUtils.devicePixelWidth(getContext()),
//                JHAdaptScreenUtils.devicePixelHeight(getContext()));
//        popupView.setPopupWindow(window);
//        window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    void handleSliderLoginClick(View view) {
        WGMainActivity activity = (WGMainActivity)getActivity();
        activity.closeSlider();
        Intent intent = new Intent(getContext(), WGLoginActivity.class);
        startActivity(intent);
    }

    void handleSliderPersonInfoClick(View view) {
        test();
    }

    void handleSliderScanClick(View view) {
        test();
    }

    void handleSliderPostCodeClick(View view) {
        tests();
    }

    void handleSliderOrderClick(View view) {
        tests();
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
