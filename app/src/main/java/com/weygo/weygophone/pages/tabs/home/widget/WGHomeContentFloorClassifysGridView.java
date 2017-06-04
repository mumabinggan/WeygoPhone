package com.weygo.weygophone.pages.tabs.home.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorItem;

import java.util.List;

/**
 * Created by muma on 2017/6/4.
 */

public class WGHomeContentFloorClassifysGridView extends JHRelativeLayout {

    GridView mGridView;
    GridViewAdapter mAdapter;

    List mList;

    public WGHomeContentFloorClassifysGridView(Context context) {
        super(context);
    }

    public WGHomeContentFloorClassifysGridView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGHomeContentFloorClassifysGridView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mGridView = (GridView) findViewById(R.id.gridView);
        mAdapter = new GridViewAdapter(getContext());
    }

    public void showWithArray(List data) {
        if (mList != null) {
            if (mList.equals(data)) {
                return;
            }
        }
        mList = data;
        mAdapter.setList(mList);
        ViewGroup.LayoutParams params = mGridView.getLayoutParams();
        params.height = (int) (mList.size()/4 * JHAdaptScreenUtils.pixelWidth(getContext(), 114));
        mGridView.setLayoutParams(params);
    }

    private class GridViewAdapter extends BaseAdapter {

        private Context context;

        List mList;
        public void setList(List list) {
            mList = list;
            notifyDataSetChanged();
        }

        public GridViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            if (mList == null) {
                return 0;
            }
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(
                        getContext()).inflate(R.layout.wghome_content_floor_classify_grid_item, parent,
                        false);
            }
            WGHomeContentFloorClassifyGridItemView itemView = (WGHomeContentFloorClassifyGridItemView) convertView;
            itemView.showWithData(mList.get(position));
            return convertView;
        }

    }
}
