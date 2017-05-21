package com.weygo.weygophone.pages.integral.myIntegral.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.integral.myIntegral.model.WGIntegrationDetail;
import com.weygo.weygophone.pages.integral.myIntegral.model.WGIntegrationHistoryItem;
import com.weygo.weygophone.pages.integral.myIntegral.widget.WGIntegrationHistoryItemView;

/**
 * Created by muma on 2017/5/21.
 */

public class WGMyIntegrationAdapter extends JHRecyclerViewAdapter {
    Context mContext;

    WGIntegrationDetail mIntegrationDetail;

    public WGMyIntegrationAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(WGIntegrationDetail data) {
        mIntegrationDetail = data;
        notifyDataSetChanged();
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = R.layout.myintegration_history_item;
        View view = LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        IntegrationHistoryViewHolder holder = new IntegrationHistoryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        WGIntegrationHistoryItem item = mIntegrationDetail.history.get(position);
        holder.showWithData(item);
    }

    @Override
    public int getItemCount() {
        if (mIntegrationDetail != null) {
            return mIntegrationDetail.historyCount();
        }
        return 0;
    }

    //Classify
    class IntegrationHistoryViewHolder extends JHBaseViewHolder {

        WGIntegrationHistoryItemView itemView;

        public IntegrationHistoryViewHolder(View view) {
            super(view);
            itemView = (WGIntegrationHistoryItemView) view;
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            itemView.showWithData((WGIntegrationHistoryItem) object);
        }
    }
}
