package com.weygo.weygophone.pages.slider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.common.tools.JHStringUtils;
import com.weygo.common.tools.loadwebimage.JHImageUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.WGApplication;
import com.weygo.weygophone.common.WGApplicationUserUtils;
import com.weygo.weygophone.pages.slider.model.SliderOnItemClickListener;
import com.weygo.weygophone.pages.slider.model.WGHomeSlider;
import com.weygo.weygophone.pages.tabs.classify.model.WGClassifyItem;
import com.weygo.weygophone.pages.tabs.home.model.WGTopicItem;
import com.weygo.weygophone.pages.tabs.mine.model.WGUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by muma on 2017/5/7.
 */

public class WGSliderAdapter extends JHRecyclerViewAdapter
{

    Context mContext;

    WGHomeSlider mData;

    private int mCurrentSelectedIndex;

    public enum Item_Type {
        ITEM_TYPE_None,
        ITEM_TYPE_Head,
        ITEM_TYPE_RegisterHead,
        ITEM_TYPE_PostCode,
        ITEM_TYPE_Order,
        ITEM_TYPE_FootPrints,
        ITEM_TYPE_Coupon,
        ITEM_TYPE_Message,
        ITEM_TYPE_Topics,
        ITEM_TYPE_Classify,
        ITEM_TYPE_SubClassify,
    }

    public void handleClickView(View view) {
        if (mOnItemClickListener != null) {
            if (mOnItemClickListener instanceof SliderOnItemClickListener) {
                SliderOnItemClickListener sliderListerner = (SliderOnItemClickListener)mOnItemClickListener;
                int position = (int) view.getTag();
                Item_Type type = getItemViewTypeAfterOrdinal(position);
                if (type == Item_Type.ITEM_TYPE_PostCode) {
                    sliderListerner.onPostCodeClick(view);
                }
                else if (type == Item_Type.ITEM_TYPE_Order) {
                    sliderListerner.onOrderClick(view);
                }
                else if (type == Item_Type.ITEM_TYPE_FootPrints) {
                    sliderListerner.onFootPrintsClick(view);
                }
                else if (type == Item_Type.ITEM_TYPE_Coupon) {
                    sliderListerner.onCouponClick(view);
                }
                else if (type == Item_Type.ITEM_TYPE_Message) {
                    sliderListerner.onMessageCenterClick(view);
                }
                else if (type == Item_Type.ITEM_TYPE_Classify) {
                    setCurrentSelectedIndex((mCurrentSelectedIndex == classifyIndex(position)) ? -1 : classifyIndex(position));
                }
            }
        }
    }

    public WGSliderAdapter(Context context, WGHomeSlider data) {
        this.mData = data;
        this.mContext = context;
        mCurrentSelectedIndex = -1;
    }

