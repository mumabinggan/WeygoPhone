package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.jude.rollviewpager.HintView;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;

import java.util.List;

/**
 * Created by muma on 2017/6/2.
 */

public class WGRotationImagesView extends RollPagerView {

    List<String> mList;

    public WGRotationImagesView(Context context) {
        super(context);
    }

    public WGRotationImagesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGRotationImagesView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setData(List list) {
        mList = list;
        RotationImagesViewAdapter adapter = new RotationImagesViewAdapter();
        adapter.setData(list);
        setAdapter(adapter);
    }

    public void setHintUnSelectColor(int color) {
        setHintView(new ColorPointHintView(getContext(),
                getResources().getColor(R.color.WGAppBaseColor),
                color));
    }

    static class RotationImagesViewAdapter extends StaticPagerAdapter {

        List mList;

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            JHImageUtils.getInstance().loadImage((String) mList.get(position),
                    R.drawable.common_image_loading_carousel, view);
            return view;
        }

        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            }
            return 0;
        }

        public void setData(List list) {
            mList = list;
            notifyDataSetChanged();
        }
    }

//    private class TestNomalAdapter extends StaticPagerAdapter {
//
//        @Override
//        public View getView(ViewGroup container, int position) {
//            ImageView view = new ImageView(container.getContext());
//            view.setImageResource(imgs[position]);
//            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
//            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//            return view;
//        }
//
//        @Override
//        public int getCount() {
//            return imgs.length;
//        }
//    }
}
