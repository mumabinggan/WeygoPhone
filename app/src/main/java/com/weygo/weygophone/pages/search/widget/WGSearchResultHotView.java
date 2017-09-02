package com.weygo.weygophone.pages.search.widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGCellStyle6View;
import com.weygo.weygophone.common.widget.WGCellStyle7View;
import com.weygo.weygophone.pages.search.WGSearchActivity;
import com.weygo.weygophone.pages.search.model.WGSearchData;
import com.weygo.weygophone.pages.search.model.WGSearchKeywordItem;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/8/23.
 */

public class WGSearchResultHotView extends JHLinearLayout {

    Context mContext;

    WGCellStyle6View mHotTitleView;

    TagFlowLayout mHotFlowLayout;

    WGSearchData mData;

    public interface OnItemListener {
        void onHotItem(WGSearchKeywordItem item);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGSearchResultHotView(Context context) {
        super(context);
        mContext = context;
    }

    public WGSearchResultHotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public WGSearchResultHotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mHotTitleView = (WGCellStyle6View) findViewById(R.id.hotTitleView);
        mHotFlowLayout = (TagFlowLayout) findViewById(R.id.hotFlowLayout);
        mHotFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                handleTagClick(view, position);
                return false;
            }
        });
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        mData = (WGSearchData)object;
        String title = String.format(JHResourceUtils.getInstance().getString(R.string.Search_Keyword), mData.name);
        mHotTitleView.setTitle(title);
        List<String> hotList = new ArrayList<>();
        if (mData != null) {
            if (mData.keywords != null) {
                for (int num = 0; num < mData.keywords.size(); ++ num) {
                    WGSearchKeywordItem item = mData.keywords.get(num);
                    hotList.add(item.name);
                }
            }
        }
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        TagAdapter<String> hotTagAdapter = new TagAdapter<String>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.classifydetailfilter_item_tv,
                        mHotFlowLayout, false);
                tv.setText(s);
                return tv;
            }
        };
        mHotFlowLayout.setAdapter(hotTagAdapter);
    }

    void handleTagClick(View view, int postion) {
        if (mData != null && mData.keywords != null) {
            WGSearchKeywordItem item = mData.keywords.get(postion);
            if (mListener != null) {
                mListener.onHotItem(item);
            }
        }
    }
}
