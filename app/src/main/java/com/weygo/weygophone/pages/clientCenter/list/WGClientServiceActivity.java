package com.weygo.weygophone.pages.clientCenter.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.base.JHResponse;
import com.weygo.common.tools.network.JHRequestError;
import com.weygo.common.tools.network.JHResponseCallBack;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGTitleActivity;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.pages.clientCenter.detail.WGClientServiceDetailActivity;
import com.weygo.weygophone.pages.clientCenter.list.adapter.WGClientServiceAdapter;
import com.weygo.weygophone.pages.clientCenter.list.model.WGClientServiceItem;
import com.weygo.weygophone.pages.clientCenter.list.model.request.WGClientServiceRequest;
import com.weygo.weygophone.pages.clientCenter.list.model.response.WGClientServiceResponse;
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.util.List;

/**
 * Created by muma on 2017/5/17.
 */

public class WGClientServiceActivity extends WGTitleActivity {

    List mArray;
    RecyclerView mRecyclerView;
    WGClientServiceAdapter mAdapter;

    Button mChatBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        loadClientServiceList();
    }

    @Override
    public void initContentView() {
        setContentView(R.layout.wgclientservice_list_activity);
    }

    @Override
    public void initSubView() {
        super.initSubView();

        mNavigationBar.setTitle(R.string.ClientService_Title);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WGClientServiceAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new JHRecyclerViewAdapter.OnBaseItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                handleItemClick(view, position);
            }
        });
        mChatBtn = (Button) findViewById(R.id.chatBtn);
        mChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChat();
            }
        });
    }

    void handleChat() {
        startActivity(new Intent(WGClientServiceActivity.this, ZopimChatActivity.class));
    }

    void handleItemClick(View view, int position) {
        WGClientServiceItem item = (WGClientServiceItem) mArray.get(position);
        Intent intent = new Intent(WGClientServiceActivity.this, WGClientServiceDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("key", item);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void loadClientServiceList() {
        WGClientServiceRequest request = new WGClientServiceRequest();
        this.postAsyn(request, WGClientServiceResponse.class, new JHResponseCallBack() {
            @Override
            public void onSuccess(JHResponse response) {
                handleLoginSuccessResponse((WGClientServiceResponse )response);
            }

            @Override
            public void onFailure(JHRequestError error) {
                showWarning(R.string.Request_Fail_Tip);
            }
        });
    }

    void handleLoginSuccessResponse(WGClientServiceResponse response) {
        if (response.success()) {
            mArray = response.data;
            mAdapter.setData(mArray);
        }
        else {
            showWarning(response.message);
        }
    }
}
