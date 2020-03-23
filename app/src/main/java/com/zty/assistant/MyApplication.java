package com.zty.assistant;

import android.app.Application;
import android.content.Context;

import com.iflytek.cloud.SpeechUtility;
import com.zty.assistant.greendao.bean.DaoManager;
import com.zty.assistant.utils.FileUtils;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/7  15:09
 * desc   :
 * version: 1.0
 */
public class MyApplication extends Application {

    public static Context mContext;
    public static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init(){
        setmContext();
        setmApplication();
        setDir();
        setTts();
        setGreendao();
    }

    private void setmContext(){
        if (mContext == null){
            mContext = getApplicationContext();
        }
    }

    private void setmApplication(){
        if (mApplication == null){
            mApplication = this;
        }
    }

    /**
     * 创建app外部路径
     */
    private void setDir(){
        FileUtils.getInstance().mkdirsInAppPath("Picture");
    }

    private void setTts(){
        SpeechUtility.createUtility(this.getApplicationContext(),"appid=5dfc859b");
    }

    private void setGreendao(){
        DaoManager.getInstance().setDatebase(this);
        DaoManager.getInstance().initTypeOfApplyPermissionBean();
    }
}
