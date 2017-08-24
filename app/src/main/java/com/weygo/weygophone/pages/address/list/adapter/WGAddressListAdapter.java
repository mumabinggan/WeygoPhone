package com.weygo.weygophone.pages.address.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseViewHolder;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.shopcart.adapter.WGShopCartListAdater;
import com.weygo.weygophone.pages.shopcart.model.WGShopCart;
import com.weygo.weygophone.pages.shopcart.model.WGShopCartGoodItem;
import com.weygo.weygophone.pages.tabs.home.adapter.WGHomeFragmentAdapter;

import java.util.List;

/**
 * Created by muma on 2017/8/22.
 */

public class WGAddressListAdapter extends JHRecyclerViewAdapter {

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_AddressListItem,
        ITEM_TYPE_SeparateLine,
    }

    public interface OnItemLinsener {
        void onDelete(WGAddress address);
        void onUse(WGAddress address);
        void onChange(WGAddress address);
        void onSetDefault(WGAddress address);
    }

    OnItemLinsener mListener;
    public void setListener(OnItemLinsener listener) {
        mListener = listener;
    }

    List<WGAddress> mData;
    public void setData(List<WGAddress> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public WGAddressListAdapter(Context context, List<WGAddress> data) {
        this.mData = data;
        this.mContext = context;
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == Item_Type.ITEM_TYPE_AddressListItem.ordinal()) {
            int resourceId = R.layout.wgaddresslist_item_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_SeparateLine.ordinal()) {
            int resourceId = R.layout.wghome_content_separateline;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        WGBaseViewHolder holder = new WGBaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (mData.size() > position) {
            WGAddress item = mData.get(position);
            holder.showWithData(item);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mData != null) {
            count = mData.size() * 2;
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData != null) {
            if (position % 2 == 0) {
                Item_Type.ITEM_TYPE_AddressListItem.ordinal();
            }
            else {
                Item_Type.ITEM_TYPE_SeparateLine.ordinal();
            }
        }
        return WGHomeFragmentAdapter.Item_Type.ITEM_TYPE_None.ordinal();
    }
}
