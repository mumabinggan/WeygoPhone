package com.weygo.weygophone.base;

import android.view.View;

import com.weygo.common.base.JHBaseViewHolder;
import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;

import java.util.List;

/**
 * Created by muma on 2017/8/20.
 */

public class WGBaseViewHolder extends JHBaseViewHolder {

    View mView;

    public WGBaseViewHolder(View view) {
        super(view);
        mView = view;
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (mView instanceof JHRelativeLayout) {
            JHRelativeLayout layout = (JHRelativeLayout) mView;
            if (object instanceof List) {
                layout.showWithArray((List) object);
            }
            else {
                layout.showWithData(object);
            }
        }
        else if (mView instanceof JHLinearLayout) {
            JHLinearLayout layout = (JHLinearLayout) mView;
            if (object instanceof List) {
                layout.showWithArray((List) object);
            }
            else {
                layout.showWithData(object);
            }
        }
    }

    public View getView() {
        return mView;
    }
}
