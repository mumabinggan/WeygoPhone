package com.weygo.weygophone.pages.personInfo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHRecyclerViewAdapter;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.widget.WGCellStyle4View;
import com.weygo.weygophone.common.widget.WGCellStyle5View;
import com.weygo.weygophone.pages.order.detail.adapter.WGOrderDetailAdapter;
import com.weygo.weygophone.pages.order.list.adapter.WGOrderListAdapter;
import com.weygo.weygophone.pages.order.list.model.WGOrderGoodItem;
import com.weygo.weygophone.pages.order.list.model.WGOrderListItem;
import com.weygo.weygophone.pages.order.list.widget.WGOrderItemPriceView;
import com.weygo.weygophone.pages.order.list.widget.WGOrderListGoodsView;
import com.weygo.weygophone.pages.order.list.widget.WGOrderNumberView;
import com.weygo.weygophone.pages.personInfo.widget.WGPersonInfoHeadView;
import com.weygo.weygophone.pages.tabs.mine.model.WGUser;

import java.util.List;

import static com.weygo.weygophone.common.widget.WGCellStyle4View.WGCellStyleTextAndDetailAndArr;
import static com.weygo.weygophone.common.widget.WGCellStyle4View.WGCellStyleTextAndDetailText;

/**
 * Created by muma on 2017/5/29.
 */

public class WGPersonInfoAdapter extends JHRecyclerViewAdapter {

    WGUser mUser;

    public enum Item_Type {
        ITEM_TYPE_PersonInfoHead,
        ITEM_TYPE_FirstName,
        ITEM_TYPE_Surname,
        ITEM_TYPE_UserId,
        ITEM_TYPE_Mobile,
        ITEM_TYPE_Email,
        ITEM_TYPE_Sex,
        ITEM_TYPE_Birthday,
        ITEM_TYPE_Fax,
        ITEM_TYPE_ChangePW
    }

    public static interface PersonInfoOnItemClickListener extends OnBaseItemClickListener {

        void onSexItemClick(View view, WGUser user);

        void onBrithdayItemClick(View view, WGUser user);

        void onChangePWItemClick(View view, WGUser user);
    }

    public WGPersonInfoAdapter(Context context, WGUser data) {
        mUser = data;
        this.mContext = context;
    }

    public void setData(WGUser user) {
        mUser = user;
        notifyDataSetChanged();
    }

    void handleEndEdit(int viewType, String string) {
        if (viewType == Item_Type.ITEM_TYPE_FirstName.ordinal()) {
            mUser.name = string;
        }
        else if (viewType == Item_Type.ITEM_TYPE_Surname.ordinal()) {
            mUser.surname = string;
        }
        else if (viewType == Item_Type.ITEM_TYPE_Mobile.ordinal()) {
            mUser.mobile = string;
        }
        else if (viewType == Item_Type.ITEM_TYPE_Email.ordinal()) {
            mUser.email = string;
        }
        else if (viewType == Item_Type.ITEM_TYPE_Fax.ordinal()) {
            mUser.tax = string;
        }
    }

    void handleTouchGoodItem(View view) {
        if (mOnItemClickListener != null) {
            int tag = (int) view.getTag();
            PersonInfoOnItemClickListener listener = (PersonInfoOnItemClickListener)mOnItemClickListener;
            if (tag == 6) {
                //性别
                listener.onSexItemClick(view, mUser);
            }
            else if (tag == 7) {
                //出生日期
                listener.onBrithdayItemClick(view, mUser);
            }
            else if (tag == 9) {
                //修改密码
                listener.onChangePWItemClick(view, mUser);
            }
        }
    }

