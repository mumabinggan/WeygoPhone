package com.weygo.common.tools;

import android.app.Activity;

import com.weygo.common.base.JHActivity;
import com.weygo.common.base.JHObject;

import java.util.LinkedList;

/**
 * Created by muma on 2016/12/7.
 */

public class JHActivityCollector extends JHObject {

    private static LinkedList<Activity> activityList = new LinkedList();

    public static void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishAll(boolean containRootActivity) {
        for (Activity activity : activityList) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activityList.clear();
    }

    public static LinkedList<Activity> getActivityList() {
        return activityList;
    }

    public static JHActivity firstActivity() {
        return (JHActivity)activityList.getFirst();
    }

    public static JHActivity lastActivity() {
        return (JHActivity)activityList.getLast();
    }

    public static JHActivity getActivity(int index) {
        return (JHActivity)activityList.get(index);
    }
}
