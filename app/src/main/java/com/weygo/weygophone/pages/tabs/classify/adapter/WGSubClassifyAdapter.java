package com.weygo.weygophone.pages.tabs.classify.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.common.widget.WGCommonCellStyle2ViewHolder;
import com.weygo.weygophone.pages.common.widget.WGCommonCellStyle3ViewHolder;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyHotGoodItem;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;

/**
 * Created by muma on 2017/5/15.
 */

public class WGSubClassifyAdapter extends JHRecyclerViewAdapter {

    Context mContext;

    WGClassifyItem mData;

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_Classify,
        ITEM_TYPE_HotTitle,
        ITEM_TYPE_HotGood,
    }

    public static interface SubClassifyOnItemClickListener extends OnBaseItemClickListener {

        void onSubClassifyHotGoodItemClick(View view, WGClassifyHotGoodItem item);

        void onSubClassifyItemClick(View view, WGClassifyItem item);
    }

    public WGSubClassifyAdapter(Context context, WGClassifyItem data) {
        this.mData = data;
        this.mContext = context;
    }

    public void setData(WGClassifyItem data) {
        mData = data;
        notifyDataSetChanged();
    }

    void handleClickView(View view) {
        if (mOnItemClickListener != null) {
            if (mOnItemClickListener instanceof SubClassifyOnItemClickListener) {
                SubClassifyOnItemClickListener listener = (SubClassifyOnItemClickListener) mOnItemClickListener;
                int postion = (int) view.getTag();
                Item_Type type = getItemViewTypeAfterOrdinal(postion);
                if (type == Item_Type.ITEM_TYPE_Classify) {
                    listener.onSubClassifyItemClick(view, mData.allArray().get(postion));
                }
                else if (type == Item_Type.ITEM_TYPE_HotGood) {
                    int count = 0;
                    if (mData != null) {
                        if (mData.allArray() != null) {
                            count = mData.allArray().size() + 1;
                        }
                        else {
                            count = 1;
                        }
                        listener.onSubClassifyHotGoodItemClick(view, mData.goodArray.get(postion - count));
                    }
                }
            }
            mOnItemClickListener.onItemClick(view, (int)view.getTag());
        }
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        JHBaseViewHolder holder = null;
        if (viewType == Item_Type.ITEM_TYPE_Classify.ordinal()) {
            view = LayoutInflater.from(
                    mContext).inflate(R.layout.common_cell_type2, parent,
                    false);
            holder = new WGCommonCellStyle2ViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_HotTitle.ordinal()) {
            view = LayoutInflater.from(
                    mContext).inflate(R.layout.common_cell_type3, parent,
                    false);
            holder = new WGCommonCellStyle3ViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_HotGood.ordinal()) {
            view = LayoutInflater.from(
                    mContext).inflate(R.layout.classify_hot_good, parent,
                    false);
            holder = new HotGoodViewHolder(view);
        }
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
        if (holder instanceof WGCommonCellStyle2ViewHolder) {
            WGClassifyItem item = mData.allArray().get(position);
            holder.showWithData(item);
        }
        else if (holder instanceof  WGCommonCellStyle3ViewHolder) {
            WGCommonCellStyle3ViewHolder titleHolder = (WGCommonCellStyle3ViewHolder)holder;
            titleHolder.textView.setText(R.string.Hot_Sale);
        }
        else if (holder instanceof HotGoodViewHolder) {
            int count = 0;
            if (mData.allArray() != null) {
                count = mData.allArray().size() + 1;
            }
            else {
                count = 1;
            }
            Log.e("sss", position + " + " + count);
            WGClassifyHotGoodItem item = mData.goodArray.get(position - count);
            holder.showWithData(item);
        }
    }

    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        }
        int count = 0;
        if (mData.allArray() != null) {
            count += mData.allArray().size();
        }
        if (mData.goodArray != null) {
            if (mData.goodArray.size() > 0) {
                count += (mData.goodArray.size() + 1);
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewTypeAfterOrdinal(position).ordinal();
    }

    Item_Type getItemViewTypeAfterOrdinal(int position) {
        Item_Type type = Item_Type.ITEM_TYPE_None;
        if (mData != null) {
            int count = 0;
            if (mData.allArray() != null) {
                count = mData.allArray().size();
            }
            if (count > position) {
                type = Item_Type.ITEM_TYPE_Classify;
            }
            else if (count == position) {
                type = Item_Type.ITEM_TYPE_HotTitle;
            }
            else {
                type = Item_Type.ITEM_TYPE_HotGood;
            }
        }
        Log.e("sss", position + ":" + type.ordinal());
        return type;
    }

    //Classify
    class HotGoodViewHolder extends JHBaseViewHolder {

        public ImageView imageView;
        public TextView nameTextView;
        public ImageView numberImageView;
        public TextView currentPriceTextView;
        public TextView priceTextView;

        public HotGoodViewHolder(View view) {
            super(view);
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            imageView = (ImageView) view.findViewById(R.id.imageView);
            numberImageView = (ImageView) view.findViewById(R.id.numberImageView);
            currentPriceTextView = (TextView) view.findViewById(R.id.currentPriceTextView);
            priceTextView = (TextView) view.findViewById(R.id.priceTextView);
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (object instanceof WGClassifyHotGoodItem) {
                WGClassifyHotGoodItem item = (WGClassifyHotGoodItem) object;
                nameTextView.setText(item.name);
                JHImageUtils.getInstance().loadImage(item.pictureURL, R.drawable.common_image_loading_carousel, imageView);
                //numberImageView
                currentPriceTextView.setText(item.currentPrice);
                priceTextView.setText(item.price);
                priceTextView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            }
        }
    }
}
