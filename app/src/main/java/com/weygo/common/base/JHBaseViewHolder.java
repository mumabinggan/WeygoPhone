package com.weygo.common.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

/**
 * Created by muma on 2017/5/10.
 */

public class JHBaseViewHolder extends RecyclerView.ViewHolder{

    Context mContext;

    public JHBaseViewHolder(View view) {
        super(view);
    }

    public JHBaseViewHolder(Context context, View view) {
        super(view);
        mContext = context;
    }

    public void showWithData(Object object) {

    }
    public void showWithArray(List list) {

    }
}
