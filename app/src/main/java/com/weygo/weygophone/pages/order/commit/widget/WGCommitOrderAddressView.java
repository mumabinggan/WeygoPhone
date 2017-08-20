package com.weygo.weygophone.pages.order.commit.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

/**
 * Created by muma on 2017/8/18.
 */

public class WGCommitOrderAddressView extends JHRelativeLayout {

    TextView mNameTV;

    public WGCommitOrderAddressView(Context context) {
        super(context);
    }

    public WGCommitOrderAddressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCommitOrderAddressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameTV = (TextView) findViewById(R.id.nameTextView);
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof WGAddress) {
            WGAddress item = (WGAddress) object;
            mNameTV.setText(item.fullName + "  " +
                    item.address + "   " +
                    item.cap + "   " +
                    item.phone);
        }
    }
}
