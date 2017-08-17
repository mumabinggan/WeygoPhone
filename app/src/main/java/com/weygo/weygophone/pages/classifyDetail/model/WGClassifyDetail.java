package com.weygo.weygophone.pages.classifyDetail.model;

import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGObject;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by muma on 2017/8/2.
 */

public class WGClassifyDetail extends WGObject {

    public long id;

    public long snappedUpExpiredTime;

    public String name;

    public WGCarouselFigureItem carouselFigureItem;

    public List<WGClassifyItem> subClassifyArray;

    public List<WGHomeFloorContentGoodItem> goodArray;

    // 促销
    public List<WGHomeFloorContentGoodItem> recommendedArray;

    //消息
    //@property (nonatomic, strong) WGNews *news;

    //for self use
    public boolean hasCarousel() {
        return carouselFigureItem != null;
    }

    public boolean hasRecommendedArray() {
        return (recommendedArray != null && recommendedArray.size() > 0);
    }

    public boolean hasSubClassify() {
        return (subClassifyArray != null && subClassifyArray.size() > 0);
    }

    public boolean hasGoods() {
        return (goodArray != null && goodArray.size() > 0);
    }

    //for self use
    public List<Integer> mSortTitles = Arrays.asList(R.string.ClassifyDetail_Sort,
            R.string.ClassifyDetail_Brand, R.string.ClassifyDetail_Price_Up,
            R.string.ClassifyDetail_Price_Down);
    //排序
    int mSortType = WGConstants.WGClassifySortTypeDefault;
    public int sortType() {
        return mSortType;
    }
    public void setSortType(int sortType) {
        mSortType = sortType;
    }

    public int selectedSortTitleResId() {
        return mSortTitles.get(mSortType);
    }

    //筛选
    WGClassifyFilterCondition mFilterCondition;
    public WGClassifyFilterCondition filterCondition() {
        return mFilterCondition;
    }
    public void setFilterCondition(WGClassifyFilterCondition filterCondition) {
        mFilterCondition = filterCondition;
    }

    //Vista
    int mGrid = -1;
    public void setGrid(boolean isGrid) {
        mGrid = isGrid ? 1 : 0;
    }
    public boolean isGrid() {
        if (hasRecommendedArray() && mGrid == -1) {
            mGrid = 1;
        }
        return (mGrid == -1 || mGrid == 0) ? false : true;
    }
}
