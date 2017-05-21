package com.weygo.weygophone.pages.order.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.widget.WGOrderItemPriceView;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodItemViewHolder;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodsView;
import com.weygo.weygophone.pages.order.list.widget.WGOrderNumberView;

import java.util.List;

/**
 * Created by muma on 2017/5/21.
 */

public class WGOrderListAdapter extends JHRecyclerViewAdapter {
    Context mContext;

    List mArray;

    public WGOrderListAdapter(Context context, List<WGOrderListItem> data) {
        this.mArray = data;
        this.mContext = context;
    }

    public void setData(List<WGOrderListItem> data) {
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
        int resourceId = R.layout.wgorderlist_item;
        final View view = LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        WGOrderListItemViewHolder holder = new WGOrderListItemViewHolder(view);
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
        WGOrderListItem item = (WGOrderListItem) mArray.get(position);
        holder.showWithData(item);
    }

    @Override
    public int getItemCount() {
        if (mArray == null) {
            return 0;
        }
        return mArray.size();
    }

    class WGOrderListItemViewHolder extends JHBaseViewHolder {

        WGOrderNumberView numberView;
        WGOrderListGoodsView goodsView;
        WGOrderItemPriceView priceView;

        public WGOrderListItemViewHolder(View view) {
            super(view);
            numberView = (WGOrderNumberView) view.findViewById(R.id.orderNumberView);
            goodsView = (WGOrderListGoodsView) view.findViewById(R.id.orderGoodsView);
            priceView = (WGOrderItemPriceView) view.findViewById(R.id.orderPriceView);
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (object instanceof WGOrderListItem) {
                WGOrderListItem item = (WGOrderListItem) object;
                numberView.showWithData(item);
                goodsView.showWithArray(item.goods);
                priceView.showWithData(item);
            }
        }
    }
}
