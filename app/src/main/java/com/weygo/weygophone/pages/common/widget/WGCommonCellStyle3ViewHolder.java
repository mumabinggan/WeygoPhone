package com.weygo.weygophone.pages.common.widget;

import android.view.View;
import android.widget.TextView;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/15.
 */

public class WGCommonCellStyle3ViewHolder extends JHBaseViewHolder {

    public TextView textView;

    public WGCommonCellStyle3ViewHolder(View view) {
        super(view);
        textView = (TextView) view.findViewById(R.id.textView);
    }
}
