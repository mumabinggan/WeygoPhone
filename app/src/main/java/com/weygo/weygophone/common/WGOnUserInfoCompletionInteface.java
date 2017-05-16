package com.weygo.weygophone.common;

import com.weygo.weygophone.base.WGInterface;
import com.weygo.weygophone.pages.tabs.mine.model.response.WGUserInfoResponse;

/**
 * Created by muma on 2017/5/16.
 */

public interface WGOnUserInfoCompletionInteface extends WGInterface {
    void onUserInfoCompletion(WGUserInfoResponse response);
}
