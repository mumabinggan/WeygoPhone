package com.weygo.weygophone.pages.common.widget;

import android.content.Context;
import android.util.AttributeSet;

import com.shizhefei.view.indicator.ScrollIndicatorView;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.weygo.common.tools.JHFontUtils;
import com.weygo.weygophone.R;

/**
 * Created by muma on 2017/5/31.
 */

public class WGSegmentView extends ScrollIndicatorView {
    public WGSegmentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConfig(context);
    }

    void initConfig(Context context) {
        float unSelectSize = JHFontUtils.font(context, 12);
        float selectSize = unSelectSize;
        int selectColor = getResources().getColor(R.color.WGAppBaseColor);
        int unSelectColor = getResources().getColor(R.color.WGAppBaseTitleAColor);
        setScrollBar(new ColorBar(context, selectColor, 5));
        setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(selectSize, unSelectSize));
    }
}
