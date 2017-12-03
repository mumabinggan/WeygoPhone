package com.weygo.weygophone.pages.order.detail.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weygo.common.tools.JHResourceUtils;
import com.weygo.weygophone.R;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDeliver;
import com.weygo.weygophone.pages.order.detail.model.WGOrderDetail;
import com.weygo.weygophone.pages.order.detail.model.WGOrderFax;

/**
 * Created by muma on 2017/5/22.
 */

public class WGOrderDetailFaxView extends LinearLayout {

    TextView mFaxNumberTV;

    TextView mFaxCompanyTV;

    TextView mFaxCapTV;

    public WGOrderDetailFaxView(Context context) {
        this(context, null);
    }

    public WGOrderDetailFaxView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WGOrderDetailFaxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mFaxNumberTV = (TextView) findViewById(R.id.numberTV);
        mFaxCompanyTV = (TextView) findViewById(R.id.companyTV);
        mFaxCapTV = (TextView) findViewById(R.id.capTV);
    }

    public void showWithData(WGOrderFax fax) {
        if (fax != null) {
            String number = JHResourceUtils.getInstance()
                    .getString(R.string.OrderDetail_Fax_Number);
            mFaxNumberTV.setText(String.format(number, fax.taxCode));
            String company = JHResourceUtils.getInstance()
                    .getString(R.string.OrderDetail_Fax_Company);
            mFaxCompanyTV.setText(String.format(company, fax.companyName));
            String cap = JHResourceUtils.getInstance()
                    .getString(R.string.OrderDetail_Fax_Cap);
            mFaxCapTV.setText(String.format(cap, fax.cap));
        }
    }
}
