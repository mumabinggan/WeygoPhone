package com.weygo.weygophone.pages.shopcart.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.SwipeItemLayout;
import com.weygo.weygophone.pages.shopcart.model.WGShopCart;
import com.weygo.weygophone.pages.shopcart.model.WGShopCartGoodItem;
import com.weygo.weygophone.pages.shopcart.widget.WGShopCartItemView;
import com.weygo.weygophone.pages.tabs.classify.adapter.WGClassifyAdapter;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.adapter.WGHomeFragmentAdapter;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.List;

/**
 * Created by muma on 2017/8/18.
 */

public class WGShopCartListAdater extends JHRecyclerViewAdapter {

    Context mContext;

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_ShopCartGoodItem,
        ITEM_TYPE_ShopCartMinTips,
        ITEM_TYPE_ShopCartSeparateLine,
    }

    public interface OnListener {

        void onGoodItemClick(View view, WGShopCartGoodItem item);

        void onDeleteGoodItem(View view, WGShopCartGoodItem item);

        void onUpdateGoodItem(WGShopCartGoodItem item);

        void onAddGoodItem(View view, WGShopCartGoodItem item);

        void onSubGoodItem(View view, WGShopCartGoodItem item);

        void onLongTouchItem(View view, WGShopCartGoodItem item);
    }

    OnListener mListener;
    public void setListener(OnListener listener) {
        mListener = listener;
    }

    WGShopCart mData;
    public void setData(WGShopCart data) {
        mData = data;
        notifyDataSetChanged();
    }

    public WGShopCartListAdater(Context context, WGShopCart data) {
        this.mData = data;
        this.mContext = context;
    }

    boolean hasMinTips() {
        if (mData != null && !JHStringUtils.isNullOrEmpty(mData.minPriceTips)) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == Item_Type.ITEM_TYPE_ShopCartGoodItem.ordinal()) {
            int resourceId = R.layout.wgshopcart_list_item;
            WGShopCartItemView temp = (WGShopCartItemView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setLister(new WGShopCartItemView.GoodListItemOnListener() {
                @Override
                public void onTouchShopCart(WGHomeFloorContentGoodItem item) {
                    if (mListener != null) {
                        mListener.onGoodItemClick(null, (WGShopCartGoodItem) item);
                    }
                }

                @Override
                public void onAddGood(WGHomeFloorContentGoodItem item) {
                    if (mListener != null) {
                        mListener.onAddGoodItem(null, (WGShopCartGoodItem) item);
                    }
                }

                @Override
                public void onSubGood(WGHomeFloorContentGoodItem item) {
                    if (mListener != null) {
                        mListener.onSubGoodItem(null, (WGShopCartGoodItem) item);
                    }
                }

                @Override
                public void onLongTouch(WGHomeFloorContentGoodItem item) {
                    if (mListener != null) {
                        mListener.onLongTouchItem(null, (WGShopCartGoodItem) item);
                    }
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_ShopCartMinTips.ordinal()) {
            int resourceId = R.layout.common_cell_type3;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_ShopCartSeparateLine.ordinal()) {
            int resourceId = R.layout.wghome_content_separateline;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        ShopCartViewHolder holder = new ShopCartViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (mData.goods.size() > position) {
            WGShopCartGoodItem item = mData.goods.get(position);
            holder.showWithData(item);
        }
        else {
            holder.showWithData(mData.minPriceTips);
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mData != null) {
            if (mData.goods != null) {
                count += mData.goods.size();
            }
            if (hasMinTips()) {
                count += 1;
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (mData != null) {
            if (mData.goods != null) {
                if (mData.goods.size() == position) {
                    return Item_Type.ITEM_TYPE_ShopCartMinTips.ordinal();
                }
                else {
                    return Item_Type.ITEM_TYPE_ShopCartGoodItem.ordinal();
                }
            }
        }
        return WGHomeFragmentAdapter.Item_Type.ITEM_TYPE_None.ordinal();
    }

    //Classify
    class ShopCartViewHolder extends JHBaseViewHolder {

        View mView;

        public ShopCartViewHolder(View view) {
            super(view);
            mView = view;
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (mView instanceof JHRelativeLayout) {
                JHRelativeLayout layout = (JHRelativeLayout) mView;
                if (object instanceof List) {
                    layout.showWithArray((List) object);
                }
                else {
                    layout.showWithData(object);
                }
            }
            else if (mView instanceof JHLinearLayout) {
                JHLinearLayout layout = (JHLinearLayout) mView;
                if (object instanceof List) {
                    layout.showWithArray((List) object);
                }
                else {
                    layout.showWithData(object);
                }
            }
        }
    }

}
