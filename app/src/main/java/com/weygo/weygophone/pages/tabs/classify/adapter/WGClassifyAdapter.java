package com.weygo.weygophone.pages.tabs.classify.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;

import java.util.List;

/**
 * Created by muma on 2017/5/14.
 */

public class WGClassifyAdapter extends JHRecyclerViewAdapter {

    Context mContext;

    List<WGClassifyItem> mArray;

    private int mCurrentSelectedIndex;

    public WGClassifyAdapter(Context context, List<WGClassifyItem> data) {
        this.mArray = data;
        this.mContext = context;
        mCurrentSelectedIndex = 0;
    }

    public void setData(List<WGClassifyItem> data) {
        mArray = data;
        selectSelectedClassifyItem();
        notifyDataSetChanged();
    }

    void handleClickView(View view) {
        if (mOnItemClickListener != null) {
            int tag = (int) view.getTag();
            mOnItemClickListener.onItemClick(view, (int)view.getTag());
            mCurrentSelectedIndex = tag;
            selectSelectedClassifyItem();
        }
    }

    void selectSelectedClassifyItem() {
        if (mArray == null) {
            return;
        }
        if (mArray.size() <= mCurrentSelectedIndex) {
            mCurrentSelectedIndex = 0;
        }
        for (int num = 0; num < mArray.size(); ++num) {
            WGClassifyItem item = mArray.get(num);
            item.isSelected = (mCurrentSelectedIndex == num);
        }
        notifyDataSetChanged();
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = R.layout.classify_item;
        View view = LayoutInflater.from(
                mContext).inflate(resourceId, parent,
                false);
        ClassifyViewHolder holder = new ClassifyViewHolder(view);
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
        WGClassifyItem item = mArray.get(position);
        holder.showWithData(item);
    }

    @Override
    public int getItemCount() {
        if (mArray == null) {
            return 0;
        }
        return mArray.size();
    }

    //Classify
    class ClassifyViewHolder extends JHBaseViewHolder {

        public TextView textView;
        public ImageView imageView;
        public ImageView arrImageView;

        public ClassifyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            arrImageView = (ImageView) view.findViewById(R.id.classify_arr);
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (object instanceof WGClassifyItem) {
                WGClassifyItem item = (WGClassifyItem) object;
                arrImageView.setVisibility(item.isSelected ? View.VISIBLE : View.INVISIBLE);
                textView.setText(item.name);
                JHImageUtils.getInstance().loadImage(item.pictureURL, R.drawable.common_image_loading_carousel, imageView);
            }
        }
    }
}
