package com.weygo.weygophone.pages.address.list.model.response;

import com.weygo.weygophone.base.WGResponse;
import com.weygo.weygophone.pages.address.edit.model.WGAddress;

import java.util.List;

/**
 * Created by muma on 2017/8/22.
 */

public class WGAddressListResponse extends WGResponse {
    public List<WGAddress> data;
}
