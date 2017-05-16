package com.weygo.weygophone.pages.common.widget;

import android.view.View;
import android.widget.TextView;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;

/**
 * Created by muma on 2017/5/15.
 */

public class WGCommonCellStyle2ViewHolder extends JHBaseViewHolder {
    public TextView textView;

    public WGCommonCellStyle2ViewHolder(View view) {
        super(view);
        textView = (TextView) view.findViewById(R.id.textView);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGClassifyItem) {
            WGClassifyItem item = (WGClassifyItem) object;
            textView.setText(item.name);
        }
    }
}
