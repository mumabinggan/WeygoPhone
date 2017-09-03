package com.weygo.weygophone.pages.order.detail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDeliver;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDetail;
import com.weygo.weygophone.pages.order.detail.model.WGOrderFax;
import com.weygo.weygophone.pages.order.detail.model.WGOrderPay;
import com.weygo.weygophone.pages.order.detail.model.WGOrderStatus;
import com.weygo.weygophone.pages.order.detail.widget.WGOrderDetailDeliverStatusView;
import com.weygo.weygophone.pages.order.detail.widget.WGOrderDetailFaxView;
import com.weygo.weygophone.pages.order.detail.widget.WGOrderDetailGoodItemView;
import com.weygo.weygophone.pages.order.detail.widget.WGOrderDetailGoodsMoreView;
import com.weygo.weygophone.pages.order.detail.widget.WGOrderDetailPayView;
import com.weygo.weygophone.pages.order.detail.widget.WGOrderDetailUserInfoView;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodsView;
import com.weygo.weygophone.pages.tabs.classify.adapter.WGClassifyAdapter;
import com.weygo.weygophone.pages.tabs.classify.adapter.WGSubClassifyAdapter;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;

import java.util.List;

/**
 * Created by muma on 2017/5/23.
 */

public class WGOrderDetailAdapter extends JHRecyclerViewAdapter {

    Context mContext;

    WGOrderDetail mOrderDetail;

