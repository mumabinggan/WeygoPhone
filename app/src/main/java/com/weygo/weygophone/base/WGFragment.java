package com.weygo.weygophone.base;

import android.widget.Toast;

import com.weygo.common.base.JHFragment;

/**
 * Created by muma on 2016/12/14.
 */

public class WGFragment extends JHFragment {

    public void showWarning(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG);
    }
}
