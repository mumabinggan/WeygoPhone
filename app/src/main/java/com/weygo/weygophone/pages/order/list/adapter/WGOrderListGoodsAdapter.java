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
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodItemView;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodItemViewHolder;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListGoodsAdapter extends JHRecyclerViewAdapter {

    Context mContext;

    List mArray;

    public interface OnItemListener extends OnBaseItemClickListener {
        void onGoodItem(WGOrderGoodItem item);
    }
    public void setListener(OnItemListener listener) {
        mOnItemClickListener = listener;
    }


    public WGOrderListGoodsAdapter(Context context, List<WGOrderGoodItem> data) {
        this.mArray = data;
        this.mContext = context;
    }

    public void setData(List<WGOrderGoodItem> data) {
        mArray = data;
        notifyDataSetChanged();
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = R.layout.wgorderlist_good_item;
        final WGOrderListGoodItemView view = (WGOrderListGoodItemView)LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    OnItemListener temp = (OnItemListener) mOnItemClickListener;
                    temp.onGoodItem((WGOrderGoodItem)mArray.get((int)v.getTag()));
                }
            }
        });
        WGOrderListGoodItemViewHolder holder = new WGOrderListGoodItemViewHolder(view);
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
