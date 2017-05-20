package com.weygo.weygophone.pages.clientCenter.list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.clientCenter.list.model.WGClientServiceItem;
import com.weygo.weygophone.pages.clientCenter.list.model.response.WGClientServiceResponse;
import com.weygo.weygophone.pages.common.widget.WGCommonCellStyle2ViewHolder;

import java.util.List;

/**
 * Created by muma on 2017/5/18.
 */

public class WGClientServiceAdapter extends JHRecyclerViewAdapter {

    Context mContext;

    List<WGClientServiceItem> mArray;

    public WGClientServiceAdapter(Context context, List<WGClientServiceItem> data) {
        this.mArray = data;
        this.mContext = context;
    }

    public void setData(List<WGClientServiceItem> data) {
        mArray = data;
        notifyDataSetChanged();
    }

    void handleClickView(View view) {
        if (mOnItemClickListener != null) {
            int tag = (int) view.getTag();
            mOnItemClickListener.onItemClick(view, tag);
        }
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = R.layout.common_cell_type2;
        View view = LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        WGCommonCellStyle2ViewHolder holder = new WGCommonCellStyle2ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickView(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        WGClientServiceItem item = mArray.get(position);
        holder.showWithData(item.name);
    }

    @Override
    public int getItemCount() {
        if (mArray == null) {
            return 0;
        }
        return mArray.size();
    }

}
