package com.zty.assistant.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/8  9:13
 * desc   :
 * version: 1.0
 */
public class PermmisionApply {
    private static final String TAG = PermmisionApply.class.getSimpleName();
    private final int BASIC_PERMISSION_REQUEST_CODE = 10327;

    public static PermmisionApply getInstance(Object object){
        return new PermmisionApply(object);
    }

    private Object object;
    private PermmisionApply(Object object){
        this.object = object;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(String[] permissions){
        if (object instanceof Activity){
            ((Activity)object).requestPermissions(permissions,BASIC_PERMISSION_REQUEST_CODE);
        } else if (object instanceof Fragment){
            ((Fragment)object).requestPermissions(permissions,BASIC_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * 申请辅助服务权限
     */
    public void requestAccessibility(Context context){
        if (!isAccessibilitySettingsOn(context)){
            Intent accessibleIntent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
            accessibleIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(accessibleIntent);
        }
    }

    /**
     * 判断辅助服务是否开启
     * @param context 上下文
     * @return true为已开启
     */
    public boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch(Settings.SettingNotFoundException e) {
            Log.i("URL", "错误信息为：" + e.getMessage());
        }

        if(accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if(services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }
        return false;
    }

    /**
     * 是否开启了通知栏监听权限
     * @return true or false
     */
    public boolean isNotificationListenersEnabled(Context context) {
        String pkgName = context.getPackageName();
        final String flat = Settings.Secure.getString(context.getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(flat)) {
            final String[] names = flat.split(":");
            for (String name : names) {
                final ComponentName cn = ComponentName.unflattenFromString(name);
                if (cn != null) {
                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 获取状态栏监听权限
     */
    public void requestNotificationPermission(Context context){
        context.startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
    }
}
