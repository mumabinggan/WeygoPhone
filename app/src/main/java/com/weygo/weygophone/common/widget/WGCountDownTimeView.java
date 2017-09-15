package com.weygo.weygophone.common.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weygo.common.base.JHLinearLayout;
import com.weygo.common.base.JHRelativeLayout;
import com.weygo.weygophone.R;
import com.weygo.weygophone.common.WGConstants;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by muma on 2017/8/17.
 */

public class WGCountDownTimeView extends JHRelativeLayout {

    TextView mDayTV;

    TextView mHourTV;

    TextView mMinuteTV;

    TextView mSecondTV;

    Timer mTimer;

    long mDistanceTime;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            //更新UI
            switch (msg.what)
            {
                case 1:
                    handleTimer(mDistanceTime);
                    break;
            }
        };
    };

    public WGCountDownTimeView(Context context) {
        super(context);
    }

    public WGCountDownTimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WGCountDownTimeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mDayTV = (TextView) findViewById(R.id.dayTV);
        mHourTV = (TextView) findViewById(R.id.hourTV);
        mMinuteTV = (TextView) findViewById(R.id.minuteTV);
        mSecondTV = (TextView) findViewById(R.id.secondTV);
    }

    class Task extends TimerTask {
        private Timer timer;

        public Task(Timer timer) {
            this.timer = timer;
        }

        @Override
        public void run() {
            Log.e("==========", "sss");
            if (mDistanceTime <= 0) {
                this.timer.cancel();
            }
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }

    void handleTimer(long distanceTime) {
        if (distanceTime > 0) {
            setVisibility(VISIBLE);
            String day = String.format("%d", distanceTime / (60 * 60 * 24));
            mDayTV.setText(day);
            String hour = String.format("%d", (distanceTime / (60 * 60)) % 24);
            mHourTV.setText(hour);
            String minute = String.format("%d", (distanceTime / (60)) % 60);
            mMinuteTV.setText(minute);
            String second = String.format("%d", (distanceTime) % 60);
            mSecondTV.setText(second);
            mDistanceTime--;
        }
        else {
            setVisibility(INVISIBLE);
        }
    }

    @Override
    public void showWithData(Object object) {
        super.showWithData(object);
        if (object instanceof Long) {
            long expireTime = (long) object;
            long nowTime = new Date().getTime();
            mDistanceTime = expireTime - nowTime;
            if (mTimer == null) {
                mTimer = new Timer();
                mTimer.schedule(new Task(mTimer), 0, 1000);
            }
            if (mDistanceTime > 0) {
                setVisibility(VISIBLE);
            }
            else {
                setVisibility(INVISIBLE);
            }
        }
    }
}
