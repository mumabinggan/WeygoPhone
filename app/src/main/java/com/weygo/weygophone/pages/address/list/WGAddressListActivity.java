package com.weygo.weygophone.pages.address.list;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGCommonAlertView;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.address.list.adapter.WGAddressListAdapter;
import com.weygo.weygophone.pages.address.list.model.request.WGAddAddressRequest;
import com.weygo.weygophone.pages.address.list.model.request.WGAddressListRequest;
import com.weygo.weygophone.pages.address.list.model.request.WGDeleteAddressRequest;
import com.weygo.weygophone.pages.address.list.model.request.WGSetDefaultAddressRequest;
import com.weygo.weygophone.pages.address.list.model.response.WGAddressListResponse;
import com.weygo.weygophone.pages.address.list.model.response.WGDeleteAddressResponse;
import com.weygo.weygophone.pages.address.list.model.response.WGSetDefaultAddressResponse;
import com.weygo.weygophone.pages.classifyDetail.adapter.WGClassifyDetailContentAdapter;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyFilterCondition;
import com.weygo.weygophone.pages.order.commit.adapter.WGCommitOrderAdapter;
import com.weygo.weygophone.pages.order.commit.widget.WGCommitOrderFooterView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by muma on 2017/8/22.
 */

public class WGAddressListActivity extends WGTitleActivity {

    RecyclerView mRecyclerView;
    WGAddressListAdapter mAdapter;

    List<WGAddress> mData;

    long mAddressId;

    public interface OnItemLinsener {
        void onUse(WGAddress address);
    }
    OnItemLinsener mListener;
    public void setListener(OnItemLinsener listener) {
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    void handleUse(WGAddress address) {
        if (mListener != null) {
            mListener.onUse(address);
        }
        finish();
    }

    void handleSetDefault(WGAddress address) {
        loadSetDefaultAddressRequest(address);
    }

    void handleChange(WGAddress address) {

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
