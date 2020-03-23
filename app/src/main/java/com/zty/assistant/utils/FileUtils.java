package com.zty.assistant.utils;

import android.os.Environment;

import com.zty.assistant.MyApplication;

import java.io.File;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/8  9:34
 * desc   :
 * version: 1.0
 */
public class FileUtils {
    private String ABSOLUTLY_PATH = Environment.getExternalStorageDirectory().getPath();

    private static FileUtils mInstance;
    public static FileUtils getInstance(){
        if (mInstance == null){
            mInstance = new FileUtils();
        }
        return mInstance;
    }

    private FileUtils(){ }

    public boolean mkdirs(String path){
        File file = new File(path);
        return file.mkdirs();
    }

    public boolean isFileExist(String path){
        File file = new File(path);
        return file.exists();
    }

    public boolean mkdirsInAppPath(String path){
        File file = new File(getAppPath(),path);
        return file.mkdirs();
    }

    public boolean isFileExistInAppPath(String path){
        File file = new File(getAppPath(),path);
        return file.exists();
    }

    /**
     * 获取app外部路径
     * @return app外部路径
     */
    public String getAppPath(){
        return ABSOLUTLY_PATH + "/" + MyApplication.mContext.getPackageName();
    }
}
