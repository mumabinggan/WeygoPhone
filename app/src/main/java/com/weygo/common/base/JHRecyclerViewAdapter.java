package com.weygo.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by muma on 2017/5/10.
 */

public class JHRecyclerViewAdapter extends RecyclerView.Adapter<JHBaseViewHolder> {

    protected Context mContext;

    public OnBaseItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnBaseItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public void setOnItemClickListener(OnBaseItemClickListener listener) {
        mOnItemClickListener = listener;
    }
}
