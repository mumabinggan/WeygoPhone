package com.weygo.weygophone.pages.search.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHActivity;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.common.WGApplicationGlobalUtils;
import com.weygo.weygophone.common.WGConstants;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by muma on 2017/8/23.
 */

public class WGShopCartView extends JHRelativeLayout {

    TextView mCountTV;

    Context mContext;

    JHBroadcastReceiver mShopCartReceiver;

    class JHBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive (Context context, Intent intent) {
            handelShopCart();
        }
    }

    public WGShopCartView(Context context) {
        super(context);
        mContext = context;
    }

    public WGShopCartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public WGShopCartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCountTV = (TextView) findViewById(R.id.countTV);
        showShopCartCountStatus();
        initShopcartReceiver();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mShopCartReceiver != null) {
            mContext.unregisterReceiver(mShopCartReceiver);
        }
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
    }

    void initShopcartReceiver() {
        if (mShopCartReceiver == null) {
            mShopCartReceiver = new JHBroadcastReceiver();
            IntentFilter filter = new IntentFilter();
            filter.addAction(WGConstants.WGSpecialNotificationTypeShopCartCountAction);
            mContext.registerReceiver(mShopCartReceiver, filter);
        }
    }

    public void handelShopCart() {
        mCountTV.setText(WGApplicationGlobalUtils.getInstance().mShopCartGoodCount + "");
        showShopCartCountStatus();
    }

    void showShopCartCountStatus() {
        long count = WGApplicationGlobalUtils.getInstance().mShopCartGoodCount;
        if (count == 0) {
            mCountTV.setVisibility(INVISIBLE);
        }
        else {
            mCountTV.setVisibility(VISIBLE);
        }
    }
}