    boolean mShowMoreGoods;

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_OrderStatus,
        ITEM_TYPE_OrderDeliver,
        ITEM_TYPE_OrderGoodsTitle,
        ITEM_TYPE_OrderGoods,
        ITEM_TYPE_OrderGoodsMore,
        ITEM_TYPE_OrderFax,
        ITEM_TYPE_OrderPay,
    }

    public WGOrderDetailAdapter(Context context, WGOrderDetail data) {
        mOrderDetail = data;
        this.mContext = context;
        mShowMoreGoods = false;
    }

    public void setData(WGOrderDetail data) {
        mOrderDetail = data;
        notifyDataSetChanged();
    }

    void handleShowMoreGood() {
        mShowMoreGoods = true;
        notifyDataSetChanged();
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        int resId = 0;
        WGOrderDetailViewHolder holder = null;
        if (viewType == Item_Type.ITEM_TYPE_OrderStatus.ordinal()) {
            resId = R.layout.wgorderdetail_order_status;
            view = LayoutInflater.from(
                    mContext).inflate(resId, parent,
                    false);
            holder = new WGOrderDetailViewHolder(view);
            holder.showWithData(mOrderDetail);
        }
        else if (viewType == Item_Type.ITEM_TYPE_OrderDeliver.ordinal()) {
            resId = R.layout.wgorderdetail_order_userinfo;
            view = LayoutInflater.from(
                    mContext).inflate(resId, parent,
                    false);
            holder = new WGOrderDetailViewHolder(view);
            holder.showWithData(mOrderDetail.deliver);
        }
        else if (viewType == Item_Type.ITEM_TYPE_OrderGoodsTitle.ordinal()) {
            resId = R.layout.wgorderdetail_order_goodtitle;
            view = LayoutInflater.from(
                    mContext).inflate(resId, parent,
                    false);
            holder = new WGOrderDetailViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_OrderGoods.ordinal()) {
            resId = R.layout.wgorderdetail_order_gooditem;
            view = LayoutInflater.from(
                    mContext).inflate(resId, parent,
                    false);
            holder = new WGOrderDetailViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_OrderGoodsMore.ordinal()) {
            resId = R.layout.wgorderdetail_order_goodmore;
            WGOrderDetailGoodsMoreView moreView = (WGOrderDetailGoodsMoreView) LayoutInflater.from(
                    mContext).inflate(resId, parent,
                    false);
            moreView.setOnShowGoodMore(new WGOrderDetailGoodsMoreView.OnGoodsMore() {
                @Override
                public void onTouchMore() {
                    handleShowMoreGood();
                }
            });
            view = moreView;
            holder = new WGOrderDetailViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_OrderFax.ordinal()) {
            resId = R.layout.wgorderdetail_order_fax;
            view = LayoutInflater.from(
                    mContext).inflate(resId, parent,
                    false);
            holder = new WGOrderDetailViewHolder(view);
            holder.showWithData(mOrderDetail.fax);
        }
        else if (viewType == Item_Type.ITEM_TYPE_OrderPay.ordinal()) {
            resId = R.layout.wgorderdetail_order_pay;
            view = LayoutInflater.from(
                    mContext).inflate(resId, parent,
                    false);
            holder = new WGOrderDetailViewHolder(view);
            holder.showWithData(mOrderDetail.pay);
        }
//        else if (viewType == Item_Type.ITEM_TYPE_OrderRebuy.ordinal()) {
//            resId = R.layout.wgorderdetail_order_rebuy;
//            view = LayoutInflater.from(
//                    mContext).inflate(resId, parent,
//                    false);
//            holder = new WGOrderDetailViewHolder(view);
//        }
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Item_Type type = getItemViewTypeWithPositon(position);
        if (type == Item_Type.ITEM_TYPE_OrderGoods) {
            if (mOrderDetail != null && mOrderDetail.goods != null) {
                int index = position - 3;
                WGOrderGoodItem item = mOrderDetail.goods.get(index);
                holder.showWithData(item);
                WGOrderDetailGoodItemView itemView = (WGOrderDetailGoodItemView) holder.itemView;
                if (mShowMoreGoods) {
                    itemView.setVisibility(true);
                }
                else {
                    itemView.setVisibility((index > 2) ? false : true);
                }
            }
        }
        if (type == Item_Type.ITEM_TYPE_OrderGoodsMore) {
            WGOrderDetailGoodsMoreView itemView = (WGOrderDetailGoodsMoreView) holder.itemView;
            itemView.setVisibility(!mShowMoreGoods);
        }
    }

    @Override
    public int getItemCount() {
        if (mOrderDetail == null) {
            return 0;
        }
        int count = 6;
        if (mOrderDetail.goods != null) {
            count += mOrderDetail.goods.size();
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewTypeWithPositon(position).ordinal();
    }

    Item_Type getItemViewTypeWithPositon(int position) {
        Item_Type type = Item_Type.ITEM_TYPE_None;
        int goodCount = 0;
        if (mOrderDetail != null && mOrderDetail.goods != null) {
            goodCount = mOrderDetail.goods.size();
        }
        if (position == 0) {
            type = Item_Type.ITEM_TYPE_OrderStatus;
        }
        else if (position == 1) {
            type = Item_Type.ITEM_TYPE_OrderDeliver;
        }
        else if (position == 2) {
            type = Item_Type.ITEM_TYPE_OrderGoodsTitle;
        }
        else if (position <= 2 + goodCount && position > 2) {
            type = Item_Type.ITEM_TYPE_OrderGoods;
        }
        else if (position == 3 + goodCount) {
            type = Item_Type.ITEM_TYPE_OrderGoodsMore;
        }
        else if (position == 4 + goodCount) {
            type = Item_Type.ITEM_TYPE_OrderFax;
        }
        else if (position == 5 + goodCount) {
            type = Item_Type.ITEM_TYPE_OrderPay;
        }
//        else if (position == 6 + mOrderDetai.goods.size()) {
//            type = Item_Type.ITEM_TYPE_OrderRebuy;
//        }
        Log.e("---getType-----", type + ":" + position);
        return type;
    }

    class WGOrderDetailViewHolder extends JHBaseViewHolder {

        View itemView;

        public WGOrderDetailViewHolder(View view) {
            super(view);
            itemView = view;
            Log.e("caonima", itemView.getClass().toString());
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (itemView instanceof WGOrderDetailDeliverStatusView) {
                ((WGOrderDetailDeliverStatusView)itemView).showWithData((WGOrderDetail) object);
            }
            else if (itemView instanceof WGOrderDetailUserInfoView) {
                ((WGOrderDetailUserInfoView)itemView).showWithData((WGOrderDeliver) object);
            }
            else if (itemView instanceof WGOrderDetailFaxView) {
                ((WGOrderDetailFaxView)itemView).showWithData((WGOrderFax) object);
            }
            else if (itemView instanceof WGOrderDetailPayView) {
                ((WGOrderDetailPayView)itemView).showWithArray((List) object);
                Log.e("-------pay--", object.toString());
            }
            else if (itemView instanceof WGOrderDetailGoodItemView) {
                ((WGOrderDetailGoodItemView)itemView).showWithData((WGOrderGoodItem) object);
            }
        }
    }
}
