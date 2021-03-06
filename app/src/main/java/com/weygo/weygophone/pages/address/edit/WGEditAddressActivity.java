package com.weygo.weygophone.pages.address.edit;

import android.content.Intent;
import android.graphics.Color;
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
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.address.edit.model.WGEditAddressData;
import com.weygo.weygophone.pages.address.list.model.WGAddressCityListItem;
import com.weygo.weygophone.pages.address.list.model.request.WGAddAddressRequest;
import com.weygo.weygophone.pages.address.list.model.request.WGAddressCityListRequest;
import com.weygo.weygophone.pages.address.list.model.request.WGAddressDetailRequest;
import com.weygo.weygophone.pages.address.list.model.response.WGAddAddressResponse;
import com.weygo.weygophone.pages.address.list.model.response.WGAddressCityResponse;
import com.weygo.weygophone.pages.address.list.model.response.WGAddressDetailResponse;
import com.weygo.weygophone.pages.order.commit.model.request.WGCommitOrderDeleteExpireGoodRequest;
import com.weygo.weygophone.pages.order.commit.model.response.WGCommitOrderDeleteExpireGoodResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.OptionPicker;

/**
 * Created by muma on 2017/8/22.
 */

public class WGEditAddressActivity extends WGTitleActivity {

    EditText mFirstNameET;

    EditText mLastNameET;

    EditText mPhoneET;

    EditText mCapET;

    EditText mAddressET;

    EditText mStreetET;

    EditText mCitofonoET;       //楼座

    EditText mScalaET;          //楼座

    EditText mPianoET;

    WGCellStyle4View mCityView;             //城市

    WGCellStyle4View mAscensoreView;        //电梯

    Button mSaveBtn;

    WGEditAddressData mAddressData;

    WGAddress mAddress;

    List<WGAddressCityListItem> mCityList;

    public interface OnItemListener {
        void onApply(WGAddress address);
    }
    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mAddressData != null) {
            loadAddressDetailRequest();
        }
        loadCityListRequest();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgeditaddress_activity);
    }

    @Override
    public void initData() {
        super.initData();
        mAddress = new WGAddress();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp != null && temp instanceof WGEditAddressData) {
                mAddressData = (WGEditAddressData) temp;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.AddressList_Add_Title);
        mFirstNameET = (EditText) findViewById(R.id.firstET);
        mLastNameET = (EditText) findViewById(R.id.lastET);
        mPhoneET = (EditText) findViewById(R.id.phoneET);
        mCapET = (EditText) findViewById(R.id.capET);
        mAddressET = (EditText) findViewById(R.id.addressET);
        mStreetET = (EditText) findViewById(R.id.streetET);
        mCitofonoET = (EditText) findViewById(R.id.citofonoET);
        mScalaET = (EditText) findViewById(R.id.scalaET);
        mPianoET = (EditText) findViewById(R.id.floorET);
        mCityView = (WGCellStyle4View) findViewById(R.id.cityView);
        mCityView.setTitle(R.string.Address_Citta);
        mCityView.setTitleColor(Color.BLACK);
        mCityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCity();
            }
        });
        mAscensoreView = (WGCellStyle4View) findViewById(R.id.ascensoreView);
        mAscensoreView.setTitle(R.string.Address_Ascensore);
        mAscensoreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAscensore();
            }
        });
        mSaveBtn = (Button) findViewById(R.id.saveBtn);
        mSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSaveBtn();
            }
        });
    }

    void handleSaveBtn() {
        loadAddAddressRequest();
    }

    void refreshUI() {
        if (mCityList != null && mAddress != null) {
            mFirstNameET.setText(mAddress.firstName);
            mLastNameET.setText(mAddress.lastName);
            mPhoneET.setText(mAddress.phone);
            mCapET.setText(mAddress.cap);
            mAddressET.setText(mAddress.address);
            mStreetET.setText(mAddress.streetNumber);
            mCitofonoET.setText(mAddress.citofono);
            mScalaET.setText(mAddress.scala);
            mPianoET.setText(mAddress.piano);
            mCityView.setDetailTitle(mAddress.city);
            mAscensoreView.setDetailTitle(mAddress.currentAscensore());
        }
    }

    void handleCity() {
        int index = 0;
        List<String> list = new ArrayList();
        for (int num = 0; num < mCityList.size(); ++num) {
            WGAddressCityListItem item = mCityList.get(num);
            list.add(item.name);
            if (item.value.equals(mAddress.cityId)) {
                index = num;
            }
        }
        if (list.size() > 0) {
            WGOptionPickerView picker = new WGOptionPickerView(this, list);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    handleSelectedCity(index, item);
                }
            });
            picker.setSelectedIndex(index);
            picker.show();
        }
    }

    void handleSelectedCity(int index, String title) {
        WGAddressCityListItem item = mCityList.get(index);
        mAddress.cityId = item.value;
        mAddress.city = item.name;
        mCityView.setDetailTitle(mAddress.city);
    }

    void handleAscensore() {
        List<String> list = new ArrayList();
        for (Integer item : mAddress.ascensores) {
            list.add(JHResourceUtils.getInstance().getString(item));
        }
        if (list.size() > 0) {
            WGOptionPickerView picker = new WGOptionPickerView(this, list);
            picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
                @Override
                public void onOptionPicked(int index, String item) {
                    handleSelectedAscensore(index, item);
                }
            });
            picker.setSelectedIndex(mAddress.ascensore);
            picker.show();
        }
    }

    void handleSelectedAscensore(int index, String title) {
        mAddress.ascensore = index;
        mAscensoreView.setDetailTitle(mAddress.currentAscensore());
    }

    void loadAddressDetailRequest() {
        WGAddressDetailRequest request = new WGAddressDetailRequest();
        request.id = mAddressData.addressId;
        this.postAsyn(request, WGAddressDetailResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessAddressDetail((WGAddressDetailResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessAddressDetail(WGAddressDetailResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mAddress = response.data;
            refreshUI();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadCityListRequest() {
        WGAddressCityListRequest request = new WGAddressCityListRequest();
        request.showsLoadingView = false;
        this.postAsyn(request, WGAddressCityResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessAddressCityList((WGAddressCityResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessAddressCityList(WGAddressCityResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mCityList = response.data;
            for (WGAddressCityListItem item : mCityList) {
                if (item.value.equals(mAddress.cityId)) {
                    mAddress.city = item.name;
                    break;
                }
            }
            refreshUI();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadAddAddressRequest() {
        WGAddAddressRequest request = new WGAddAddressRequest();
        request.id = (mAddressData == null) ? 0 : mAddressData.addressId;
        request.firstname = mFirstNameET.getText().toString();
        request.lastname = mLastNameET.getText().toString();
        request.address = mAddressET.getText().toString();
        request.street = mStreetET.getText().toString();
        request.city = mAddress.cityId;
        request.countryId = mAddress.countryId;
        request.cap = mCapET.getText().toString();
        request.telephone = mPhoneET.getText().toString();
        request.hasLift = mAddress.ascensore;
        request.floor = mPianoET.getText().toString();
        request.floorHole = mScalaET.getText().toString();
        request.doorbell = mCitofonoET.getText().toString();
        this.postAsyn(request, WGAddAddressResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessAddAddress((WGAddAddressResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessAddAddress(WGAddAddressResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            if (mAddressData != null) {
                Intent intent = getIntent();
                Bundle bundle = new Bundle();
                if (response.data != null) {
                    bundle.putSerializable(WGConstants.WGIntentDataKey, response.data);
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                }
            }
            finish();
        }
        else {
            showWarning(response.message);
        }
    }
}
