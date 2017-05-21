package com.weygo.weygophone.pages.order.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.collection.adapter.WGGoodListAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodItemViewHolder;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListGoodsAdapter extends JHRecyclerViewAdapter {

    Context mContext;

    List mArray;

    public WGOrderListGoodsAdapter(Context context, List<WGOrderGoodItem> data) {
        this.mArray = data;
        this.mContext = context;
    }

    public void setData(List<WGOrderGoodItem> data) {
        mArray = data;
        notifyDataSetChanged();
    }

    void handleTouchGoodItem(View view) {
        if (mOnItemClickListener != null) {
            WGOrderGoodItem item = (WGOrderGoodItem) mArray.get((int) view.getTag());
            mOnItemClickListener.onItemClick(view, (int)view.getTag());
        }
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = R.layout.wgorderlist_good_item;
        final View view = LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        WGOrderListGoodItemViewHolder holder = new WGOrderListGoodItemViewHolder(view);
//        ((WGGoodListView)view).setInnerTouchListener(new WGGoodListView.GoodListItemOnListener() {
//            @Override
//            public void onTouchShopCart() {
//                handleTouchShopCartItem(view);
//            }
//        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTouchGoodItem(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        WGOrderGoodItem item = (WGOrderGoodItem) mArray.get(position);
        holder.showWithData(item);
    }

    @Override
    public int getItemCount() {
        if (mArray == null) {
            return 0;
        }
        return mArray.size();
    }
}
