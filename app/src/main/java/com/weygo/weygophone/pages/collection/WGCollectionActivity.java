package com.weygo.weygophone.pages.collection;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.weygo.common.base.JHDividerItemDecoration;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.widget.JHNavigationBar;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.pages.collection.adapter.WGGoodListAdapter;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/5/19.
 */

public class WGCollectionActivity extends WGGoodListActivity {

    PullRefreshLayout mRefreshLayout;
    RecyclerView mRecyclerView;
    WGGoodListAdapter mAdapter;

    protected List mList;

    @Override
    public void initContentView() {
        setContentView(R.layout.wgcollection_activity);
    }

    @Override
    public void initData() {
        super.initData();
        mList = new ArrayList();
        WGHomeFloorContentGoodItem item = new WGHomeFloorContentGoodItem();
        item.pictureURL = "";
        item.name = "郑少要";
        item.chineseName = "sss";
        item.briefDescription = "asdfasdfasdfas";
        item.price = "12d";
        item.currentPrice = "fss";
        item.reducePrice = "fs2323";
        mList.add(item);

        WGHomeFloorContentGoodItem item2 = new WGHomeFloorContentGoodItem();
        item2.pictureURL = "";
        item2.name = "郑少要";
        item2.chineseName = "sss";
        item2.briefDescription = "asdfasdfasdfas";
        item2.price = "12d";
        item2.currentPrice = "fss";
        item2.reducePrice = "fs2323";
        mList.add(item);
    }

    @Override
    public void initSubView() {
        super.initSubView();

        mNavigationBar.setTitle(R.string.Collection_Title);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.addItemDecoration(new JHDividerItemDecoration(this,
                JHDividerItemDecoration.VERTICAL_LIST));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGGoodListAdapter(this, mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new WGGoodListAdapter.WGGoodListOnItemClickListener() {
            @Override
            public void onTouchGoodItem(View view, WGHomeFloorContentGoodItem item) {
                sss();
            }
            @Override
            public void onTouchAddCart(View view, WGHomeFloorContentGoodItem item) {
                sss();
            }
            @Override
            public void onItemClick(View view, int position) {
                sss();
            }
        });

        mRefreshLayout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mRefreshLayout.setRefreshStyle(PullRefreshLayout.STYLE_RING);
        mRefreshLayout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.setRefreshing(false);
                    }
                }, 3000);
            }
        });
    }

    @Override
    public void handleRightBarItem() {
        super.handleRightBarItem();
    }

    void handleItem() {

    }

    void sss() {
        Log.e("dsasadfasdfasd", "----------------");
//        return;
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        AlertDialog alert = builder.setMessage("这是一个最普通的AlertDialog,\n带有三个按钮，分别是取消，中立和确定")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(WGCollectionActivity.this, "你点击了取消按钮~", Toast.LENGTH_SHORT).show();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(WGCollectionActivity.this, "你点击了确定按钮~", Toast.LENGTH_SHORT).show();
                    }
                })
                .create();             //创建AlertDialog对象
        alert.show();                    //显示对话框
    }

}
