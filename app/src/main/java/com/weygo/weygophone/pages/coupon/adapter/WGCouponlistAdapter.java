package com.weygo.weygophone.pages.coupon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseViewHolder;
import com.weygo.weygophone.common.widget.WGCellStyle5View;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.address.list.adapter.WGAddressListAdapter;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.coupon.widget.WGCouponListItemView;

import java.util.List;

/**
 * Created by muma on 2017/8/26.
 */

public class WGCouponlistAdapter extends JHRecyclerViewAdapter {

    public interface OnListener {
        void onItem(WGCoupon coupon);
    }
    OnListener mListener;
    public void setListener(OnListener listener) {
        mListener = listener;
    }

    List<WGCoupon> mData;
    public void setData(List<WGCoupon> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public WGCouponlistAdapter(Context context, List<WGCoupon> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = R.layout.wgcouponlist_item_view;
        WGCouponListItemView view = (WGCouponListItemView)LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        view.setListener(new WGCouponListItemView.OnItemListener() {
            @Override
            public void onTouch(WGCoupon coupon) {
                handleTouch(coupon);
            }
        });
        WGBaseViewHolder holder = new WGBaseViewHolder(view);
        return holder;
    }

    void handleTouch(WGCoupon coupon) {
        if (mListener != null) {
            mListener.onItem(coupon);
        }
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (mData.size() > position) {
            WGCoupon item = mData.get(position);
            holder.showWithData(item);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mData != null) {
            count = mData.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }
}
