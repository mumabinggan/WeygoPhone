package com.weygo.weygophone.pages.address.list;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGCommonAlertView;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.address.edit.WGEditAddressActivity;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.address.edit.model.WGEditAddressData;
import com.weygo.weygophone.pages.address.list.adapter.WGAddressListAdapter;
import com.weygo.weygophone.pages.address.list.model.request.WGAddressListRequest;
import com.weygo.weygophone.pages.address.list.model.request.WGDeleteAddressRequest;
import com.weygo.weygophone.pages.address.list.model.request.WGSetDefaultAddressRequest;
import com.weygo.weygophone.pages.address.list.model.response.WGAddressListResponse;
import com.weygo.weygophone.pages.address.list.model.response.WGDeleteAddressResponse;
import com.weygo.weygophone.pages.address.list.model.response.WGSetDefaultAddressResponse;

import java.io.Serializable;
import java.util.List;

/**
 * Created by muma on 2017/8/22.
 */

public class WGAddressListActivity extends WGTitleActivity {

    RecyclerView mRecyclerView;
    WGAddressListAdapter mAdapter;
    List<WGAddress> mData;

    ImageView mAddIV;

    long mAddressId;

    WGAddress mChangeAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAddressListRequest();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                mChangeAddress = (WGAddress) bundle.getSerializable(WGConstants.WGIntentDataKey);
            }
        }
    }

    @Override
    public void handleReturn() {
        handleChangeAddress(mChangeAddress);
        super.handleReturn();
    }

    @Override
    public void onBackPressed() {
        handleChangeAddress(mChangeAddress);
        super.onBackPressed();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgaddresslist_activity);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp != null && temp instanceof Long) {
                mAddressId = (Long)temp;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.AddressList_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGAddressListAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setListener(new WGAddressListAdapter.OnItemLinsener() {
            @Override
            public void onItemClick(View view, int position) {

            }

            @Override
            public void onDelete(WGAddress address) {
                handleDelete(address);
            }

            @Override
            public void onUse(WGAddress address) {
                handleUse(address);
            }

            @Override
            public void onChange(WGAddress address) {
                handleChange(address);
            }

            @Override
            public void onSetDefault(WGAddress address) {
                handleSetDefault(address);
            }
        });
        mAddIV = (ImageView) findViewById(R.id.addIV);
        mAddIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAdd();
            }
        });
    }

    void handleAdd() {
        Intent intent = new Intent(this, WGEditAddressActivity.class);
        startActivity(intent);
    }

    void handleUse(WGAddress address) {
        handleChangeAddress(address);
        finish();
    }

    void handleChangeAddress(WGAddress address) {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        if (address != null) {
            bundle.putSerializable(WGConstants.WGIntentDataKey, address);
            intent.putExtras(bundle);
            setResult(WGConstants.WGCommitOrderAddressReturn, intent);
        }
    }

    void handleSetDefault(WGAddress address) {
        loadSetDefaultAddressRequest(address);
    }

    void handleChange(WGAddress address) {
        Intent intent = new Intent(this, WGEditAddressActivity.class);
        Bundle bundle = new Bundle();
        WGEditAddressData item = new WGEditAddressData();
        boolean needRefresh = false;
        if (address == null) {
            needRefresh = false;
        }
        else {
            if (address.addressId == mAddressId) {
                needRefresh = true;
            }
            else {
                needRefresh = false;
            }
        }
        item.needRefresh = needRefresh;
        item.addressId = address.addressId;
        bundle.putSerializable(WGConstants.WGIntentDataKey, item);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void handleAddAddress() {
        handleChange(null);
    }

    void handleDelete(final WGAddress address) {
        WGCommonAlertView builder = new WGCommonAlertView(getBaseContext(), R.style.MyDialogTheme)
                .setCustomMessage(R.string.Address_Clean_One_Tip)
                .setCustomCancelEnable(true)
                .setCustomPositiveButton(R.string.Collection_Delete_OK, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handleConfirmDelete(address);
                    }
                })
                .setCustomNegativeButton(R.string.Collection_Delete_Cancel, null)
                .showAlert();
    }

    void handleConfirmDelete(WGAddress address) {
        loadDeleteAddressRequest(address);
    }

    void refreshUI() {
        mAdapter.setData(mData);
    }

    void loadAddressListRequest() {
        WGAddressListRequest request = new WGAddressListRequest();
        this.postAsyn(request, WGAddressListResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessAddressList((WGAddressListResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessAddressList(WGAddressListResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mData = response.data;
            mAdapter.setData(response.data);
        }
        else {
            showWarning(response.message);
        }
    }

    void loadSetDefaultAddressRequest(final WGAddress address) {
        WGSetDefaultAddressRequest request = new WGSetDefaultAddressRequest();
        request.id = address.addressId;
        this.postAsyn(request, WGSetDefaultAddressResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessSetDefaultAddress((WGSetDefaultAddressResponse) result, address);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessSetDefaultAddress(WGSetDefaultAddressResponse response, WGAddress address) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            for (WGAddress item : mData) {
                item.isDefault = false;
            }
            address.isDefault = true;
            refreshUI();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadDeleteAddressRequest(final WGAddress address) {
        WGDeleteAddressRequest request = new WGDeleteAddressRequest();
        request.id = address.addressId;
        this.postAsyn(request, WGDeleteAddressResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessDeleteAddress((WGDeleteAddressResponse) result, address);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessDeleteAddress(WGDeleteAddressResponse response, WGAddress address) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            for (WGAddress item : mData) {
                if (item.addressId == address.addressId) {
                    mData.remove(item);
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
