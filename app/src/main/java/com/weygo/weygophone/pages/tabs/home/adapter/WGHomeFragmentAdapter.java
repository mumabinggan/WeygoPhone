package com.weygo.weygophone.pages.tabs.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.tabs.classify.adapter.WGClassifyAdapter;
import com.weygo.weygophone.pages.tabs.classify.adapter.WGSubClassifyAdapter;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHome;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorBaseContentItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by muma on 2017/6/5.
 */

public class WGHomeFragmentAdapter extends JHRecyclerViewAdapter {

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
        if (mHome == null) {
            mPostionValueMap.clear();
            notifyDataSetChanged();
            return;
        }

        int key = 0;
        if (home.carouselFigures != null && home.carouselFigures.size() > 0) {
            HomeCellData value = new HomeCellData(Item_Type.ITEM_TYPE_HomeCarouselFigures, home.carouselFigures);
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
                        item.type == WGConstants.WGHomeFloorItemTypeGoodGrid ||
                        item.type == WGConstants.WGHomeFloorItemTypeClassifyColumn ||
                        item.type == WGConstants.WGHomeFloorItemTypeClassifyGrid ||
                        item.type == WGConstants.WGHomeFloorItemTypeCountryColumn) {
                    if (item.content != null) {
                        List list = new ArrayList();
                        for (WGHomeFloorContentItem contentItem : item.content) {
                            WGHomeFloorBaseContentItem goodItem = contentItem
                                    .contentItemWithType(item.type);
                            list.add(goodItem);
                        }
                        HomeCellData value = null;
                        if (item.type == WGConstants.WGHomeFloorItemTypeGoodColumn) {
                            value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorGoodColumn, list);
                        }
                        else if (item.type == WGConstants.WGHomeFloorItemTypeGoodGrid) {
                            value = new HomeCellData(Item_Type.ITEM_TYPE_HomeFloorGoodGrid, list);
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
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeTopics.ordinal()) {
            resourceId = R.layout.common_horizontal_list;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
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
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorGoodListItem.ordinal()) {
            resourceId = R.layout.wgorderlist_good_item;
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
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorGoodColumn.ordinal()) {
            resourceId = R.layout.common_horizontal_list;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorGoodGrid.ordinal()) {
            resourceId = R.layout.wghome_content_floor_good_grid_cell;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorClassifyListCellItem.ordinal()) {
            resourceId = R.layout.wghome_content_floor_classify_list_item;
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
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorClassifyGrid.ordinal()) {
            resourceId = R.layout.wghome_content_floor_classify_grid;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorClassifyColumn.ordinal()) {
            resourceId = R.layout.common_horizontal_list;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_HomeFloorCountryColumn.ordinal()) {
            resourceId = R.layout.common_horizontal_list;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
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
