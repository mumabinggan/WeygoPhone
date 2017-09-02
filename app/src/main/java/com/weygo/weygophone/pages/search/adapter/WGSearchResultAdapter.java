package com.weygo.weygophone.pages.search.adapter;

import android.content.Context;
import android.util.Log;
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
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.receipt.model.WGReceipt;
import com.weygo.weygophone.pages.search.model.WGSearchData;
import com.weygo.weygophone.pages.search.model.WGSearchKeywordItem;
import com.weygo.weygophone.pages.search.widget.WGSearchResultHotView;
import com.weygo.weygophone.pages.tabs.home.adapter.WGHomeFragmentAdapter;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorGoodsColumnView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muma on 2017/8/20.
 */

public class WGSearchResultAdapter extends JHRecyclerViewAdapter {

    public WGSearchResultAdapter(Context context, WGSearchData data) {
        this.mData = data;
        this.mContext = context;
    }

    public interface OnItemListener extends OnBaseItemClickListener {
        void onGoodItem(WGHomeFloorContentGoodItem item);
        void onHotItem(WGSearchKeywordItem item);
    }

    public void setListener(OnItemListener listener) {
        mOnItemClickListener = listener;
    }

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_Hot,
        ITEM_TYPE_GoodListItem,
        ITEM_TYPE_GoodGrid,
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

    Map<Integer, WGSearchResultAdapter.CellData> mPostionValueMap = new HashMap<>();

    WGSearchData mData;
    public void setData(WGSearchData data) {
        mData = data;
        mPostionValueMap.clear();
        if (mData == null) {
            notifyDataSetChanged();
            return;
        }
        int key = 0;
        if (mData.keywords != null && mData.keywords.size() > 0) {
            CellData value = new CellData(Item_Type.ITEM_TYPE_Hot, mData);
            mPostionValueMap.put(key, value);
            key += 1;
        }
        List<WGHomeFloorContentGoodItem> goods = mData.goods;
        if (goods == null) {
            return;
        }
        if (mData.isGrid) {
            int count = goods.size();
            if (count % 2 == 1) {
                count += 1;
            }
            for (int num = 0; num < count/2; ++num) {
                List<WGHomeFloorContentGoodItem> list = new ArrayList();
                WGHomeFloorContentGoodItem item = goods.get(2 * num);
                list.add(item);

                int index = 2*num + 1;
                if (count > index) {
                    item = goods.get(index);
                    list.add(item);
                }

                CellData value = new CellData(Item_Type.ITEM_TYPE_GoodGrid, list);
                mPostionValueMap.put(key, value);
                key += 1;
            }
        }
        else {
            for (int num = 0; num < goods.size(); num++) {
                WGHomeFloorContentGoodItem item = goods.get(num);
                CellData value = new CellData(Item_Type.ITEM_TYPE_GoodListItem, item);
                mPostionValueMap.put(key, value);
                key += 1;
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = 0;
        View view = null;
        if (viewType == Item_Type.ITEM_TYPE_Hot.ordinal()) {
            resourceId = R.layout.search_result_hot;
            WGSearchResultHotView tempView = (WGSearchResultHotView) LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            tempView.setListener(new WGSearchResultHotView.OnItemListener() {
                @Override
                public void onHotItem(WGSearchKeywordItem item) {
                    handleHotItem(item);
                }
            });
            view = tempView;
        }
        else if (viewType == Item_Type.ITEM_TYPE_GoodListItem.ordinal()) {
            resourceId = R.layout.wggood_list_item;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleGoodItem(view);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_GoodGrid.ordinal()) {
            resourceId = R.layout.wghome_content_floor_good_grid_cell;
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

    void handleGoodItem(View view) {
        Log.e("-----", "-----handleGoodItem----");
        if (mOnItemClickListener instanceof OnItemListener) {
            WGHomeFloorContentGoodItem data = (WGHomeFloorContentGoodItem)mPostionValueMap.get(view.getTag()).mObject;
            ((OnItemListener) mOnItemClickListener).onGoodItem(data);
        }
    }

    void handleHotItem(WGSearchKeywordItem data) {
        if (mOnItemClickListener instanceof OnItemListener) {
            ((OnItemListener) mOnItemClickListener).onHotItem(data);
        }
    }
}
