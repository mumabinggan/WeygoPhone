package com.weygo.weygophone.pages.classifyDetail.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyDetail;
import com.weygo.weygophone.pages.classifyDetail.model.WGClassifyFilterCondition;

/**
 * Created by muma on 2017/8/2.
 */

public class WGClassifyDetailFilterView extends JHLinearLayout {

    public interface OnItemClickListener {
        void onSort(View view);

        void onFilter(View view);

        void onVista(View view);
    }

    OnItemClickListener mListener;
    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    WGClassifyDetailFilterItemView mSortView;
    WGClassifyDetailFilterItemView mFilterView;
    WGClassifyDetailFilterItemView mGridView;

    public WGClassifyDetailFilterView(Context context) {
        super(context);
    }

    public WGClassifyDetailFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGClassifyDetailFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSortView = (WGClassifyDetailFilterItemView) findViewById(R.id.sortView);
        mSortView.setImageView(R.drawable.classifydetail_sort);
        mSortView.setTextView(R.string.ClassifyDetail_Sort);
        mSortView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSort();
            }
        });

        mFilterView = (WGClassifyDetailFilterItemView) findViewById(R.id.filterView);
        mFilterView.setImageView(R.drawable.classifydetail_filter);
        mFilterView.setTextView(R.string.ClassifyDetail_Filter);
        mFilterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFilter();
            }
        });

        mGridView = (WGClassifyDetailFilterItemView) findViewById(R.id.gridView);
        mGridView.setImageView(R.drawable.classifydetail_vista);
        mGridView.setTextView(R.string.ClassifyDetail_Vista);
        mGridView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleVista();
            }
        });
    }

    void handleSort() {
        if (mListener != null) {
            mListener.onSort(this);
        }
    }

    void handleFilter() {
        if (mListener != null) {
            mListener.onFilter(this);
        }
    }

    void handleVista() {
        if (mListener != null) {
            mListener.onVista(this);
        }
    }

    public void showWithData(Object data) {
        if (data instanceof WGClassifyDetail) {
            mSortView.setTextView(((WGClassifyDetail) data).selectedSortTitleResId());
            WGClassifyFilterCondition condition = ((WGClassifyDetail) data).filterCondition();
            if (condition != null) {
                mFilterView.setViewSelected(condition.isSelected());
            }
            else {
                mFilterView.setViewSelected(false);
            }
            mGridView.setViewSelected(((WGClassifyDetail) data).isGrid());
        }
    }
}
