package com.weygo.weygophone.pages.order.commit.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseViewHolder;
import com.weygo.weygophone.common.widget.WGCellStyle6View;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDeliverTime;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderDetail;
import com.weygo.weygophone.pages.order.commit.model.WGCommitOrderPay;
import com.weygo.weygophone.pages.order.commit.model.WGSettlementTips;
import com.weygo.weygophone.pages.order.commit.widget.WGCommitOrderLookMoreTipsView;
import com.weygo.weygophone.pages.order.commit.widget.WGCommitOrderOverWeightItemView;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodsView;
import com.weygo.weygophone.pages.receipt.model.WGReceipt;
import com.weygo.weygophone.pages.tabs.home.adapter.WGHomeFragmentAdapter;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorGoodsColumnView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by muma on 2017/8/20.
 */

public class WGCommitOrderAdapter extends JHRecyclerViewAdapter {

    public WGCommitOrderAdapter(Context context, WGCommitOrderDetail data) {
        this.mData = data;
        this.mContext = context;
    }

    public interface OnItemListener extends OnBaseItemClickListener {
        void onAddress(WGAddress address);
        void onReceipt(WGReceipt receipt);
        void onDeliverDate(WGCommitOrderDeliverTime data);
        void onDeliverTime(WGCommitOrderDeliverTime data);
        void onPayMethod(WGCommitOrderPay data);
        void onIntegral(WGCommitOrderDetail data);
        void onCoupon(WGCoupon data);
        void onGoodItem(WGOrderGoodItem item);
        void onLookMore(WGCommitOrderDetail data);
    }

