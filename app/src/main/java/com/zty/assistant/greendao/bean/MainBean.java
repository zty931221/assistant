package com.zty.assistant.greendao.bean;

import androidx.annotation.NonNull;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/11  10:20
 * desc   :
 * version: 1.0
 */
public class MainBean {
    private String appName;
    private String packageName;
    private boolean isInstall;

    public MainBean(String appName, String packageName, boolean isInstall){
        this.appName = appName;
        this.packageName = packageName;
        this.isInstall = isInstall;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppName() {
        return appName;
    }

    public void setInstall(boolean install) {
        isInstall = install;
    }

    public boolean isInstall() {
        return isInstall;
    }

    @NonNull
    @Override
    public String toString() {
        return "MainBean : packageName = " + packageName
                + "\n appName = " + appName
                + "\n isInstall = " + isInstall;
    }
}
