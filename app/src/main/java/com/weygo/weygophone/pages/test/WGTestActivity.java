package com.weygo.weygophone.pages.test;

import android.os.Bundle;
import android.util.Log;

import com.weygo.weygophone.R;
import com.weygo.weygophone.base.WGBaseActivity;
import com.weygo.weygophone.models.JHTests;

import org.parceler.Parcels;

/**
 * Created by muma on 2016/12/20.
 */

public class WGTestActivity extends WGBaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_info);
//        JHTests book = Parcels.unwrap(getIntent().getParcelableExtra("book"));
//        Log.e("ss : ", book.ss);
//        Log.e("m : ", book.m + "");
//        Log.e("mm : ", book.mm);
    }

}
