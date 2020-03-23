package com.zty.assistant.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by zhang.tianyi on 2020/3/10 12:05
 */

public class NormalUtils {
    private static NormalUtils mInstance;

    public static NormalUtils getInstance(){
        if (mInstance == null){
            mInstance = new NormalUtils();
        }
        return mInstance;
    }

    private NormalUtils(){ }

    public static final String MYACCESSIBILITYSERVICE_NAME = "com.zty.assistant.service.MyAccessibilityService";
    public static final String MYNOTIFICATIONLISTENERSERVICE_NAME = "com.zty.assistant.service.MyNotificationListenerService";

    /**
     * 判断服务是否开启
     *
     * @return true or false
     */
    public boolean isServiceRunning(Context context, String ServiceName) {
        if (TextUtils.isEmpty(ServiceName)) {
            return false;
        }
        ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(1000);
        for (int i = 0; i < runningService.size(); i++) {
            if (runningService.get(i).service.getClassName().equals(ServiceName)) {
                return true;
            }
        }
        return false;
    }
}