    public void setListener(OnItemListener listener) {
        mOnItemClickListener = listener;
    }

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_Address,
        ITEM_TYPE_ReceiptTitle,
        ITEM_TYPE_Receipt,
        ITEM_TYPE_ReceiptEmpty,
        ITEM_TYPE_DeliverDateTitle,
        ITEM_TYPE_DeliverDate,
        ITEM_TYPE_DeliverTime,
        ITEM_TYPE_PayMethodTitle,
        ITEM_TYPE_Integral,
        ITEM_TYPE_PayMethod,
        ITEM_TYPE_Coupon,
        ITEM_TYPE_ConsumptionTitle,
        ITEM_TYPE_Consumption,
        ITEM_TYPE_ConsumptionGoodTitle,
        ITEM_TYPE_ConsumptionGood,
        ITEM_TYPE_Remark,
        ITEM_TYPE_DeliverTips,
        ITEM_TYPE_LookMore,
        ITEM_TYPE_MinPriceTips,
        ITEM_TYPE_SeparateLine
    }

    class CellData extends Object {

        public Item_Type mType;

        public Object mObject;

        public CellData(Item_Type type, Object object) {
            mType = type;
            mObject = object;
        }
    }

    Map<Integer, WGCommitOrderAdapter.CellData> mPostionValueMap = new HashMap<>();

    WGCommitOrderDetail mData;
    public void setData(WGCommitOrderDetail data) {
        mData = data;
        mPostionValueMap.clear();
        if (mData == null) {
            notifyDataSetChanged();
            return;
        }
        int key = 0;
        //Address
        CellData value = new CellData(Item_Type.ITEM_TYPE_Address, mData.address);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_ReceiptTitle, null);
        mPostionValueMap.put(key, value);
        key += 1;

        if (mData.receipt == null) {
            value = new CellData(Item_Type.ITEM_TYPE_ReceiptEmpty,
                    null);
            mPostionValueMap.put(key, value);
            key += 1;
        }
        else {
            value = new CellData(Item_Type.ITEM_TYPE_Receipt,
                    mData.receipt);
            mPostionValueMap.put(key, value);
            key += 1;
        }

        value = new CellData(Item_Type.ITEM_TYPE_SeparateLine,
                null);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_DeliverDateTitle,
                null);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_DeliverDate,
                mData.deliverTime);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_DeliverTime,
                mData.deliverTime);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_SeparateLine,
                null);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_PayMethodTitle,
                null);
        mPostionValueMap.put(key, value);
        key += 1;

        if (mData != null && mData.integration > 0) {
            value = new CellData(Item_Type.ITEM_TYPE_Integral,
                    mData);
            mPostionValueMap.put(key, value);
            key += 1;
        }

        value = new CellData(Item_Type.ITEM_TYPE_PayMethod,
                mData.payMothod);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_Coupon,
                mData.coupon);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_SeparateLine,
                null);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_ConsumptionTitle,
                null);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_Consumption,
                mData.consumePrice);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_SeparateLine,
                null);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_ConsumptionGoodTitle,
                null);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_ConsumptionGood,
                mData.goods);
        mPostionValueMap.put(key, value);
        key += 1;

        value = new CellData(Item_Type.ITEM_TYPE_Remark,
                mData.remark);
        mPostionValueMap.put(key, value);
        key += 1;

        if (!JHStringUtils.isNullOrEmpty(mData.tip.orderChangeTip)) {
            value = new CellData(Item_Type.ITEM_TYPE_SeparateLine,
                    null);
            mPostionValueMap.put(key, value);
            key += 1;

            value = new CellData(Item_Type.ITEM_TYPE_DeliverTips,
                    mData.tip);
            mPostionValueMap.put(key, value);
            key += 1;
        }

        if (!JHStringUtils.isNullOrEmpty(mData.tip.orderPriceTip)) {
            value = new CellData(Item_Type.ITEM_TYPE_SeparateLine,
                    null);
            mPostionValueMap.put(key, value);
            key += 1;

            value = new CellData(Item_Type.ITEM_TYPE_LookMore,
                    mData.tip);
            mPostionValueMap.put(key, value);
            key += 1;
        }

        if (!JHStringUtils.isNullOrEmpty(mData.minPriceTips)) {
            value = new CellData(Item_Type.ITEM_TYPE_SeparateLine,
                    null);
            mPostionValueMap.put(key, value);
            key += 1;

            value = new CellData(Item_Type.ITEM_TYPE_MinPriceTips,
                    mData);
            mPostionValueMap.put(key, value);
        }

        notifyDataSetChanged();
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = 0;
        View view = null;
        if (viewType == Item_Type.ITEM_TYPE_Address.ordinal()) {
            resourceId = R.layout.commitorder_address_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleTouchAddress(mData.address);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_ReceiptTitle.ordinal()) {
            resourceId = R.layout.common_cell_type6;
            WGCellStyle6View tempView = (WGCellStyle6View) LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            tempView.setTitle(R.string.CommitOrder_Fax);
            view = tempView;
        }
        else if (viewType == Item_Type.ITEM_TYPE_ReceiptEmpty.ordinal()) {
            resourceId = R.layout.commitorder_emptyreceipt_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleTouchReceipt(null);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_Receipt.ordinal()) {
            resourceId = R.layout.commitorder_receipt_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleTouchReceipt(mData.receipt);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_DeliverDateTitle.ordinal()) {
            resourceId = R.layout.common_cell_type6;
            WGCellStyle6View tempView = (WGCellStyle6View) LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            tempView.setTitle(R.string.CommitOrder_Deliver);
            view = tempView;
        }
        else if (viewType == Item_Type.ITEM_TYPE_DeliverDate.ordinal()) {
            resourceId = R.layout.commitorder_deliverdate_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleTouchDeliverDate(mData.deliverTime);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_DeliverTime.ordinal()) {
            resourceId = R.layout.commitorder_delivertime_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleTouchDeliverTime(mData.deliverTime);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_PayMethodTitle.ordinal()) {
            resourceId = R.layout.common_cell_type6;
            WGCellStyle6View tempView = (WGCellStyle6View) LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            tempView.setTitle(R.string.CommitOrder_Pay_Mode);
            view = tempView;
        }
        else if (viewType == Item_Type.ITEM_TYPE_Integral.ordinal()) {
            resourceId = R.layout.commitorder_integral_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleTouchIntegral(mData);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_PayMethod.ordinal()) {
            resourceId = R.layout.commitorder_paymethod_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleTouchPayMethod(mData.payMothod);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_Coupon.ordinal()) {
            resourceId = R.layout.commitorder_coupon_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleTouchCoupon(mData.coupon);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_ConsumptionTitle.ordinal()) {
            resourceId = R.layout.common_cell_type6;
            WGCellStyle6View tempView = (WGCellStyle6View) LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            tempView.setTitle(R.string.CommitOrder_Consume);
            view = tempView;
        }
        else if (viewType == Item_Type.ITEM_TYPE_Consumption.ordinal()) {
            resourceId = R.layout.commitorder_consumption_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_ConsumptionGoodTitle.ordinal()) {
            resourceId = R.layout.common_cell_type6;
            WGCellStyle6View tempView = (WGCellStyle6View) LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            tempView.setTitle(R.string.CommitOrder_Consume_List);
            view = tempView;
        }
        else if (viewType == Item_Type.ITEM_TYPE_ConsumptionGood.ordinal()) {
            resourceId = R.layout.wghome_content_goods_column;
            WGHomeContentFloorGoodsColumnView tempView = (WGHomeContentFloorGoodsColumnView) LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view = tempView;
        }
        else if (viewType == Item_Type.ITEM_TYPE_Remark.ordinal()) {
            resourceId = R.layout.commitorder_remark_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_DeliverTips.ordinal()) {
            resourceId = R.layout.commitorder_delivertips_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_LookMore.ordinal()) {
            resourceId = R.layout.commitorder_lookmore_view;
            WGCommitOrderLookMoreTipsView tempView = (WGCommitOrderLookMoreTipsView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            tempView.setListener(new WGCommitOrderLookMoreTipsView.OnItemListener() {
                @Override
                public void onMore(WGSettlementTips tips) {
                    handleTouchLookMore(mData);
                }
            });
            view = tempView;
        }
        else if (viewType == Item_Type.ITEM_TYPE_MinPriceTips.ordinal()) {
            resourceId = R.layout.commitorder_minpricetips_view;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_SeparateLine.ordinal()) {
            resourceId = R.layout.wghome_content_separateline;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        WGBaseViewHolder holder = new WGBaseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        CellData item = mPostionValueMap.get(position);
        holder.showWithData(item.mObject);
    }

    @Override
    public int getItemCount() {
        if (mPostionValueMap == null) {
            return 0;
        }
        return mPostionValueMap.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mPostionValueMap != null && mPostionValueMap.size() > position) {
            CellData data = mPostionValueMap.get(position);
            return data.mType.ordinal();
        }
        return WGHomeFragmentAdapter.Item_Type.ITEM_TYPE_None.ordinal();
    }

    void handleTouchAddress(WGAddress data) {
        if (mOnItemClickListener instanceof OnItemListener) {
            ((OnItemListener) mOnItemClickListener).onAddress(data);
        }
    }

    void handleTouchReceipt(WGReceipt data) {
        if (mOnItemClickListener instanceof OnItemListener) {
            ((OnItemListener) mOnItemClickListener).onReceipt(data);
        }
    }

    void handleTouchDeliverDate(WGCommitOrderDeliverTime data) {
        if (mOnItemClickListener instanceof OnItemListener) {
            ((OnItemListener) mOnItemClickListener).onDeliverDate(data);
        }
    }

    void handleTouchDeliverTime(WGCommitOrderDeliverTime data) {
        if (mOnItemClickListener instanceof OnItemListener) {
            ((OnItemListener) mOnItemClickListener).onDeliverTime(data);
        }
    }

    void handleTouchPayMethod(WGCommitOrderPay data) {
        if (mOnItemClickListener instanceof OnItemListener) {
            ((OnItemListener) mOnItemClickListener).onPayMethod(data);
        }
    }

    void handleTouchIntegral(WGCommitOrderDetail data) {
        if (mOnItemClickListener instanceof OnItemListener) {
            ((OnItemListener) mOnItemClickListener).onIntegral(data);
        }
    }

    void handleTouchCoupon(WGCoupon data) {
        if (mOnItemClickListener instanceof OnItemListener) {
            ((OnItemListener) mOnItemClickListener).onCoupon(data);
        }
    }

    void handleTouchLookMore(WGCommitOrderDetail data) {
        if (mOnItemClickListener instanceof OnItemListener) {
            ((OnItemListener) mOnItemClickListener).onLookMore(data);
        }
    }
}
