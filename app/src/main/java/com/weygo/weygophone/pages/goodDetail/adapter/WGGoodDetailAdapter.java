package com.weygo.weygophone.pages.goodDetail.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;
import com.weygo.weygophone.pages.goodDetail.widget.WGGoodDetailCommodityDescriptionView;
import com.weygo.weygophone.pages.goodDetail.widget.WGGoodDetailConfigView;
import com.weygo.weygophone.pages.goodDetail.widget.WGGoodDetailDescriptionView;
import com.weygo.weygophone.pages.goodDetail.widget.WGGoodDetailImagesView;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodsView;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;

/**
 * Created by muma on 2017/5/30.
 */

public class WGGoodDetailAdapter extends JHRecyclerViewAdapter {

    WGGoodDetail mGoodDetail;

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_GoodDetailImage,
        ITEM_TYPE_GoodDetailConfig,
        ITEM_TYPE_GoodDetailDes,
        ITEM_TYPE_GoodDetailCommityDes,
        ITEM_TYPE_GoodDetailRecommendGoods
    }

    public static interface GoodDetailItemClickListener extends OnBaseItemClickListener {

        void onGoodItemClick(View view, WGHomeFloorContentGoodItem item);
    }

    public void setListener(GoodDetailItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public WGGoodDetailAdapter(Context context, WGGoodDetail data) {
        mGoodDetail = data;
        this.mContext = context;
    }

    public void setData(WGGoodDetail data) {
        mGoodDetail = data;
        notifyDataSetChanged();
    }

    boolean hasProductDes() {
        if (mGoodDetail != null && mGoodDetail.productDes != null && mGoodDetail.productDes.size() > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        super.onCreateViewHolder(parent, viewType);
        View view = null;
        if (viewType == Item_Type.ITEM_TYPE_GoodDetailImage.ordinal()) {
            view = LayoutInflater.from(
                    mContext).inflate(R.layout.wggooddetail_image, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_GoodDetailConfig.ordinal()) {
            view = LayoutInflater.from(
                    mContext).inflate(R.layout.wggooddetail_config, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_GoodDetailDes.ordinal()) {
            WGGoodDetailDescriptionView commonView = (WGGoodDetailDescriptionView)LayoutInflater.from(
                    mContext).inflate(R.layout.wggooddetail_description, parent,
                    false);
            commonView.showWithData(mGoodDetail);
            view = commonView;
        }
        else if (viewType == Item_Type.ITEM_TYPE_GoodDetailCommityDes.ordinal()) {
            if (hasProductDes()) {
                WGGoodDetailCommodityDescriptionView commonView = (WGGoodDetailCommodityDescriptionView)LayoutInflater.from(
                        mContext).inflate(R.layout.wggooddetail_commodity_description, parent,
                        false);
                view = commonView;
            }
            else {
                View emptyView = new View(mContext);
                view = emptyView;
            }
        }
        else if (viewType == Item_Type.ITEM_TYPE_GoodDetailRecommendGoods.ordinal()) {
            WGOrderListGoodsView commonView = (WGOrderListGoodsView)LayoutInflater.from(
                    mContext).inflate(R.layout.wgorderlist_goods, parent,
                    false);
            commonView.setListener(new WGOrderListGoodsView.OnItemListener() {
                @Override
                public void onGoodItem(WGHomeFloorContentGoodItem item) {
                    if (mOnItemClickListener != null) {
                        GoodDetailItemClickListener temp = (GoodDetailItemClickListener)mOnItemClickListener;
                        temp.onGoodItemClick(null, item);
                    }
                }
            });
            view = commonView;
        }
        GoodDetailItemViewHolder holder = new GoodDetailItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.showWithData(mGoodDetail);
    }

    @Override
    public int getItemCount() {
        if (mGoodDetail == null) {
            return 0;
        }
        return 5;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemTypeWithPosition(position).ordinal();
    }

    Item_Type getItemTypeWithPosition(int position) {
        Item_Type type = Item_Type.ITEM_TYPE_None;
        if (position == 0) {
            type = Item_Type.ITEM_TYPE_GoodDetailImage;
        }
        else if (position == 1) {
            type = Item_Type.ITEM_TYPE_GoodDetailConfig;
        }
        else if (position == 2) {
            type = Item_Type.ITEM_TYPE_GoodDetailDes;
        }
        else if (position == 3) {
            type = Item_Type.ITEM_TYPE_GoodDetailCommityDes;
        }
        else if (position == 4) {
            type = Item_Type.ITEM_TYPE_GoodDetailRecommendGoods;
        }
        return type;
    }

    class GoodDetailItemViewHolder extends JHBaseViewHolder {

        View mView;

        public GoodDetailItemViewHolder(View view) {
            super(view);
            mView = view;
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (object == null) {
                return;
            }
            WGGoodDetail item = (WGGoodDetail)object;
            int tag = (int) mView.getTag();
            if (tag == 0) {
                WGGoodDetailImagesView view = (WGGoodDetailImagesView) mView;
                List list = new ArrayList();
                for(WGCarouselFigureItem carouseItem : item.carouselFigures) {
                    list.add(carouseItem.pictureURL);
                }
                view.showWithArray(list);
            }
            else if (tag == 1) {
                WGGoodDetailConfigView view = (WGGoodDetailConfigView) mView;
                view.showWithData(item);
            }
            else if (tag == 2) {
                WGGoodDetailDescriptionView view = (WGGoodDetailDescriptionView) mView;
            }
            else if (tag == 3) {
                if (hasProductDes()) {
                    WGGoodDetailCommodityDescriptionView view = (WGGoodDetailCommodityDescriptionView) mView;
                    view.showWithData(item);
                }
                else {

                }
            }
            else if (tag == 4) {
                WGOrderListGoodsView view = (WGOrderListGoodsView) mView;
                view.showWithArray(item.recommendProduce);
            }
        }
    }
}
