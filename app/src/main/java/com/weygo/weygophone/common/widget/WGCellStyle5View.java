package com.weygo.weygophone.common.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/29.
 */

public class WGCellStyle5View extends RelativeLayout {

    public TextView mTextView;

    public EditText mEditText;

    TextWatcher mTextWatcher;

    public interface OnListener {
        void onEndEdit(View view, String text);
    }

    OnListener mListener;
    public void setListener(OnListener listener) {
        mListener = listener;
    }

    public WGCellStyle5View(Context context) {
        super(context);
    }

    public WGCellStyle5View(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCellStyle5View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTextView = (TextView) findViewById(R.id.titleView);
        mEditText = (EditText) findViewById(R.id.titleValueET);
        final View parent = this;
        mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mListener != null) {
                    mListener.onEndEdit(parent, s.toString());
                }
            }
        };
        mEditText.addTextChangedListener(mTextWatcher);
    }

    public void showWithData(Object object) {
        mEditText.setText((String)object);
    }
}
