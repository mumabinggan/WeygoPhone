package com.weygo.weygophone.pages.receipt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGCellStyle4View;
import com.weygo.weygophone.common.widget.WGOptionPickerView;
import com.weygo.weygophone.pages.address.list.model.WGAddressCityListItem;
import com.weygo.weygophone.pages.receipt.model.WGReceipt;
import com.weygo.weygophone.pages.receipt.model.WGReceiptCountryListItem;
import com.weygo.weygophone.pages.receipt.model.request.WGCountryListRequest;
import com.weygo.weygophone.pages.receipt.model.response.WGCountryListResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by muma on 2017/8/25.
 */

public class WGReceiptActivity extends WGTitleActivity {

    EditText mConpanyET;

    WGCellStyle4View mCountryView;             //城市

    EditText mPhoneET;

    EditText mAddressET;

    EditText mStreetET;

    EditText mCityET;       //城市

    EditText mCapET;

    EditText mCodiceET;

    Button mCommitBtn;

    Button mCancelBtn;

    WGReceipt mData;

    List<WGReceiptCountryListItem> mCountryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCountryListRequest();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgreceipt_activity);
    }

    @Override
    public void initData() {
        super.initData();
        mData = new WGReceipt();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp != null && temp instanceof WGReceipt) {
                mData = (WGReceipt) temp;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.AddReceipt_Title);
        mConpanyET = (EditText) findViewById(R.id.companyET);
        mPhoneET = (EditText) findViewById(R.id.phoneET);
        mCapET = (EditText) findViewById(R.id.capET);
        mAddressET = (EditText) findViewById(R.id.addressET);
        mStreetET = (EditText) findViewById(R.id.streetET);
        mCityET = (EditText) findViewById(R.id.cityET);
        mCountryView = (WGCellStyle4View) findViewById(R.id.countryView);
        mCountryView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCountry();
            }
        });
        mCountryView.setTitleColor(JHResourceUtils.getInstance().getColor(R.color.WGAppBaseTitleAColor));
        mCodiceET = (EditText) findViewById(R.id.codiceET);
        mCommitBtn = (Button) findViewById(R.id.commitBtn);
        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCommit();
            }
        });
        mCancelBtn = (Button) findViewById(R.id.cancelBtn);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCancel();
            }
        });
        refreshUI();
    }

    void handleCancel() {
        Intent intent = new Intent();
        setResult(WGConstants.WGCommitOrderReceiptCancelReturn, intent);
        finish();
    }

    void handleCommit() {
        mData.companyName = mConpanyET.getText().toString();
        mData.address = mAddressET.getText().toString();
        mData.phone = mPhoneET.getText().toString();
        mData.civico = mStreetET.getText().toString();
        mData.city = mCityET.getText().toString();
        mData.cap = mCapET.getText().toString();
        mData.taxCode = mCodiceET.getText().toString();

        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey, mData);
        intent.putExtras(bundle);
        setResult(WGConstants.WGCommitOrderReceiptCommitReturn, intent);
        finish();
    }

    void refreshUI() {
        if (mData != null) {
            mConpanyET.setText(mData.companyName);
            mPhoneET.setText(mData.phone);
            mCapET.setText(mData.cap);
            mAddressET.setText(mData.address);
            mStreetET.setText(mData.civico);
            mCityET.setText(mData.city);
            mCodiceET.setText(mData.taxCode);
            mCountryView.setTitle(mData.country);
        }
    }

    void handleCountry() {
        int index = 0;
        List<String> list = new ArrayList();
        for (int num = 0; num < mCountryList.size(); ++num) {
            WGReceiptCountryListItem item = mCountryList.get(num);
            list.add(item.label);
            if (mData != null && mData.countryId.equals(item.value)) {
                index = num;
            }
        }
        if (list.size() > 0) {
            WGOptionPickerView picker = new WGOptionPickerView(this, list);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    handleSelectedCountry(index, item);
                }
            });
            picker.setSelectedIndex(index);
            picker.show();
        }
    }

    void handleSelectedCountry(int index, String title) {
        WGReceiptCountryListItem item = mCountryList.get(index);
        mData.country = item.label;
        mData.countryId = item.value;
        mCountryView.setTitle(mData.country);
    }

    void loadCountryListRequest() {
        WGCountryListRequest request = new WGCountryListRequest();
        this.postAsyn(request, WGCountryListResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessCountryList((WGCountryListResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessCountryList(WGCountryListResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mCountryList = response.data;
            for (WGReceiptCountryListItem item : mCountryList) {
                if (item.value.equals(mData.countryId)) {
                    mData.country = item.label;
                    break;
                }
            }
            refreshUI();
        }
        else {
            showWarning(response.message);
        }
    }
}
