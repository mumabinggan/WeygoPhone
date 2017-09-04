package com.weygo.weygophone.pages.tabs.home.adapter;

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
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.collection.widget.WGGoodListView;
import com.weygo.weygophone.pages.common.widget.WGCommonHorizontalListView;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;
import com.weygo.weygophone.pages.goodDetail.widget.WGGoodDetailImagesView;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodsView;
import com.weygo.weygophone.pages.tabs.classify.adapter.WGClassifyAdapter;
import com.weygo.weygophone.pages.tabs.classify.adapter.WGSubClassifyAdapter;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHome;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorBaseContentItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentCountryItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorClassifyColumnView;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorClassifyListItemView;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorClassifysGridView;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorCountryColumnView;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorGoodsColumnView;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorGoodsGridCellView;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentFloorImageTitleView;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeContentRotationView;
import com.weygo.weygophone.pages.tabs.home.widget.WGHomeTopicsView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muma on 2017/6/5.
 */

public class WGHomeFragmentAdapter extends JHRecyclerViewAdapter {

    public WGHomeFragmentAdapter(Context context, WGHome data) {
        this.mHome = data;
        this.mContext = context;
    }

    public interface OnItemListener {
        void onCarouselFigures(int index);
        void onTopicItem(WGTopicItem item);
        void onFloorHead(WGHomeFloorItem item);
        void onFloorCountryItem(WGHomeFloorBaseContentItem item);
        void onPurchase(WGHomeFloorContentGoodItem item, View view, Point fromPoint);
        void onGoodItem(WGHomeFloorContentGoodItem item);
        void onClassifyItem(WGHomeFloorBaseContentItem item);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_HomeCarouselFigures,
        ITEM_TYPE_HomeTopics,
        ITEM_TYPE_HomeFloorTitle,
        ITEM_TYPE_HomeFloorImageTitle,
        ITEM_TYPE_HomeFloorGoodListItem,
        ITEM_TYPE_HomeFloorGoodColumn,
        ITEM_TYPE_HomeFloorGoodGrid,
        ITEM_TYPE_HomeFloorClassifyListCellItem,
        ITEM_TYPE_HomeFloorClassifyGrid,
        ITEM_TYPE_HomeFloorClassifyColumn,
        ITEM_TYPE_HomeFloorCountryColumn,
        ITEM_TYPE_HomeFloorSeparateLine,
    }

    class HomeCellData extends Object {

        public Item_Type mType;

        public Object mObject;

        public HomeCellData(Item_Type type, Object object) {
            mType = type;
            mObject = object;
        }
    }

    WGHome mHome;

    Map<Integer, HomeCellData> mPostionValueMap = new HashMap<>();

