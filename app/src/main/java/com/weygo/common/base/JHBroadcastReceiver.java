package com.weygo.common.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by muma on 2017/12/3.
 */

public class JHBroadcastReceiver extends BroadcastReceiver {

    public interface OnListener {
        void onReceive(Context context, Intent intent);
    }
    OnListener mListener;
    public void setListener(OnListener listener) {
        mListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mListener != null) {
            mListener.onReceive(context, intent);
        }
    }
}
