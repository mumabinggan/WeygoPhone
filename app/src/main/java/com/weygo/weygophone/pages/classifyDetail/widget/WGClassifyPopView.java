package com.weygo.weygophone.pages.classifyDetail.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.widget.JHPopView;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.pages.classifyDetail.adapter.WGClassifyDetailContentAdapter;
import com.weygo.weygophone.pages.classifyDetail.adapter.WGClassifyPopAdapter;
import com.weygo.weygophone.pages.collection.adapter.WGGoodListAdapter;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;

import java.util.List;

/**
 * Created by muma on 2017/8/17.
 */

public class WGClassifyPopView extends JHPopView {

    RecyclerView mRecyclerView;
    WGClassifyPopAdapter mAdapter;
    Context mContext;

    List<WGClassifyItem> mData;
    public void setData(List<WGClassifyItem> data) {
        mData = data;
        mAdapter.setData(mData);
    }

    public interface OnItemListener {
        void onItemClick(WGClassifyItem item);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGClassifyPopView(Context context) {
        super(context);
        mContext = context;
    }

    public WGClassifyPopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public WGClassifyPopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(mContext,
                JHDividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new WGClassifyPopAdapter(mContext, mData);
        mAdapter.setOnItemClickListener(new JHRecyclerViewAdapter.OnBaseItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mListener != null) {
                    WGClassifyItem item = mData.get(position);
                    mListener.onItemClick(item);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    void handleClose() {
        mPopupWindow.dismiss();
    }
}
