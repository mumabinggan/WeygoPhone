package com.weygo.weygophone.pages.goodDetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.JHAdaptScreenUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.common.widget.JHBasePopupWindow;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationAnimationUtils;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGPostPopView;
import com.weygo.weygophone.pages.collection.model.request.WGCancelCollectionGoodRequest;
import com.weygo.weygophone.pages.collection.model.response.WGCancelCollectionGoodResponse;
import com.weygo.weygophone.pages.goodDetail.adapter.WGGoodDetailAdapter;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;
import com.weygo.weygophone.pages.goodDetail.model.request.WGCollectGoodRequest;
import com.weygo.weygophone.pages.goodDetail.model.request.WGGoodDetailRecommendRequest;
import com.weygo.weygophone.pages.goodDetail.model.request.WGGoodDetailRequest;
import com.weygo.weygophone.pages.goodDetail.model.response.WGAddGoodToCartResponse;
import com.weygo.weygophone.pages.goodDetail.model.response.WGCollectGoodResponse;
import com.weygo.weygophone.pages.goodDetail.model.response.WGGoodDetailRecommendResponse;
import com.weygo.weygophone.pages.goodDetail.model.response.WGGoodDetailResponse;
import com.weygo.weygophone.pages.goodDetail.widget.WGGoodDetailOperateView;
import com.weygo.weygophone.pages.order.list.WGOrderListActivity;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.widget.WGShopCartNavigationView;
import com.weygo.weygophone.pages.shopcart.WGShopCartListActivity;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/5/30.
 */

public class WGGoodDetailActivity extends WGBaseActivity {

    long mGoodId;

    LinearLayout mLayout;
    WGShopCartNavigationView mNavigationBar;

    RecyclerView mRecyclerView;

    WGGoodDetailAdapter mAdapter;

    WGGoodDetail mData;

    WGGoodDetailOperateView mOperateView;

