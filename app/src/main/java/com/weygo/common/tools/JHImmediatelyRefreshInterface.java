package com.weygo.common.tools;

import com.weygo.common.base.JHInterface;

/**
 * Created by muma on 2017/11/30.
 */

public interface JHImmediatelyRefreshInterface extends JHInterface {

    void onReceiveLogin();

    void onReceiveLogout();
}
