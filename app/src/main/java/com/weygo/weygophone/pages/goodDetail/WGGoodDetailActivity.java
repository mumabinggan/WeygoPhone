package com.weygo.weygophone.pages.goodDetail;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.IndicatorViewPager;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.collection.model.request.WGCancelCollectionGoodRequest;
import com.weygo.weygophone.pages.collection.model.response.WGCancelCollectionGoodResponse;
import com.weygo.weygophone.pages.common.widget.WGSegmentView;
import com.weygo.weygophone.pages.goodDetail.adapter.WGGoodDetailAdapter;
import com.weygo.weygophone.pages.goodDetail.model.WGCarouselFigureItem;
import com.weygo.weygophone.pages.goodDetail.model.WGGoodDetail;
import com.weygo.weygophone.pages.goodDetail.model.request.WGCollectGoodRequest;
import com.weygo.weygophone.pages.goodDetail.model.request.WGGoodDetailRequest;
import com.weygo.weygophone.pages.goodDetail.model.response.WGCollectGoodResponse;
import com.weygo.weygophone.pages.goodDetail.model.response.WGGoodDetailResponse;
import com.weygo.weygophone.pages.goodDetail.widget.WGGoodDetailOperateView;
import com.weygo.weygophone.pages.order.detail.adapter.WGOrderDetailAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.tabs.home.fragment.WGTabHomeFragment;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeTitleItem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/5/30.
 */

public class WGGoodDetailActivity extends WGTitleActivity {

    long mGoodId;

    RecyclerView mRecyclerView;

    WGGoodDetailAdapter mAdapter;

    WGGoodDetail mData;

    WGGoodDetailOperateView mOperateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //loadGoodDetail();
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
            mGoodId = (int)bundle.getSerializable(WGConstants.WGIntentDataKey);
        }

        mData = new WGGoodDetail();
        WGCarouselFigureItem carouseItem = new WGCarouselFigureItem();
        carouseItem.pictureURL = "https://www.weygo.com/media/catalog/product/1/2/120073.jpg";
        List carouseList = new ArrayList();
        carouseList.add(carouseItem);
        mData.carouselFigures = carouseList;

        mData.name = "郑淡和";
        mData.currentPrice = "2323.23";
        mData.price = "23";
        mData.specification = "我们的名字";
        mData.expiredTime = "1922.121";

        WGGoodDetail.WGGoodDetailDesItem desItem = new WGGoodDetail.WGGoodDetailDesItem();
        desItem.name = "改名";
        desItem.value = "郑洒";
        WGGoodDetail.WGGoodDetailDesItem desItem1 = new WGGoodDetail.WGGoodDetailDesItem();
        desItem1.name = "改ss名";
        desItem1.value = "郑ss洒";
        List desList = new ArrayList();
        desList.add(desItem);
        desList.add(desItem1);
        desList.add(desItem);
        mData.productDes = desList;
        mData.deliveryInfo = "Weygo.com è il tuo supermercato online con consegna a domicilio. \n" +
                "Con la tua APP sempre a portata di mano potrai fare la spesa in ogni momento! \n" +
                "Sul tram, nella pausa pranzo, mentre aspetti in coda. \n" +
                "Mai più tempo perso, ma solo comodità e vantaggi. \n" +
                "Weygo.com consegna a Milano tutti i giorni, domenica e festivi inclusi, dalle 8 alle 22. \n" +
                "Puoi ricevere la tua spesa a partire da 2 ore dopo l";
        mData.purchaseTip = "间交流通道,iOS,Android,Web客户端均可接入ios 推送,保持TCP长连接,支持海量并发,同时提供Rest API和后台管理系统,简单的SDK集成接入,让APP跨应用畅聊";
        mData.productInfo = "间交流通道,iOS,Android,Web客户端均可接入ios";



        List list = new ArrayList();
        WGOrderGoodItem goodItem = new WGOrderGoodItem();
        goodItem.name = "郑枯塔顶地";
        goodItem.goodCount = 12;
        goodItem.price = "2332.sf";
        goodItem.currentPrice = "23233";
        goodItem.pictureURL = "https://www.weygo.com/media/catalog/product/1/2/120073.jpg";
        list.add(goodItem);
        list.add(goodItem);
        list.add(goodItem);
        list.add(goodItem);
        list.add(goodItem);
        list.add(goodItem);
        mData.recommendProduce = list;

    }

    @Override
    public void initSubView() {
        super.initSubView();
        mNavigationBar.setTitle(R.string.OrderDetail_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGGoodDetailAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);

        mOperateView = (WGGoodDetailOperateView) findViewById(R.id.operateView);
        mOperateView.setListener(new WGGoodDetailOperateView.OnGoodOperateListener() {
            @Override
            public void onAddShopCart() {
                //加购物车
            }

            @Override
            public void onCollection() {
                //加收藏
            }
        });
    }

    @Override
    public void handleRightBarItem() {
        super.handleRightBarItem();
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
            mAdapter.setData(mData);
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
            mOperateView.showWithData(mData);
        }
        else {
            showWarning(response.message);
        }
    }
}