    WGGoodDetailRecommendResponse mRecommendResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadGoodDetail();
        loadRecommendGoods();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wggooddetail_activity);
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mGoodId = (long)bundle.getSerializable(WGConstants.WGIntentDataKey);
        }
    }

    @Override
    public void initSubView() {
        super.initSubView();
        mLayout = (LinearLayout) findViewById(R.id.mLayout);
        mNavigationBar = (WGShopCartNavigationView) findViewById(R.id.titlebar);
        mNavigationBar.setTitle(R.string.OrderDetail_Title);
        mNavigationBar.setListener(new WGShopCartNavigationView.OnItemListener() {
            @Override
            public void onLeft() {
                finish();
            }

            @Override
            public void onRight() {
                Intent intent = new Intent(WGGoodDetailActivity.this, WGShopCartListActivity.class);
                startActivity(intent);
            }
        });
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGGoodDetailAdapter(this, mData);
        mAdapter.setListener(new WGGoodDetailAdapter.GoodDetailItemClickListener() {
            @Override
            public void onGoodItemClick(View view, WGHomeFloorContentGoodItem item) {
                Intent intent = new Intent(WGGoodDetailActivity.this, WGGoodDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(WGConstants.WGIntentDataKey, item.id);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

        mOperateView = (WGGoodDetailOperateView) findViewById(R.id.operateView);
        mOperateView.setListener(new WGGoodDetailOperateView.OnGoodOperateListener() {
            @Override
            public void onAddShopCart() {
                //加购物车
                handleAddToShopCart();
            }

            @Override
            public void onCollection() {
                //加收藏
                if (mData != null) {
                    loadCollectGood((mData.hasFavorited == 0) ? true : false);
                }
            }
        });
    }

    void handleAddToShopCart() {
        if (JHStringUtils.isNullOrEmpty(WGApplicationUserUtils.getInstance().currentPostCode())) {
            WGPostPopView popupView = (WGPostPopView) getLayoutInflater().inflate(R.layout.common_cap_pop, null);
            popupView.setListener(new WGPostPopView.OnItemListener() {
                @Override
                public void onSuccess() {

                }
            });
            JHBasePopupWindow window = new JHBasePopupWindow(popupView,
                    JHAdaptScreenUtils.devicePixelWidth(this),
                    JHAdaptScreenUtils.devicePixelHeight(this));
            popupView.setPopupWindow(window);
            window.showAtLocation(popupView, Gravity.CENTER, 0, 0);
            return;
        }
        WGApplicationRequestUtils.getInstance().loadAddGoodToCart(mGoodId, mOperateView.getGoodCount(), new WGApplicationRequestUtils.WGOnCompletionInteface() {
            @Override
            public void onSuccessCompletion(WGResponse response) {
                handleShopCartCount((WGAddGoodToCartResponse) response);
            }

            @Override
            public void onFailCompletion(WGResponse response) {

            }
        });
        if (mData != null && mData.carouselFigures != null && mData.carouselFigures.size() > 0) {
            WGCarouselFigureItem item = mData.carouselFigures.get(0);
            //动画
            int[] distance = {0,0};
            WGApplicationAnimationUtils.add(this, mLayout, mOperateView,
                    item.pictureURL, R.drawable.common_add_cart, mNavigationBar.getShopCartView(), distance);
        }
    }

    void handleShopCartCount(WGAddGoodToCartResponse response) {
        if (response.success()) {
            WGApplicationGlobalUtils.getInstance().handleShopCartGoodCount(response.data.goodCount);
        }
        else {
            showWarning(response.message);
        }
    }

    void loadGoodDetail() {
        WGGoodDetailRequest request = new WGGoodDetailRequest();
        request.id = mGoodId;
        this.postAsyn(request, WGGoodDetailResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleGoodDetailSuccessResponse((WGGoodDetailResponse )response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleGoodDetailSuccessResponse(WGGoodDetailResponse response) {
        if (response.success()) {
            mData = response.data;
            if (mRecommendResponse != null) {
                mData.recommendProduce = mRecommendResponse.data;
            }
            mAdapter.setData(mData);
            mOperateView.showWithData(mData);
        }
        else {
            showWarning(response.message);
        }
    }

    void loadCollectGood(boolean collection) {
        if (collection) {
            WGCollectGoodRequest request = new WGCollectGoodRequest();
            request.productId = mGoodId;
            this.postAsyn(request, WGCollectGoodResponse.class, new JHResponseCallBack() {
                @Override
                public void onSuccess(JHResponse response) {
                    handleCollectGoodSuccessResponse((WGCollectGoodResponse )response);
                }

                @Override
                public void onFailure(JHRequestError error) {
                    showWarning(R.string.Request_Fail_Tip);
                }
            });
        }
        else {
            WGCancelCollectionGoodRequest request = new WGCancelCollectionGoodRequest();
            request.id = mData.favoritedId;
            this.postAsyn(request, WGCancelCollectionGoodResponse.class, new JHResponseCallBack() {
                @Override
                public void onSuccess(JHResponse response) {
                    handleCancelCollectGoodSuccessResponse((WGCancelCollectionGoodResponse )response);
                }

                @Override
                public void onFailure(JHRequestError error) {
                    showWarning(R.string.Request_Fail_Tip);
                }
            });
        }

    }

    void handleCollectGoodSuccessResponse(WGCollectGoodResponse response) {
        if (response.success()) {
            mData.hasFavorited = response.data.favoriteId;
            mData.favoritedId = response.data.favoriteId;
            mOperateView.showWithData(mData);
        }
        else {
            showWarning(response.message);
        }
    }

    void handleCancelCollectGoodSuccessResponse(WGCancelCollectionGoodResponse response) {
        if (response.success()) {
            mData.favoritedId = 0;
            mData.hasFavorited = 0;
            mOperateView.showWithData(mData);
        }
        else {
            showWarning(response.message);
        }
    }

    void loadRecommendGoods() {
        WGGoodDetailRecommendRequest request = new WGGoodDetailRecommendRequest();
        request.goodId = mGoodId;
        request.showsLoadingView = false;
        this.postAsyn(request, WGGoodDetailRecommendResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleGoodDetailRecommendSuccessResponse((WGGoodDetailRecommendResponse )response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleGoodDetailRecommendSuccessResponse(WGGoodDetailRecommendResponse response) {
        if (response.success()) {
            if (mData != null) {
                mData.recommendProduce = response.data;
                mAdapter.setData(mData);
            }
            else {
                mRecommendResponse = response;
            }
        }
        else {
            showWarning(response.message);
        }
    }
}
