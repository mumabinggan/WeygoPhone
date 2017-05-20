package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;

import org.w3c.dom.Text;

/**
 * Created by muma on 2016/12/16.
 */

public class WGCellStyle4View extends RelativeLayout {

    public static final int WGCellStyleOnlyText = 1;     //Only Text
    public static final int WGCellStyleTextAndArr = 2;     //Text and arr
    public static final int WGCellStyleTextAndDetailText = 3;     //Text and detail
    public static final int WGCellStyleTextAndDetailAndArr = 4;     //Test and detail and arr

    private TextView    mtextView;
    private ImageView   mRightImageView;
    private TextView    mDetailTextView;

    int mStyle = 1;
    String mTitle;
    String mDetailTitle;

    public void setTitle(String title) {
        mtextView.setText(title);
    }

    public void setTitle(int resId) {
        mtextView.setText(resId);
    }

    public void setDetailTitle(int resId) {
        mDetailTextView.setText(resId);
    }

    public void setDetailTitle(String title) {
        mDetailTextView.setText(title);
    }

    public void setRightBarItem(int resId) {
        mRightImageView.setImageResource(resId);
    }

    public WGCellStyle4View(Context context) {
        this(context, null);
    }

    public WGCellStyle4View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCellStyle4View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAtts(context, attrs, defStyleAttr);
    }

    public void setmStyle(int style) {
        setStyle(style);
    }

    public void setmTitle(String title) {
        mtextView.setText(title);
    }

    public void  setmDetailTitle(String title) {
        mDetailTextView.setText(title);
    }

    void getAtts(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WGCellStyle4View);
        mStyle = ta.getInteger(R.styleable.WGCellStyle4View_style, 1);
        mTitle = ta.getString(R.styleable.WGCellStyle4View_title);
        mDetailTitle = ta.getString(R.styleable.WGCellStyle4View_detailTitle);
        ta.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mRightImageView = (ImageView) findViewById(R.id.arrImageView);
        mtextView = (TextView) findViewById(R.id.textView);
        mDetailTextView = (TextView) findViewById(R.id.detailTextView);
        setStyle(mStyle);
    }

    public void setStyle(int style) {
        mStyle = style;
        if (style == WGCellStyleOnlyText) {
            mDetailTextView.setVisibility(GONE);
            mRightImageView.setVisibility(GONE);
        }
        else if (style == WGCellStyleTextAndArr) {
            mDetailTextView.setVisibility(GONE);
        }
        else if (style == WGCellStyleTextAndDetailText) {
            mRightImageView.setVisibility(GONE);
        }
    }
}
