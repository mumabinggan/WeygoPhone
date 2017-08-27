package com.weygo.weygophone.pages.tabs.mine.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.address.list.WGAddressListActivity;
import com.weygo.weygophone.pages.address.list.model.WGAddressListData;
import com.weygo.weygophone.pages.order.commit.WGCommitOrderActivity;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by muma on 2017/5/16.
 */

public class WGTabMineFragment extends WGFragment {

    ImageView mSliderImageView;

    CircleImageView mHeadImageView;

    TextView mNameTextView;

    TextView mPostCodeTextView;

    TextView mOrderTextView;

    TextView mDeliverTextView;

    ViewGroup mIntegralView;

    ViewGroup mCouponView;

    ViewGroup mFootPrintView;

    ViewGroup mFavoriteView;

    ViewGroup mAddressView;

    ViewGroup mServiceView;

    ViewGroup mPersonView;

    ViewGroup mSettingView;

    ViewGroup mExitView;

    @Override
    public int fragmentResId() {
        return R.layout.tab_mine_fragment;
    }

    @Override
    public void initSubView(View view) {

        //Slider
        final WGMainActivity activity = (WGMainActivity) getActivity();
        mSliderImageView = (ImageView) view.findViewById(R.id.sliderImageView);
        mSliderImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打开 Slider
                activity.openSlider();
            }
        });

        //HeadImageView
        mHeadImageView = (CircleImageView) view.findViewById(R.id.headImageView);
        mHeadImageView.setImageResource(WGApplicationUserUtils.getInstance().userAvatar());

        //Name
        mNameTextView = (TextView) view.findViewById(R.id.nameTextView);
        mNameTextView.setText(WGApplicationUserUtils.getInstance().fullName());

        //Cap
        mPostCodeTextView = (TextView) view.findViewById(R.id.postCodeTextView);
        mPostCodeTextView.setText(WGApplicationUserUtils.getInstance().currentPostCode());

        //All Order
        mOrderTextView = (TextView) view.findViewById(R.id.allOrderCountTextView);
        mOrderTextView.setText(WGApplicationUserUtils.getInstance().orderCount() + "");

        //Deliver Order
        mDeliverTextView = (TextView) view.findViewById(R.id.deliverOrderCountTextView);
        mDeliverTextView.setText(WGApplicationUserUtils.getInstance().deliverOrderCount() + "");

        //mIntegralView
        mIntegralView = (ViewGroup) view.findViewById(R.id.scoreLayout);

        //mCouponView
        mCouponView = (ViewGroup) view.findViewById(R.id.couponLayout);

        //mFootPrintView
        mFootPrintView = (ViewGroup) view.findViewById(R.id.footprintLayout);

        //mFavoriteView
        mFavoriteView = (ViewGroup) view.findViewById(R.id.couponLayout);

        //mAddressView
        mAddressView = (ViewGroup) view.findViewById(R.id.addressLayout);
        mAddressView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddress();
            }
        });

        //mServiceView
        mServiceView = (ViewGroup) view.findViewById(R.id.serviceLayout);

        //mServiceView
        mPersonView = (ViewGroup) view.findViewById(R.id.userInfoLayout);

        //mServiceView
        mSettingView = (ViewGroup) view.findViewById(R.id.setLayout);

        //mServiceView
        mExitView = (ViewGroup) view.findViewById(R.id.loginLayout);
    }

    void handleAddress() {
        Intent intent = new Intent(getActivity(), WGAddressListActivity.class);
        Bundle bundle = new Bundle();
        WGAddressListData item = new WGAddressListData();
        item.canUse = false;
        bundle.putSerializable(WGConstants.WGIntentDataKey, item);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }
}
