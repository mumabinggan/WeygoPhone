package com.weygo.common.tools.network;

import com.weygo.common.base.JHInterface;
import com.weygo.common.base.JHResponse;

/**
 * Created by muma on 2016/11/30.
 */

public interface JHResponseCallBack extends JHInterface {

    public void onSuccess(JHResponse result);

    public void onFailure(JHRequestError error);
}
