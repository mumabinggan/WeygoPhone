package com.weygo.weygophone.pages.coupon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.address.list.adapter.WGAddressListAdapter;
import com.weygo.weygophone.pages.address.list.model.request.WGAddressListRequest;
import com.weygo.weygophone.pages.address.list.model.response.WGAddressListResponse;
import com.weygo.weygophone.pages.coupon.adapter.WGCouponlistAdapter;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.coupon.model.request.WGActiveCouponRequest;
import com.weygo.weygophone.pages.coupon.model.request.WGCouponListRequest;
import com.weygo.weygophone.pages.coupon.model.response.WGActiveCouponResponse;
import com.weygo.weygophone.pages.coupon.model.response.WGCouponListResponse;
import com.weygo.weygophone.pages.coupon.widget.WGCouponListInputView;

import java.io.Serializable;
import java.util.List;

/**
 * Created by muma on 2017/8/26.
 */

public class WGCouponListActivity extends WGTitleActivity {

    RecyclerView mRecyclerView;
    WGCouponlistAdapter mAdapter;
    List<WGCoupon> mData;

    WGCouponListInputView mHeaderView;

    WGCoupon mCoupon;
    boolean mIsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadCouponListRequest();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgcouponlist_activity);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable temp = bundle.getSerializable(WGConstants.WGIntentDataKey);
            if (temp != null && temp instanceof WGCoupon) {
                mCoupon = (WGCoupon)temp;
            }
            Serializable temp1 = bundle.getSerializable(WGConstants.WGIntentDataKey1);
            if (temp1 != null && temp1 instanceof Boolean) {
                mIsSelected = (boolean)temp1;
            }
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.CouponList_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGCouponlistAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new JHRecyclerViewAdapter.OnBaseItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                handleClick(position);
            }
        });
        mHeaderView = (WGCouponListInputView) findViewById(R.id.headerView);
        mHeaderView.setListener(new WGCouponListInputView.OnItemListener() {
            @Override
            public void onActive(String code) {
                handleActive(code);
            }
        });
        if (mIsSelected) {
            mHeaderView.setVisibility(View.VISIBLE);
        }
        else {
            mHeaderView.setVisibility(View.GONE);
        }
    }

    void handleClick(int position) {
        if (mIsSelected) {
            WGCoupon coupon = mData.get(position);
            if (coupon != null) {
                loadUseCoupon(coupon, coupon.isSelected);
            }
        }
    }

    void handleActive(String code) {
        for (WGCoupon coupon : mData) {
            if (coupon.isSelected) {
                loadUseCoupon(coupon, true);
                return;
            }
        }
        WGCoupon coupon = new WGCoupon();
        coupon.couponCode = code;
        coupon.isSelected = mHeaderView.isSelectedActiveTV();
        loadUseCoupon(coupon, mHeaderView.isSelectedActiveTV());
    }

    void refreshUI() {
        mAdapter.setData(mData);
    }

    void loadCouponListRequest() {
        WGCouponListRequest request = new WGCouponListRequest();
        this.postAsyn(request, WGCouponListResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessCouponList((WGCouponListResponse) result);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessCouponList(WGCouponListResponse response) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            mData = response.data;
            boolean isCouponCode = true;
            if (mCoupon != null) {
                for (WGCoupon item : mData) {
                    if (item.id == mCoupon.id) {
                        item.isSelected = !item.isSelected;
                        isCouponCode = false;
                    }
                    else {
                        item.isSelected = false;
                    }
                }
            }
            if (mCoupon != null) {
                mHeaderView.setInput(mCoupon.couponCode, isCouponCode);
                mHeaderView.setActiveTitle(R.string.CouponList_UnActive, true, true);
            }
            else {
                mHeaderView.setInput(null, true);
                mHeaderView.setActiveTitle(R.string.CouponList_Active, false, false);
            }
            refreshUI();
        }
        else {
            showWarning(response.message);
        }
    }

    void loadUseCoupon(final WGCoupon coupon, final boolean remove) {
        WGActiveCouponRequest request = new WGActiveCouponRequest();
        request.couponCode = (coupon.id == 0) ? coupon.couponCode : null;
        request.couponId = coupon.id;
        request.remove = remove ? 1 : 0;
        this.postAsyn(request, WGActiveCouponResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse result) {
                handleSuccessActiveCoupon((WGActiveCouponResponse) result, coupon, remove);
            }

            @Override
            public void onFailure(JHRequestError error) {
                Log.e("onFailure", error.toString());
            }
        });
    }

    void handleSuccessActiveCoupon(WGActiveCouponResponse response, WGCoupon coupon, boolean remove) {
        Log.e("onSuccess", JSON.toJSONString(response));
        if (response.success()) {
            if (remove) {
                mHeaderView.setInput(null, true);
                mHeaderView.setActiveTitle(R.string.CouponList_Active, true, false);
                for (WGCoupon item : mData) {
                    item.isSelected = false;
                }
                //发送数据
                response.data.coupon = null;
                handleApplyCallBack(response);
            }
            else {
                boolean isCouponCode = true;
                for (WGCoupon item : mData) {
                    if (response.data.coupon.id == item.id) {
                        isCouponCode = false;
                    }
                }
                if (isCouponCode) {
                    mHeaderView.setInput(coupon.couponCode, true);
                    mHeaderView.setActiveTitle(R.string.CouponList_UnActive, true, true);
                    for (WGCoupon item : mData) {
                        item.isSelected = false;
                    }
                    //发送数据
                    handleApplyCallBack(response);
                }
                else {
                    mHeaderView.setInput(coupon.couponCode, false);
                    mHeaderView.setActiveTitle(R.string.CouponList_UnActive, false, true);
                    for (WGCoupon item : mData) {
                        if (item.id == response.data.coupon.id) {
                            item.isSelected = true;
                        }
                        else {
                            item.isSelected = false;
                        }
                    }
                    //发送数据
                    handleApplyCallBack(response);
                }
            }
            refreshUI();
            finish();
        }
        else {
            showWarning(response.message);
        }
    }

    void handleApplyCallBack(WGActiveCouponResponse response) {
        Intent intent = getIntent();
        Bundle bundle = new Bundle();
        if (response.data != null) {
            bundle.putSerializable(WGConstants.WGIntentDataKey, response.data);
            intent.putExtras(bundle);
            setResult(WGConstants.WGCommitOrderCouponReturn, intent);
        }
    }
}
