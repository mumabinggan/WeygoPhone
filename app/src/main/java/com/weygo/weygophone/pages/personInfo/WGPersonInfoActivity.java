package com.weygo.weygophone.pages.personInfo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHPaddingDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGOnUserInfoCompletionInteface;
import com.weygo.weygophone.common.widget.WGDatePickerView;
import com.weygo.weygophone.common.widget.WGOptionPickerView;
import com.weygo.weygophone.pages.order.detail.adapter.WGOrderDetailAdapter;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDeliver;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDetail;
import com.weygo.weygophone.pages.order.detail.model.WGOrderFax;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatus;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatusItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.personInfo.adapter.WGPersonInfoAdapter;
import com.weygo.weygophone.pages.tabs.classify.model.request.WGClassifyRequest;
import com.weygo.weygophone.pages.tabs.classify.model.response.WGClassifyResponse;
import com.weygo.weygophone.pages.tabs.mine.model.WGUser;
import com.weygo.weygophone.pages.tabs.mine.model.request.WGUserInfoRequest;
import com.weygo.weygophone.pages.tabs.mine.model.response.WGUserInfoResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by muma on 2017/5/29.
 */

public class WGPersonInfoActivity extends WGTitleActivity {
    RecyclerView mRecyclerView;
    WGPersonInfoAdapter mAdapter;

    WGUser mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadUserInfo();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgpersoninfo_activity);
    }

    @Override
    public void initData() {
        super.initData();
        mData = new WGUser();
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.PersonInfo_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGPersonInfoAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(this,
                JHDividerItemDecoration.VERTICAL_LIST));
        mAdapter.setOnItemClickListener(new WGPersonInfoAdapter.PersonInfoOnItemClickListener() {
            @Override
            public void onSexItemClick(View view, WGUser user) {
                //性别
                handleSex(user);
            }

            @Override
            public void onBrithdayItemClick(View view, WGUser user) {
                //生日
                handleBirthday(user);
            }

            @Override
            public void onChangePWItemClick(View view, WGUser user) {
                //忘记密码
                handleChangePW(user);
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });

    }

    @Override
    public void handleRightBarItem() {
        super.handleRightBarItem();
    }

    void handleSex(final WGUser user) {
        OptionPicker picker = new WGOptionPickerView(this, user.sexs());
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                handleSelectedSex(index);
            }
        });
        picker.show();
    }

    void handleSelectedSex(int index) {
        mData.sex = index;
        mAdapter.setData(mData);
    }

    void handleBirthday(WGUser user) {
        final DatePicker picker = new WGDatePickerView(this);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                handleSelectedBrithday(year, month, day);
            }
        });
        picker.show();
    }

    void handleSelectedBrithday(String year, String month, String day) {
        mData.birth = year + "-" + month + "-" + day;
        mAdapter.setData(mData);
    }

    void handleChangePW(WGUser user) {

    }

    void loadUserInfo() {
        WGApplicationRequestUtils.getInstance().loadUserInfoOnCompletion(this, new WGOnUserInfoCompletionInteface() {
            @Override
            public void onUserInfoCompletion(WGUserInfoResponse response) {
                handleUserInfoCompletion(response);
            }
        });
    }

    void handleUserInfoCompletion(WGUserInfoResponse response) {
        if (response.success()) {
            mData = response.data;
            mAdapter.setData(mData);
        }
        else {
            showWarning(response.message);
        }
    }
}
