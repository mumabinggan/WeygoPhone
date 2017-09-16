package com.weygo.weygophone.pages.collection.adapter;

import android.content.Context;
import android.graphics.Point;
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

        void onTouchAddCart(View view, WGHomeFloorContentGoodItem item, Point point);
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

    void handleTouchShopCartItem(WGHomeFloorContentGoodItem item, View view, Point point) {
        if (mOnItemClickListener != null) {
            WGGoodListOnItemClickListener listener = (WGGoodListOnItemClickListener) mOnItemClickListener;
            listener.onTouchAddCart(view, item, point);
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
            public void onTouchShopCart(WGHomeFloorContentGoodItem item, View view, Point point) {
                handleTouchShopCartItem(item, view, point);
            }

            @Override
            public void onTouchItem(WGHomeFloorContentGoodItem item) {

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
