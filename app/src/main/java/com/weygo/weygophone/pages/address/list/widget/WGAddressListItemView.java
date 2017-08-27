package com.weygo.weygophone.pages.address.list.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.EventLog;
import android.view.View;
import android.widget.TextView;

import com.weygo.common.base.JHRelativeLayout;
import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;
import com.weygo.weygophone.pages.order.commit.widget.WGCommitOrderFooterView;

/**
 * Created by muma on 2017/8/23.
 */

public class WGAddressListItemView extends JHRelativeLayout {

    TextView mNameTV;

    TextView mAddressTV;

    TextView mPhoneTV;

    TextView mDefaultTV;

    TextView mUnDefaultTV;

    TextView mUseTV;

    TextView mChangeTV;

    WGAddress mData;

    public interface OnItemListener {
        void onDelete(WGAddress data);
        void onSetDefault(WGAddress data);
        void onUse(WGAddress data);
        void onChange(WGAddress data);
    }

    OnItemListener mListener;
    public void setListener(OnItemListener listener) {
        mListener = listener;
    }

    public WGAddressListItemView(Context context) {
        super(context);
    }

    public WGAddressListItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGAddressListItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mNameTV = (TextView) findViewById(R.id.nameValueTV);
        mAddressTV = (TextView) findViewById(R.id.addressValueTV);
        mPhoneTV = (TextView) findViewById(R.id.phoneValueTV);
        mDefaultTV = (TextView) findViewById(R.id.defaultTV);
        mDefaultTV.setText("  " + JHResourceUtils.getInstance().getString(R.string.AddressList_Default) + "  ");
        mUnDefaultTV = (TextView) findViewById(R.id.unDefaultTV);
        mUnDefaultTV.setText("  " + JHResourceUtils.getInstance().getString(R.string.AddressList_unDefault) + "  ");
        mUnDefaultTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSetDefault();
            }
        });
        mUseTV = (TextView) findViewById(R.id.useTV);
        mUseTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleUse();
            }
        });
        mChangeTV = (TextView) findViewById(R.id.changeTV);
        mChangeTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleChange();
            }
        });
        this.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                handleDelete();
                return false;
            }
        });
    }

    void handleDelete() {
        if (mListener != null) {
            mListener.onDelete(mData);
        }
    }

    void handleSetDefault() {
        if (mListener != null) {
            mListener.onSetDefault(mData);
        }
    }

    void handleUse() {
        if (mListener != null) {
            mListener.onUse(mData);
        }
    }

    void handleChange() {
        if (mListener != null) {
            mListener.onChange(mData);
        }
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        mData = (WGAddress) object;
        mNameTV.setText(mData.fullName);
        mAddressTV.setText(mData.address);
        mPhoneTV.setText(mData.phone);
        if (mData.isDefault) {
            mUnDefaultTV.setVisibility(GONE);
            mDefaultTV.setVisibility(VISIBLE);
        }
        else {
            mUnDefaultTV.setVisibility(VISIBLE);
            mDefaultTV.setVisibility(GONE);
        }
        if (mData.showUse) {
            mUseTV.setVisibility(VISIBLE);
        }
        else {
            mUseTV.setVisibility(INVISIBLE);
        }
    }
}
