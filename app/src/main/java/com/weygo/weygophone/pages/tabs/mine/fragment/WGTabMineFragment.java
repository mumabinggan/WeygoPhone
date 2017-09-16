package com.weygo.weygophone.pages.tabs.mine.fragment;

import android.content.Intent;
import android.media.MediaDrmException;
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
import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.common.WGApplicationRequestUtils;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.common.WGConstants;
import com.weygo.weygophone.pages.address.list.WGAddressListActivity;
import com.weygo.weygophone.pages.address.list.model.WGAddressListData;
import com.weygo.weygophone.pages.clientCenter.list.WGClientServiceActivity;
import com.weygo.weygophone.pages.collection.WGCollectionActivity;
import com.weygo.weygophone.pages.coupon.WGCouponListActivity;
import com.weygo.weygophone.pages.coupon.model.WGCoupon;
import com.weygo.weygophone.pages.footprint.WGFootprintActivity;
import com.weygo.weygophone.pages.integral.myIntegral.WGMyIntegralActivity;
import com.weygo.weygophone.pages.order.commit.WGCommitOrderActivity;
import com.weygo.weygophone.pages.order.list.WGOrderListActivity;
import com.weygo.weygophone.pages.personInfo.WGPersonInfoActivity;
import com.weygo.weygophone.pages.setting.WGSettingActivity;

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
//        mHeadImageView.setImageResource(WGApplicationUserUtils.getInstance().userAvatar());

        //Name
        mNameTextView = (TextView) view.findViewById(R.id.nameTextView);
//        mNameTextView.setText(WGApplicationUserUtils.getInstance().fullName());

        //Cap
        mPostCodeTextView = (TextView) view.findViewById(R.id.postCodeTextView);
//        mPostCodeTextView.setText(WGApplicationUserUtils.getInstance().currentPostCode());

        //All Order
        mOrderTextView = (TextView) view.findViewById(R.id.allOrderCountTextView);
//        mOrderTextView.setText(WGApplicationUserUtils.getInstance().orderCount() + "");
        mOrderTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOrder(false);
            }
        });

        //Deliver Order
        mDeliverTextView = (TextView) view.findViewById(R.id.deliverOrderCountTextView);
//        mDeliverTextView.setText(WGApplicationUserUtils.getInstance().deliverOrderCount() + "");
        mDeliverTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleOrder(true);
            }
        });

        //mIntegralView
        mIntegralView = (ViewGroup) view.findViewById(R.id.scoreLayout);
        mIntegralView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleIntegral();
            }
        });

        //mCouponView
        mCouponView = (ViewGroup) view.findViewById(R.id.couponLayout);
        mCouponView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCoupon();
            }
        });

        //mFootPrintView
        mFootPrintView = (ViewGroup) view.findViewById(R.id.footprintLayout);
        mFootPrintView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFooterPrint();
            }
        });

        //mFavoriteView
        mFavoriteView = (ViewGroup) view.findViewById(R.id.collectionLayout);
        mFavoriteView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleFavorite();
            }
        });

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
        mServiceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleService();;
            }
        });

        //mServiceView
        mPersonView = (ViewGroup) view.findViewById(R.id.userInfoLayout);
        mPersonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlePerson();
            }
        });

        //mServiceView
        mSettingView = (ViewGroup) view.findViewById(R.id.setLayout);
        mSettingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSetting();
            }
        });

        //mServiceView
        mExitView = (ViewGroup) view.findViewById(R.id.logoutLayout);
        mExitView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mHeadImageView.setImageResource(WGApplicationUserUtils.getInstance().userAvatar());

        //Name
        mNameTextView.setText(WGApplicationUserUtils.getInstance().fullName());

        //Cap
        mPostCodeTextView.setText(WGApplicationUserUtils.getInstance().currentPostCode());

        mOrderTextView.setText(WGApplicationUserUtils.getInstance().orderCount() + "");

        //Deliver Order
        mDeliverTextView.setText(WGApplicationUserUtils.getInstance().deliverOrderCount() + "");
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

    void handleSetting() {
        Intent intent = new Intent(getActivity(), WGSettingActivity.class);
        startActivity(intent);
    }

    void handleFooterPrint() {
        Intent intent = new Intent(getActivity(), WGFootprintActivity.class);
        startActivity(intent);
    }

    void handleCoupon() {
        Intent intent = new Intent(getActivity(), WGCouponListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(WGConstants.WGIntentDataKey1, false);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    void handleFavorite() {
        Intent intent = new Intent(getActivity(), WGCollectionActivity.class);
        startActivity(intent);
    }

    void handleService() {
        Intent intent = new Intent(getActivity(), WGClientServiceActivity.class);
        startActivity(intent);
    }

    void handlePerson() {
        Intent intent = new Intent(getActivity(), WGPersonInfoActivity.class);
        startActivity(intent);
    }

    void handleIntegral() {
        Intent intent = new Intent(getActivity(), WGMyIntegralActivity.class);
        startActivity(intent);
    }

    void handleExit() {
//        WGApplicationRequestUtils.getInstance().loadLogoutRequest(new WGApplicationRequestUtils.WGOnCompletionInteface() {
//            @Override
//            public void onSuccessCompletion(WGResponse response) {
//                handleLogoutCompletion();
//            }
//
//            @Override
//            public void onFailCompletion(WGResponse response) {
//
//            }
//        });
    }

    void handleLogoutCompletion() {

    }

    void handleOrder(boolean deliver) {
        Intent intent = new Intent(getActivity(), WGOrderListActivity.class);
        Bundle bundle = new Bundle();
        int type = 1;
        if (deliver) {
            type = WGConstants.WGOrderListTypeDelivering;
        }
        else {
            type = WGConstants.WGOrderListTypeAll;
        }
        bundle.putSerializable(WGConstants.WGIntentDataKey, type);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
