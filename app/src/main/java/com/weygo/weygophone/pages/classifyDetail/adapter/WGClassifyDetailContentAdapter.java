package com.weygo.weygophone.pages.classifyDetail.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyDetail;
import com.weygo.weygophone.pages.classifyDetail.model.WGHomeRotation;
import com.weygo.weygophone.pages.classifyDetail.widget.WGClassifyDetailFilterView;
import com.weygo.weygophone.pages.collection.widget.WGGoodListView;
import com.weygo.weygophone.pages.tabs.home.adapter.WGHomeFragmentAdapter;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorBaseContentItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentItem;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorGoodsGridCellView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muma on 2017/8/2.
 */

public class WGClassifyDetailContentAdapter extends JHRecyclerViewAdapter {

    public interface OnItemClickListener extends OnBaseItemClickListener {
        void onSort(View view);
        void onFilter(View view);
        void onGrid(View view);
        void onGoodItem(WGHomeFloorContentGoodItem item);
        void onShopCart(WGHomeFloorContentGoodItem item, View view, Point point);
    }

    public WGClassifyDetailContentAdapter(Context context, WGClassifyDetail data) {
        this.mContext = context;
        setData(data);
    }

    WGClassifyDetail mData;
    public void setData(WGClassifyDetail data) {
        mData = data;
        mPostionValueMap.clear();
        if (mData == null) {
            notifyDataSetChanged();
            return;
        }
        int key = 0;
        if (mData.hasCarousel()) {
            List list = new ArrayList();
            list.add(mData.carouselFigureItem.pictureURL);
            WGHomeRotation model = new WGHomeRotation();
            model.mList = list;
            model.time = mData.snappedUpExpiredTime;
            CellData cellData = new CellData(Item_Type.ITEM_TYPE_CarouselFigures,
                    model);
            mPostionValueMap.put(key, cellData);
            key++;
        }
        if (mData.hasRecommendedArray()) {
            for (WGHomeFloorContentGoodItem item : mData.recommendedArray) {
                CellData cellData = new CellData(Item_Type.ITEM_TYPE_GoodListItem,
                        item);
                mPostionValueMap.put(key, cellData);
                key++;
            }
        }
        CellData cellData = new CellData(Item_Type.ITEM_TYPE_GoodFilter,
                null);
        mPostionValueMap.put(key, cellData);
        key++;
        notifyDataSetChanged();
        if (mData.hasGoods()) {
            if (mData.isGrid()) {
                List list = new ArrayList();
                List<WGHomeFloorContentGoodItem> goodArray = mData.goodArray;
                int count = goodArray.size();
                for (int num = 0; num < Math.ceil(count/2.0); ++num) {
                    list.clear();
                    WGHomeFloorContentGoodItem goodItem = null;
                    if (count > 2 * num) {
                        goodItem = goodArray.get(2 * num);
                        list.add(goodItem);
                    }
                    if (count > 2 * num + 1) {
                        goodItem = goodArray.get(2 * num + 1);
                        list.add(goodItem);
                    }
                    WGClassifyDetailContentAdapter.CellData value = new WGClassifyDetailContentAdapter.
                            CellData(Item_Type.ITEM_TYPE_GoodGridItem, list);
                    mPostionValueMap.put(key, value);
                    key++;
                }
            }
            else {
                for (WGHomeFloorContentGoodItem item : mData.goodArray) {
                    CellData cellData1 = new CellData(Item_Type.ITEM_TYPE_GoodListItem,
                            item);
                    mPostionValueMap.put(key, cellData1);
                    key++;
                }
            }
        }
    }

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_CarouselFigures,
        ITEM_TYPE_GoodFilter,
        ITEM_TYPE_GoodListItem,
        ITEM_TYPE_GoodGridItem
    }

    class CellData extends Object {

        public Item_Type mType;

        public Object mObject;

        public CellData(Item_Type type, Object object) {
            mType = type;
            mObject = object;
        }
    }

    Map<Integer, CellData> mPostionValueMap = new HashMap<>();

    void handleGoodItem(WGHomeFloorContentGoodItem item) {
        if (mOnItemClickListener != null) {
            OnItemClickListener temp = (OnItemClickListener)mOnItemClickListener;
            if (temp != null) {
                temp.onGoodItem(item);
            }
        }
    }

    void handleShopCart(WGHomeFloorContentGoodItem item, View view, Point fromPoint) {
        if (mOnItemClickListener != null) {
            OnItemClickListener temp = (OnItemClickListener)mOnItemClickListener;
            if (temp != null) {
                temp.onShopCart(item, view, fromPoint);
            }
        }
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = 0;
        View view = null;
        if (viewType == WGClassifyDetailContentAdapter.Item_Type.ITEM_TYPE_CarouselFigures.ordinal()) {
            resourceId = R.layout.wghome_content_rotation;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == WGClassifyDetailContentAdapter.Item_Type.
                        ITEM_TYPE_GoodListItem.ordinal()) {
            resourceId = R.layout.wggood_list_item;
            WGGoodListView temp = (WGGoodListView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGGoodListView.GoodListItemOnListener() {
                @Override
                public void onTouchShopCart(WGHomeFloorContentGoodItem item, View view, Point point) {
                    handleShopCart(item, view, point);
                }

                @Override
                public void onTouchItem(WGHomeFloorContentGoodItem item) {
                    handleGoodItem(item);
                }
            });
            view = temp;
        }
        else if (viewType == WGClassifyDetailContentAdapter.Item_Type.
                ITEM_TYPE_GoodGridItem.ordinal()) {
            resourceId = R.layout.wghome_content_floor_good_grid_cell;
            WGHomeContentFloorGoodsGridCellView temp = (WGHomeContentFloorGoodsGridCellView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGHomeContentFloorGoodsGridCellView.OnPurchaseListener() {
                @Override
                public void onPurchase(WGHomeFloorContentGoodItem item, View view, Point point) {
                    handleShopCart(item, view, point);
                }

                @Override
                public void onGoodDetail(WGHomeFloorContentGoodItem item) {
                    handleGoodItem(item);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_GoodFilter.ordinal()) {
            resourceId = R.layout.classifydetail_filter_view;
            final WGClassifyDetailFilterView filter = (WGClassifyDetailFilterView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            filter.setListener(new WGClassifyDetailFilterView.OnItemClickListener() {
                @Override
                public void onSort(View view) {
                    if (mOnItemClickListener != null) {
                        ((OnItemClickListener)mOnItemClickListener).onSort(filter);
                    }
                }

                @Override
                public void onFilter(View view) {
                    if (mOnItemClickListener != null) {
                        ((OnItemClickListener)mOnItemClickListener).onFilter(filter);
                    }
                }

                @Override
                public void onVista(View view) {
                    if (mOnItemClickListener != null) {
                        ((OnItemClickListener)mOnItemClickListener).onGrid(filter);
                    }
                }
            });
            view = filter;
        }
        HomeViewHolder holder = new HomeViewHolder(view);
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
        if (mData == null) {
            return 0;
        }
        int count = 1;  //筛选
        if (mData.hasCarousel()) {
            count += 1;
        }
        if (mData.hasRecommendedArray()) {
            count += mData.recommendedArray.size();
        }
        if (mData.hasGoods()) {
            if (mData.isGrid()) {
                count += (mData.goodArray.size() + 1)/2;
            }
            else {
                count += mData.goodArray.size();
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        if (mPostionValueMap != null && mPostionValueMap.size() > position) {
            CellData data = mPostionValueMap.get(position);
            return data.mType.ordinal();
        }
        return Item_Type.ITEM_TYPE_None.ordinal();
    }

    //Classify
    class HomeViewHolder extends JHBaseViewHolder {

        View mView;

        public HomeViewHolder(View view) {
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
