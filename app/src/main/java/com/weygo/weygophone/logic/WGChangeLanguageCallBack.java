package com.weygo.weygophone.logic;

import com.weygo.common.base.JHInterface;

import java.util.Locale;

/**
 * Created by muma on 2016/12/14.
 */

public interface WGChangeLanguageCallBack extends JHInterface {
    public void onCompletion(Locale currentLocal, boolean changed);
}
