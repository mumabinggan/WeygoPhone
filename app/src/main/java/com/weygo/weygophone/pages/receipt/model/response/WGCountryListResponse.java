package com.weygo.weygophone.pages.receipt.model.response;

import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.pages.receipt.model.WGReceiptCountryListItem;

import java.util.List;

/**
 * Created by muma on 2017/8/22.
 */

public class WGCountryListResponse extends WGResponse {
    public List<WGReceiptCountryListItem> data;
}