    public void setHome(WGHome home) {
        mHome = home;
        mPostionValueMap.clear();
        if (mHome == null) {
            notifyDataSetChanged();
            return;
        }
        int key = 0;
        if (home.carouselFigures != null && home.carouselFigures.size() > 0) {
            List list = new ArrayList();
            for (WGCarouselFigureItem item : home.carouselFigures) {
                list.add(item.pictureURL);
            }
            HomeCellData value = new HomeCellData(Item_Type.ITEM_TYPE_HomeCarouselFigures, list);
            mPostionValueMap.put(key, value);
            key += 1;
        }
        if (home.topics != null && home.topics.size() > 0) {
            HomeCellData value = new HomeCellData(Item_Type.ITEM_TYPE_HomeTopics, home.topics);
            mPostionValueMap.put(key, value);
            key += 1;
        }
        if (home.floors != null) {
            for (WGHomeFloorItem item : home.floors) {
                if (item.hasTitle()) {
                    HomeCellData value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorTitle, item);
                    mPostionValueMap.put(key, value);
                    key += 1;
                }
                if (item.hasImageTitle()) {
                    HomeCellData value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorImageTitle, item);
                    mPostionValueMap.put(key, value);
                    key += 1;
                }
                if (item.type == WGConstants.WGHomeFloorItemTypeGoodList ||
                        item.type == WGConstants.WGHomeFloorItemTypeClassifyList) {
                    for (WGHomeFloorContentItem contentItem : item.content) {
                        WGHomeFloorBaseContentItem goodItem = contentItem
                                .contentItemWithType(item.type);
                        goodItem.jumpType = contentItem.jumpType;
                        HomeCellData value = null;
                        if (item.type == WGConstants.WGHomeFloorItemTypeGoodList) {
                            value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorGoodListItem, goodItem);
                        }
                        else if (item.type == WGConstants.WGHomeFloorItemTypeClassifyList) {
                            value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorClassifyListCellItem, goodItem);
                        }
                        if (value != null) {
                            mPostionValueMap.put(key, value);
                            key += 1;
                        }
                    }
                }
                else if (item.type == WGConstants.WGHomeFloorItemTypeGoodColumn ||
                        item.type == WGConstants.WGHomeFloorItemTypeClassifyColumn ||
                        item.type == WGConstants.WGHomeFloorItemTypeClassifyGrid ||
                        item.type == WGConstants.WGHomeFloorItemTypeCountryColumn) {
                    if (item.content != null) {
                        List list = new ArrayList();
                        for (WGHomeFloorContentItem contentItem : item.content) {
                            WGHomeFloorBaseContentItem goodItem = contentItem
                                    .contentItemWithType(item.type);
                            goodItem.jumpType = contentItem.jumpType;
                            list.add(goodItem);
                        }
                        HomeCellData value = null;
                        if (item.type == WGConstants.WGHomeFloorItemTypeGoodColumn) {
                            value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorGoodColumn, list);
                        }
                        else if (item.type == WGConstants.WGHomeFloorItemTypeClassifyColumn) {
                            value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorClassifyColumn, list);
                        }
                        else if (item.type == WGConstants.WGHomeFloorItemTypeClassifyGrid) {
                            value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorClassifyGrid, list);
                        }
                        else if (item.type == WGConstants.WGHomeFloorItemTypeCountryColumn) {
                            value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorCountryColumn, list);
                        }
                        if (value != null) {
                            mPostionValueMap.put(key, value);
                            key += 1;
                        }
                    }
                }
                else if (item.type == WGConstants.WGHomeFloorItemTypeGoodGrid) {
                    List list = new ArrayList();
                    HomeCellData value = null;
                    int count = item.content.size();
                    for (int num = 0; num < Math.ceil(count/2.0); ++num) {
                        list.clear();
                        WGHomeFloorBaseContentItem goodItem = null;
                        WGHomeFloorContentItem contentItem = null;
                        if (count > 2 * num) {
                            contentItem = item.content.get(2 * num);
                            goodItem = contentItem
                                    .contentItemWithType(item.type);
                            list.add(goodItem);
                        }
                        if (count > 2 * num + 1) {
                            contentItem = item.content.get(2 * num + 1);
                            goodItem = contentItem
                                    .contentItemWithType(item.type);
                            list.add(goodItem);
                        }
                        value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorGoodGrid, list);
                        mPostionValueMap.put(key, value);
                        key += 1;
                    }

                }
                //加分隔线
                HomeCellData value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorSeparateLine, null);
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
        if (viewType == Item_Type.ITEM_TYPE_HomeCarouselFigures.ordinal()) {
            resourceId = R.layout.wghome_content_rotation;
            WGHomeContentRotationView temp = (WGHomeContentRotationView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGGoodDetailImagesView.OnItemListener() {
                @Override
                public void onItemClick(int index) {
                    handleCarouselFigures(index);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeTopics.ordinal()) {
            resourceId = R.layout.wghome_content_topics;
            WGHomeTopicsView temp = (WGHomeTopicsView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGCommonHorizontalListView.OnItemListener() {
                @Override
                public void onItemClick(Object object) {
                    handleTopicItem((WGTopicItem)object);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorTitle.ordinal()) {
            resourceId = R.layout.wghome_content_floor_title;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //handleClickView(view);
                }
            });
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorImageTitle.ordinal()) {
            resourceId = R.layout.wghome_content_floor_imagetitle;
            WGHomeContentFloorImageTitleView temp = (WGHomeContentFloorImageTitleView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGHomeContentFloorImageTitleView.OnItemListener() {
                @Override
                public void onItemClick(Object object) {
                    handleFloorHead((WGHomeFloorItem)object);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorGoodListItem.ordinal()) {
            resourceId = R.layout.wggood_list_item;
            WGGoodListView temp = (WGGoodListView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGGoodListView.GoodListItemOnListener() {
                @Override
                public void onTouchShopCart(WGHomeFloorContentGoodItem item, View view1, Point point) {
                    handlePurchase(item, view1, point);
                }

                @Override
                public void onTouchItem(WGHomeFloorContentGoodItem item) {
                    handleGoodItem(item);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorGoodColumn.ordinal()) {
            resourceId = R.layout.wghome_content_goods_column;
            WGHomeContentFloorGoodsColumnView temp = (WGHomeContentFloorGoodsColumnView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGCommonHorizontalListView.OnItemListener() {
                @Override
                public void onItemClick(Object object) {
                    handleGoodItem((WGHomeFloorContentGoodItem)object);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorGoodGrid.ordinal()) {
            resourceId = R.layout.wghome_content_floor_good_grid_cell;
            WGHomeContentFloorGoodsGridCellView temp = (WGHomeContentFloorGoodsGridCellView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGHomeContentFloorGoodsGridCellView.OnPurchaseListener() {
                @Override
                public void onPurchase(WGHomeFloorContentGoodItem item, View view, Point point) {
                    handlePurchase(item, view, point);
                }

                @Override
                public void onGoodDetail(WGHomeFloorContentGoodItem item) {
                    handleGoodItem(item);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorClassifyListCellItem.ordinal()) {
            resourceId = R.layout.wghome_content_floor_classify_list_item;
            WGHomeContentFloorClassifyListItemView temp = (WGHomeContentFloorClassifyListItemView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGHomeContentFloorClassifyListItemView.OnItemListener() {
                @Override
                public void onItemClick(WGHomeFloorContentClassifyItem data) {
                    handleClassifyItem(data);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorClassifyGrid.ordinal()) {
            resourceId = R.layout.wghome_content_floor_classify_grid;
            WGHomeContentFloorClassifysGridView temp = (WGHomeContentFloorClassifysGridView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGHomeContentFloorClassifysGridView.OnItemListener() {
                @Override
                public void onItemClick(WGHomeFloorContentClassifyItem item) {
                    handleClassifyItem(item);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorClassifyColumn.ordinal()) {
            resourceId = R.layout.wghome_content_classify_column;
            WGHomeContentFloorClassifyColumnView temp = (WGHomeContentFloorClassifyColumnView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGCommonHorizontalListView.OnItemListener() {
                @Override
                public void onItemClick(Object object) {
                    handleClassifyItem((WGHomeFloorContentClassifyItem)object);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorCountryColumn.ordinal()) {
            resourceId = R.layout.wghome_content_country_column;
            WGHomeContentFloorCountryColumnView temp = (WGHomeContentFloorCountryColumnView)LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            temp.setListener(new WGCommonHorizontalListView.OnItemListener() {
                @Override
                public void onItemClick(Object object) {
                    handleFloorCountryItem((WGHomeFloorBaseContentItem)object);
                }
            });
            view = temp;
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorSeparateLine.ordinal()) {
            resourceId = R.layout.wghome_content_separateline;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        HomeViewHolder holder = new HomeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        HomeCellData item = mPostionValueMap.get(position);
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
            HomeCellData data = mPostionValueMap.get(position);
            return data.mType.ordinal();
        }
        return Item_Type.ITEM_TYPE_None.ordinal();
    }

    void handleCarouselFigures(int index) {
        if (mListener != null) {
            mListener.onCarouselFigures(index);
        }
    }

    void handleTopicItem(WGTopicItem item) {
        if (mListener != null) {
            mListener.onTopicItem(item);
        }
    }

    void handleFloorHead(WGHomeFloorItem item) {
        if (mListener != null) {
            mListener.onFloorHead(item);
        }
    }

    void handleFloorCountryItem(WGHomeFloorBaseContentItem item) {
        if (mListener != null) {
            mListener.onFloorCountryItem(item);
        }
    }

    void handlePurchase(WGHomeFloorContentGoodItem item, View view, Point fromPoint) {
        if (mListener != null) {
            mListener.onPurchase(item, view, fromPoint);
        }
    }

    void handleGoodItem(WGHomeFloorContentGoodItem item) {
        if (mListener != null) {
            mListener.onGoodItem(item);
        }
    }

    void handleClassifyItem(WGHomeFloorBaseContentItem item) {
        if (mListener != null) {
            mListener.onClassifyItem(item);
        }
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