    @Override
    public JHBaseViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        super.onCreateViewHolder(parent, viewType);
        View view = null;
        if (viewType == Item_Type.ITEM_TYPE_PersonInfoHead.ordinal()) {
            view = LayoutInflater.from(
                    mContext).inflate(R.layout.wgpersoninfo_head, parent,
                    false);
        }
        else if (viewType == Item_Type.ITEM_TYPE_FirstName.ordinal() ||
                viewType == Item_Type.ITEM_TYPE_Surname.ordinal() ||
                viewType == Item_Type.ITEM_TYPE_Mobile.ordinal() ||
                viewType == Item_Type.ITEM_TYPE_Fax.ordinal()) {
            WGCellStyle5View commonView = (WGCellStyle5View)LayoutInflater.from(
                    mContext).inflate(R.layout.common_cell_type5, parent,
                    false);
            commonView.setListener(new WGCellStyle5View.OnListener() {
                @Override
                public void onEndEdit(View view, String text) {
                    handleEndEdit(viewType, text);
                }
            });
            view = commonView;
        }
        else {
            WGCellStyle4View commonView = (WGCellStyle4View)LayoutInflater.from(
                    mContext).inflate(R.layout.common_cell_type4, parent,
                    false);
            commonView.setStyle(WGCellStyleTextAndDetailAndArr);
            view = commonView;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    handleTouchGoodItem(view);
                }
            });
        }
        WGPersonInfoItemViewHolder holder = new WGPersonInfoItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(JHBaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.showWithData(mUser);
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class WGPersonInfoItemViewHolder extends JHBaseViewHolder {

        View mView;

        public WGPersonInfoItemViewHolder(View view) {
            super(view);
            mView = view;
        }

        @Override
        public void showWithData(Object object) {
            super.showWithData(object);

            WGUser item = (WGUser)object;
            int tag = (int) mView.getTag();
            if (tag == 0) {
                WGPersonInfoHeadView view = (WGPersonInfoHeadView) mView;
                view.showWithData(item);
            }
            else if (tag == 1) {
                WGCellStyle5View view = (WGCellStyle5View) mView;
                view.mTextView.setText(R.string.PersonInfo_FirstName);
                view.showWithData(mUser.name);
                view.setListener(new WGCellStyle5View.OnListener() {
                    @Override
                    public void onEndEdit(View view, String text) {
                        mUser.name = text;
                    }
                });
            }
            else if (tag == 2) {
                WGCellStyle5View view = (WGCellStyle5View) mView;
                view.mTextView.setText(R.string.PersonInfo_LastName);
                view.showWithData(mUser.surname);
                view.setListener(new WGCellStyle5View.OnListener() {
                    @Override
                    public void onEndEdit(View view, String text) {
                        mUser.surname = text;
                    }
                });
            }
            else if (tag == 3) {
                WGCellStyle4View view = (WGCellStyle4View) mView;
                view.setTitle(R.string.PersonInfo_Number);
                view.setDetailTitle(mUser.userId+"");
                view.setStyle(WGCellStyleTextAndDetailText);
            }
            else if (tag == 4) {
                WGCellStyle5View view = (WGCellStyle5View) mView;
                view.mTextView.setText(R.string.PersonInfo_Phone);
                view.showWithData(mUser.mobile);
                view.setListener(new WGCellStyle5View.OnListener() {
                    @Override
                    public void onEndEdit(View view, String text) {
                        mUser.mobile = text;
                    }
                });
            }
            else if (tag == 5) {
                WGCellStyle4View view = (WGCellStyle4View) mView;
                view.setTitle(R.string.PersonInfo_Email);
                view.setDetailTitle(mUser.email);
            }
            else if (tag == 6) {
                WGCellStyle4View view = (WGCellStyle4View) mView;
                view.setTitle(R.string.PersonInfo_Sex);
                view.setDetailTitle(mUser.sexString());
            }
            else if (tag == 7) {
                WGCellStyle4View view = (WGCellStyle4View) mView;
                view.setTitle(R.string.PersonInfo_Birthday);
                view.setDetailTitle(mUser.birth);
            }
            else if (tag == 8) {
                WGCellStyle5View view = (WGCellStyle5View) mView;
                view.mTextView.setText(R.string.PersonInfo_Fax);
                view.showWithData(mUser.tax);
                view.setListener(new WGCellStyle5View.OnListener() {
                    @Override
                    public void onEndEdit(View view, String text) {
                        mUser.tax = text;
                    }
                });
            }
            else if (tag == 9) {
                WGCellStyle4View view = (WGCellStyle4View) mView;
                view.setTitle(R.string.PersonInfo_ChangePW);
            }
        }
    }
}
