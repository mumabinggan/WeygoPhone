package com.weygo.weygophone.pages.classifyDetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseViewHolder;
import com.weygo.weygophone.common.widget.WGCellStyle6View;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyDetail;
import com.weygo.weygophone.pages.classifyDetail.widget.WGClassifyDetailFilterView;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;

import java.util.List;

/**
 * Created by muma on 2017/9/4.
 */

public class WGClassifyPopAdapter extends JHRecyclerViewAdapter {
    public WGClassifyPopAdapter(Context context, List<WGClassifyItem> data) {
        this.mContext = context;
        setData(data);
    }

    List<WGClassifyItem> mData;
    public void setData(List<WGClassifyItem> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = R.layout.common_cell_type6;
        View view = LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, (int)v.getTag());
                }
            }
        });
        WGBaseViewHolder holder = new WGBaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        WGClassifyItem item = mData.get(position);
        WGCellStyle6View view = (WGCellStyle6View)holder.itemView;
        view.setTitle(item.name);
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 10;
    }
}
