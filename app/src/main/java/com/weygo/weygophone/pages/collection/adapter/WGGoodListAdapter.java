package com.weygo.weygophone.pages.collection.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.collection.widget.WGGoodListView;
import com.weygo.weygophone.pages.collection.widget.WGGoodListViewHolder;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/5/19.
 */

public class WGGoodListAdapter extends JHRecyclerViewAdapter {

    Context mContext;

    List<WGHomeFloorContentGoodItem> mArray;

    public interface WGGoodListOnItemClickListener extends OnBaseItemClickListener {

        void onTouchGoodItem(View view, WGHomeFloorContentGoodItem item);

        void onLongTouchGoodItem(View view, WGHomeFloorContentGoodItem item);

        void onTouchAddCart(View view, WGHomeFloorContentGoodItem item);
    }

    public WGGoodListAdapter(Context context, List<WGHomeFloorContentGoodItem> data) {
        this.mArray = data;
        this.mContext = context;
    }

    public void setData(List<WGHomeFloorContentGoodItem> data) {
        mArray = data;
        notifyDataSetChanged();
    }

    void handleTouchGoodItem(View view) {
        if (mOnItemClickListener != null) {
            WGGoodListOnItemClickListener listener = (WGGoodListOnItemClickListener) mOnItemClickListener;
            WGHomeFloorContentGoodItem item = mArray.get((int) view.getTag());
            listener.onTouchGoodItem(view, item);
        }
    }

    void handleLongTouchGoodItem(View view) {
        if (mOnItemClickListener != null) {
            WGGoodListOnItemClickListener listener = (WGGoodListOnItemClickListener) mOnItemClickListener;
            WGHomeFloorContentGoodItem item = mArray.get((int) view.getTag());
            listener.onLongTouchGoodItem(view, item);
        }
    }

    void handleTouchShopCartItem(View view) {
        if (mOnItemClickListener != null) {
            WGGoodListOnItemClickListener listener = (WGGoodListOnItemClickListener) mOnItemClickListener;
            WGHomeFloorContentGoodItem item = mArray.get((int) view.getTag());
            listener.onTouchAddCart(view, item);
        }
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = R.layout.wggood_list_item;
        final View view = LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        WGGoodListViewHolder holder = new WGGoodListViewHolder(view);
        ((WGGoodListView)view).setInnerTouchListener(new WGGoodListView.GoodListItemOnListener() {
            @Override
            public void onTouchShopCart() {
                handleTouchShopCartItem(view);
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTouchGoodItem(view);
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleLongTouchGoodItem(view);
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        WGHomeFloorContentGoodItem item = mArray.get(position);
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
