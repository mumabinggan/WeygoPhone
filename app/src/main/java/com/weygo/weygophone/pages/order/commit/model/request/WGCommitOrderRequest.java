package com.weygo.weygophone.pages.order.commit.model.request;

import com.weygo.weygophone.base.WGGuestRequest;

/**
 * Created by muma on 2017/8/19.
 */

public class WGCommitOrderRequest extends WGGuestRequest {

    public long addressId;

    public long useBilling;     //发票1:用 ： 0 ：不用

    public String billingCompanyName;

    public String billingCountry;

    public String billingPhone;

    public String billingAddress;

    public String billingCivico;

    public String billingCity;

    public String billingCap;

    public String billingProvince;

    public String billingTaxCode;

    public String deliverDate;

    public String deliverTime;

    public String payMethod;

    public String couponCode;

    public String comments;

    public int useIntegral;     //1:使用 0:不使用

    public long couponId;

    public String api() {
        return "checkout/saveOrder?";
    }
}