    public void setData(WGHomeSlider data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void setCurrentSelectedIndex(int currentIndex) {
        mCurrentSelectedIndex = currentIndex;
        for (int num = 0; num < mData.classify.size(); ++num) {
            WGClassifyItem item = mData.classify.get(num);
            item.isSelected = (num == currentIndex);
        }
        notifyDataSetChanged();
    }

    boolean isLogin() {
        return WGApplicationUserUtils.getInstance().isLogined();
    }

    int maxClassifyTopCount() {
        int count = 0;
        if (isLogin()) {
            count = 6;
        }
        else {
            count = 3;
        }
        if (mData == null) {
            count -= count;
        }
        return count;
    }

    int classifyIndex(int position) {
        return (position - maxClassifyTopCount()) / 2;
    }

    int subClassifyIndex(int position) {
        return classifyIndex(position);
    }

    boolean isClassifyItem(int position) {
        return (position >= maxClassifyTopCount() && (position-maxClassifyTopCount()) % 2 == 1);
    }

    boolean isCommonItem(int type) {
        return (type == Item_Type.ITEM_TYPE_Order.ordinal() ||
        type == Item_Type.ITEM_TYPE_Coupon.ordinal() ||
        type == Item_Type.ITEM_TYPE_Message.ordinal() ||
        type == Item_Type.ITEM_TYPE_FootPrints.ordinal());
    }

    SliderOnItemClickListener sliderClickListener() {
        if (mOnItemClickListener == null) {
            return null;
        }
        return (SliderOnItemClickListener) mOnItemClickListener;
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int resourceId = 0;
        JHBaseViewHolder holder = null;
        View view = null;
        if (viewType == Item_Type.ITEM_TYPE_None.ordinal()) {
            resourceId = R.layout.common_empty;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            holder = new JHBaseViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_Head.ordinal()) {
            resourceId = R.layout.wgslider_personinfo;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            holder = new HeadViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_RegisterHead.ordinal()) {
            resourceId = R.layout.wgslider_registerpersoninfo;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            holder = new RegisterHeadViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_PostCode.ordinal()) {
            resourceId = R.layout.wgslider_postcode;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            holder = new PostCodeViewHolder(view);
        }
        else if (isCommonItem(viewType)) {
            resourceId = R.layout.common_cell_type2;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            holder = new CommonItemViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_Topics.ordinal()) {
            resourceId = R.layout.wgslider_topics;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            holder = new TopicsViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_Classify.ordinal()) {
            resourceId = R.layout.wgslider_classify;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            holder = new ClassifyViewHolder(view);
        }
        else if (viewType == Item_Type.ITEM_TYPE_SubClassify.ordinal()) {
            resourceId = R.layout.wgslider_subclassify;
            view = LayoutInflater.from(
                    mContext).inflate(resourceId, parent,
                    false);
            holder = new ClassifyItemViewHolder(mContext, view);
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleClickView(view);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (holder instanceof HeadViewHolder) {
            holder.showWithData(WGApplicationUserUtils.
                    getInstance(mContext).getUser());
        }
        else if (holder instanceof RegisterHeadViewHolder) {
            holder.showWithData(WGApplicationUserUtils.
                    getInstance(mContext).getUser());
        }
        else if (holder instanceof PostCodeViewHolder) {
            holder.showWithData(WGApplicationUserUtils.
                    getInstance(mContext).currentPostCode());
        }
        else if (holder instanceof CommonItemViewHolder) {
            List list = new ArrayList<Integer>(){{add(R.string.Slider_Mine_Order);
                add(R.string.Slider_Mine_FootPrint);add(R.string.Slider_Mine_Coupon);
                add(R.string.Slider_Mine_Message);
            }};
            int index = position - 2;
            Integer resId = (Integer) list.get(index);
            holder.showWithData(JHResourceUtils.getInstance().getString(resId));
        }
        else if (holder instanceof TopicsViewHolder) {
            holder.showWithArray(mData.topics);
        }
        else if (holder instanceof ClassifyViewHolder) {
            int index = classifyIndex(position);
            if (mData.classify.size() > index) {
                holder.showWithData(mData.classify.get(index));
            }
        }
        else if (holder instanceof ClassifyItemViewHolder) {
            int index = subClassifyIndex(position);
            if (index == mCurrentSelectedIndex) {
                WGClassifyItem item = mData.classify.get(index);
                holder.showWithArray(item.allArray());
            }
            else {
                holder.showWithArray(null);
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = maxClassifyTopCount();
        if (mData != null) {
            if (mData.classify != null) {
                count += mData.classify.size() * 2;
            }
        }
        return count;
    }

    @Override
    public int getItemViewType(int position) {
        return getItemViewTypeAfterOrdinal(position).ordinal();
    }

    Item_Type getItemViewTypeAfterOrdinal(int position) {
        Item_Type type = Item_Type.ITEM_TYPE_None;
        if (isLogin()) {
            if (position == 0) {
                type = Item_Type.ITEM_TYPE_Head;
            }
            else if (position == 1) {
                type = Item_Type.ITEM_TYPE_PostCode;
            }
            else if (position == 2) {
                type = Item_Type.ITEM_TYPE_Order;
            }
            else if (position == 3) {
                type = Item_Type.ITEM_TYPE_FootPrints;
            }
            else if (position == 4) {
                type = Item_Type.ITEM_TYPE_Coupon;
            }
            else if (position == 5) {
                type = Item_Type.ITEM_TYPE_Topics;
            }
            else {
                if (isClassifyItem(position)) {
                    type = Item_Type.ITEM_TYPE_SubClassify;
                }
                else {
                    type = Item_Type.ITEM_TYPE_Classify;
                }
            }
        }
        else {
            if (position == 0) {
                type = Item_Type.ITEM_TYPE_RegisterHead;
            }
            else if (position == 1) {
                type = Item_Type.ITEM_TYPE_PostCode;
            }
            else if (position == 2) {
                type = Item_Type.ITEM_TYPE_Topics;
            }
            else {
                if (isClassifyItem(position)) {
                    type = Item_Type.ITEM_TYPE_SubClassify;
                }
                else {
                    type = Item_Type.ITEM_TYPE_Classify;
                }
            }
        }

        return type;
    }

    class RegisterHeadViewHolder extends JHBaseViewHolder {

        LinearLayout mScanLayout;
        LinearLayout mMessageCenterLayout;
        RelativeLayout mLoginLayout;

        public RegisterHeadViewHolder(View view) {
            super(view);
            mScanLayout = (LinearLayout) view.findViewById(R.id.scanLayout);
            mScanLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SliderOnItemClickListener listener = sliderClickListener();
                    if (listener != null) {
                        listener.onScanClick(v);
                    }
                }
            });
            mMessageCenterLayout = (LinearLayout) view.findViewById(R.id.messageLayout);
            mMessageCenterLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SliderOnItemClickListener listener = sliderClickListener();
                    if (listener != null) {
                        listener.onMessageCenterClick(v);
                    }
                }
            });
            mLoginLayout = (RelativeLayout) view.findViewById(R.id.loginLayout);
            mLoginLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SliderOnItemClickListener listener = sliderClickListener();
                    if (listener != null) {
                        listener.onLoginClick(v);
                    }
                }
            });
        }
        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
        }
    }

    //Head
    class HeadViewHolder extends JHBaseViewHolder {

        TextView mNameTextView;

        ImageView mHeadImageView;

        public HeadViewHolder(View view) {
            super(view);
            mNameTextView = (TextView) view.findViewById(R.id.nameTextView);
            mHeadImageView = (ImageView) view.findViewById(R.id.headImageView);
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SliderOnItemClickListener listener = sliderClickListener();
                    if (listener != null) {
                        listener.onPersonInfoClick(view);
                    }
                }
            };
            mNameTextView.setOnClickListener(listener);
            mHeadImageView.setOnClickListener(listener);
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (object instanceof WGUser) {
                mNameTextView.setText(WGApplicationUserUtils.getInstance().fullName());
                mHeadImageView.setImageResource(WGApplicationUserUtils.getInstance().userAvatar());
            }
        }
    }

    //Cap
    class PostCodeViewHolder extends JHBaseViewHolder {

        public TextView textView;

        public PostCodeViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            textView.setText((String )object);
        }
    }

    //CommonItem
    class CommonItemViewHolder extends JHBaseViewHolder {

        public TextView textView;

        public CommonItemViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (object instanceof String) {
                textView.setText((String)object);
            }
        }
    }

    //Topic
    class TopicsViewHolder extends JHBaseViewHolder {

        RelativeLayout mLayout;

        public TopicsViewHolder(View view) {
            super(view);
            mLayout = (RelativeLayout) view.findViewById(R.id.relativeLayout);
        }

        @Override
        public void showWithArray(List list) {
            super.showWithArray(list);
            mLayout.removeAllViews();
            if (list == null) {
                return;
            }
            for (int num = 0; num < list.size(); ++num) {
                final WGTopicItem item = (WGTopicItem)list.get(num);
                View view = LayoutInflater.from(mContext).inflate(R.layout.wgslider_topicitem, mLayout, false);
                RelativeLayout itemLayout = (RelativeLayout) view.findViewById(R.id.item);
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) itemLayout.getLayoutParams();
                int leftMargin = 0;
                int topMargin = 0;
                leftMargin = (num % 2 == 0) ? (int) JHResourceUtils.getInstance().getDimension(R.dimen.x7) :
                        (int) JHResourceUtils.getInstance().getDimension(R.dimen.x123);
                topMargin = (num / 2) * (int) JHResourceUtils.getInstance().getDimension(R.dimen.x62) +
                        (int) JHResourceUtils.getInstance().getDimension(R.dimen.x9);
                lp.leftMargin = leftMargin;
                lp.topMargin = topMargin;
                if (list.size() == num + 1) {
                    lp.bottomMargin = (int) JHResourceUtils.getInstance().getDimension(R.dimen.x9);;
                }
                view.setLayoutParams(lp);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SliderOnItemClickListener listener = sliderClickListener();
                        if (listener != null) {
                            listener.onTopicItemClick(view, item);
                        }
                    }
                });
                TextView textView = (TextView ) view.findViewById(R.id.textView);
                textView.setText(item.name);
                ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
                JHImageUtils.getInstance().loadImage(item.pictureURL, R.drawable.common_image_loading_small, imageView);
                mLayout.addView(view);
            }
        }
    }

    //Classify
    class ClassifyViewHolder extends JHBaseViewHolder {

        public TextView textView;
        public ImageView imageView;

        public ClassifyViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.textView);
            imageView = (ImageView) view.findViewById(R.id.imageView);
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);
            if (object instanceof WGClassifyItem) {
                WGClassifyItem item = (WGClassifyItem) object;
                textView.setText(item.name);
                int resId = item.isSelected ? R.drawable.slider_down_arr : R.drawable.slider_right_arr;
                imageView.setImageResource(resId);
            }
        }
    }

    //ClassifyItem
    class ClassifyItemViewHolder extends JHBaseViewHolder {

        LinearLayout mLayout;

        public ClassifyItemViewHolder(Context context, View view) {
            super(context, view);
            mLayout = (LinearLayout) view.findViewById(R.id.lineLayout);
        }

        @Override
        public void showWithArray(final List list) {
            super.showWithArray(list);
            mLayout.removeAllViews();
            if (list == null) {
                return;
            }
            for (int num = 0; num < list.size(); ++num) {
                final WGClassifyItem item = (WGClassifyItem)list.get(num);
                View view = LayoutInflater.from(mContext).inflate(R.layout.wgslider_classifyitem, mLayout, false);
                view.setTag(num);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SliderOnItemClickListener listener = sliderClickListener();
                        if (listener != null) {
                            listener.onSubClassifyItemClick(view, item);
                        }
                    }
                });
                TextView textView = (TextView ) view.findViewById(R.id.textView);
                textView.setText(item.name);
                mLayout.addView(view);
            }
        }
    }
}
