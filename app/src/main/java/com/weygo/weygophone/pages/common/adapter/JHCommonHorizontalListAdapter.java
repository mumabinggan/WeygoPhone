package com.weygo.weygophone.pages.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.base.WGObject;

import java.util.List;

/**
 * Created by muma on 2017/6/4.
 */

public class JHCommonHorizontalListAdapter extends JHRecyclerViewAdapter {

    Context mContext;

    List mArray;

    int mLayoutResId;

    public interface OnCommonListItemClickListener extends OnBaseItemClickListener {
        void onItemClick(View view, WGObject object);
    }

    public JHCommonHorizontalListAdapter(Context context, List data, int layoutResId) {
        this.mArray = data;
        this.mContext = context;
        this.mLayoutResId = layoutResId;
    }

    public void setData(List data) {
        mArray = data;
        notifyDataSetChanged();
    }

    void handleTouchItem(View view) {
        if (mOnItemClickListener != null) {
            OnCommonListItemClickListener listener = (OnCommonListItemClickListener) mOnItemClickListener;
            if (listener != null) {
                WGObject item = (WGObject) mArray.get((int) view.getTag());
                listener.onItemClick(view, item);
            }
        }
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = mLayoutResId;
        final View view = LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        CommonHorizontalListItemHolder holder = new CommonHorizontalListItemHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleTouchItem(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        WGObject item = (WGObject) mArray.get(position);
        holder.showWithData(item);
    }

    @Override
    public int getItemCount() {
        if (mArray == null) {
            return 0;
        }
        return mArray.size();
    }

    class CommonHorizontalListItemHolder extends JHBaseViewHolder {

        View mItemView;

        public CommonHorizontalListItemHolder(View view) {
            super(view);
            mItemView = view;
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (mItemView instanceof JHRelativeLayout) {
                JHRelativeLayout layout = (JHRelativeLayout) mItemView;
                layout.showWithData(object);
            }
            else if (mItemView instanceof JHLinearLayout) {
                JHLinearLayout layout = (JHLinearLayout) mItemView;
                layout.showWithData(object);
            }
        }
    }
}
