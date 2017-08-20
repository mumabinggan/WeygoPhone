package com.weygo.common.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.PopupWindow;

import com.weygo.common.base.JHRelativeLayout;

/**
 * Created by muma on 2017/8/18.
 */

public class JHPopView extends JHRelativeLayout {

    protected Context mContext;

    public boolean mCanClose = true;

    public PopupWindow mPopupWindow;
    public void setPopupWindow(PopupWindow window) {
        mPopupWindow = window;
    }

    public JHPopView(Context context) {
        super(context);
        initConfig(context);
    }

    public JHPopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initConfig(context);
    }

    public JHPopView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context);
    }

    public void initConfig(Context context) {
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClose();
            }
        });
    }

    void handleClose() {
        if (mCanClose) {
            dismiss();
        }
    }

    public void dismiss() {
        mPopupWindow.dismiss();
    }
}
