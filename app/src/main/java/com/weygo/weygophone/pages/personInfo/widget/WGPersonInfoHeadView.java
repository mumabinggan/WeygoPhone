package com.weygo.weygophone.pages.personInfo.widget;

import android.content.Context;
import android.net.MailTo;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.tabs.mine.model.WGUser;

/**
 * Created by muma on 2017/5/29.
 */

public class WGPersonInfoHeadView extends RelativeLayout {

    TextView mTextView;

    ImageView mImageView;

    public WGPersonInfoHeadView(Context context) {
        this(context, null);
    }

    public WGPersonInfoHeadView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGPersonInfoHeadView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = (TextView) findViewById(R.id.textView);
        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    public void showWithData(WGUser item) {
        if (item != null) {
            mImageView.setImageResource(item.userAvatar());
        }
    }
}
