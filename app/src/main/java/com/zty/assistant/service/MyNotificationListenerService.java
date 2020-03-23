package com.zty.assistant.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.zty.assistant.greendao.bean.DaoManager;
import com.zty.assistant.greendao.bean.HistoryWechatTitleBean;
import com.zty.assistant.utils.TtsUtils;

import androidx.annotation.RequiresApi;

/**
 * Created by zhang.tianyi on 2019/12/25 14:18
 */

@SuppressLint("OverrideAbstract")
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MyNotificationListenerService extends NotificationListenerService {
    private static final String TAG = MyNotificationListenerService.class.getSimpleName();

    private final String WX_PACKAGE_NAME = "com.tencent.mm";

    private final String WX_MESSAGE_TYPE_GROUP = "wx.message.type.group";
    private final String WX_MESSAGE_TYPE_PERSONAL = "wx.message.type.personal";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.e(TAG, "base:" + base);
    }

    @Override
    public void onListenerConnected() {
        super.onListenerConnected();
        Log.e(TAG, "onListenerConnected:");
    }

    @Override
    public void onListenerDisconnected() {
        super.onListenerDisconnected();
        Log.e(TAG, "onListenerDisconnected: ");
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Notification notification = sbn.getNotification();
        if (notification == null) {
            return;
        }
        Bundle extras = notification.extras;
        String title;
        String content;
        String packageName;
        if (extras != null) {
            // 获取通知标题
            title = extras.getString(Notification.EXTRA_TITLE, "");
            // 获取通知内容
            content = notification.tickerText == null?
                    extras.getString(Notification.EXTRA_TEXT, ""):
                    notification.tickerText.toString();
            packageName = sbn.getPackageName();
            Log.e("包名：",packageName+"，标题:"+title+"，内容:"+content);
        } else {
            Log.e(TAG,"extras为空");
            return;
        }

        switch (packageName) {
            case WX_PACKAGE_NAME:
                //保存历史title
                HistoryWechatTitleBean bean = new HistoryWechatTitleBean();
                bean.setTitle(title);
                DaoManager.getInstance().insertHistoryWechatTitleBean(bean);
                //Todo 判断title在不在黑/白名单中
                if (DaoManager.getInstance().queryWechatBean().getSwitchPlayByBlackAndWhite()){

                    String[] strings = getTtsData(title,content);
                    TtsUtils.getInstance(this).startSpeaking(strings[0],strings[1],false,null);
                } else {
                    Log.e(TAG,"播报开关没有打开");
                }
                break;
        }
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        Log.e(TAG, "onNotificationRemoved : " + sbn.getPackageName());
    }

    public String[] getTtsData(String title,String content){
        String[] reStrings = new String[3];
        if (content.contains(":")){//消息是组群消息
            reStrings[0] = content.substring(0,content.indexOf(":"));
            reStrings[1] = content.substring(content.indexOf(":")+1);
            reStrings[2] = WX_MESSAGE_TYPE_GROUP;
        } else {//消息是个人消息
            reStrings[0] = title;
            reStrings[1] = content;
            reStrings[2] = WX_MESSAGE_TYPE_PERSONAL;
        }
        return reStrings;
    }
}
