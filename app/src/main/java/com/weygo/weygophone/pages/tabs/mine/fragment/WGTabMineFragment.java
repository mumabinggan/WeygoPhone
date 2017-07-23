package com.weygo.weygophone.pages.tabs.mine.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.WGMainActivity;
import com.weygo.weygophone.base.WGFragment;
import com.weygo.weygophone.common.WGApplicationUserUtils;

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
                activity.testActivity();
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
    }
}
