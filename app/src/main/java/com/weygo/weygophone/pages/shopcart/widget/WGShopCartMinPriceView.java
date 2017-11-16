package com.weygo.weygophone.pages.shopcart.widget;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGInterface;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.common.widget.WGAddGoodView;
import com.weygo.weygophone.pages.shopcart.model.WGShopCartGoodItem;
import com.weygo.weygophone.pages.tabs.home.model.WGHomeFloorContentGoodItem;

/**
 * Created by muma on 2017/8/18.
 */

public class WGShopCartMinPriceView extends JHLinearLayout {

    TextView mPriceTextView;

    public WGShopCartMinPriceView(Context context) {
        super(context);
    }

    public WGShopCartMinPriceView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGShopCartMinPriceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPriceTextView = (TextView) findViewById(R.id.textView);
    }

    @Override
    public void showWithData(Object object) {
        if (object instanceof String) {
            mPriceTextView.setText((String)object);
        }
    }
}
