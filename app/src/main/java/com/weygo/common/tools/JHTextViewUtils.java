package com.weygo.common.tools;

import android.graphics.Paint;
import android.widget.TextView;

import com.weygo.common.base.JHObject;

/**
 * Created by muma on 2017/5/29.
 */

public class JHTextViewUtils extends JHObject {

    static public void addMiddleLine(TextView textView) {
        textView.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); //中划线
    }

    static public void addBottomLine(TextView textView) {
        textView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG|Paint.ANTI_ALIAS_FLAG); //中划线
    }
}
