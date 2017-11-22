package com.weygo.weygophone.pages.personInfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGOnUserInfoCompletionInteface;
import com.weygo.weygophone.common.widget.WGDatePickerView;
import com.weygo.weygophone.common.widget.WGOptionPickerView;
import com.weygo.weygophone.pages.findPassword.WGFindPasswordActivity;
import com.weygo.weygophone.pages.personInfo.adapter.WGPersonInfoAdapter;
import com.weygo.weygophone.pages.personInfo.model.request.WGCommitUserInfoRequest;
import com.weygo.weygophone.pages.personInfo.model.response.WGCommitUserInfoResponse;
import com.weygo.weygophone.pages.tabs.mine.model.WGUser;
import com.weygo.weygophone.pages.tabs.mine.model.response.WGUserInfoResponse;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by muma on 2017/5/29.
 */

public class WGPersonInfoActivity extends WGTitleActivity {
    RecyclerView mRecyclerView;
    WGPersonInfoAdapter mAdapter;

    WGUser mData;

    Button mSaveBtn;

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
        mSaveBtn = (Button) findViewById(R.id.saveBtn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadCommitPersonInfo();
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
        int index = 0;
        if (user.isBoy()) {
            index = 1;
        }
        else if (user.isGirl()) {
            index = 2;
        }
        picker.setSelectedIndex(index);
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
        Intent intent = new Intent(this, WGFindPasswordActivity.class);
        startActivity(intent);
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

    void loadCommitPersonInfo() {
        WGCommitUserInfoRequest request = new WGCommitUserInfoRequest();
        request.firstName = mData.name;
        request.lastName = mData.surname;
        request.phone = mData.mobile;
        request.sex = mData.sex;
        request.birth = mData.birth;
        request.tax = mData.tax;
        request.email = mData.email;
        this.postAsyn(request, WGCommitUserInfoResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessCommitUserInfoResponse((WGCommitUserInfoResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessCommitUserInfoResponse(WGCommitUserInfoResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            WGApplicationUserUtils.getInstance().reset();
            WGApplicationUserUtils.getInstance().setUser(response.data);
            mData = response.data;
            mAdapter.setData(mData);
        } else {
            showWarning(response.message);
        }
    }
}
