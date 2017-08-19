package com.weygo.weygophone.pages.receipt.model;

import com.weygo.weygophone.base.WGObject;

/**
 * Created by muma on 2017/8/19.
 */

public class WGReceipt extends WGObject {

    public long receiptId;

    public String companyName;

    public String country;
    public String countryId = "IT";

    public String phone;

    public String address;

    public String civico;   //街道号

    public String city;

    public String cap;

    public String province;

    public String taxCode;

    public boolean isDefault;

}
