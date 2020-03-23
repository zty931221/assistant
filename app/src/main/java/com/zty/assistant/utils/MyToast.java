package com.zty.assistant.utils;

import android.widget.Toast;

import com.zty.assistant.MyApplication;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/18  14:24
 * desc   :
 * version: 1.0
 */
public class MyToast {
    private static MyToast mInstance;
    private Toast mToast;

    public static MyToast getInstance(){
        if (mInstance == null){
            mInstance = new MyToast();
        }
        return mInstance;
    }

    private MyToast(){ }

    public void showLong(int i){
        cancel();
        mToast = Toast.makeText(MyApplication.mContext,i,Toast.LENGTH_LONG);
        mToast.show();
    }

    public void showLong(String s){
        cancel();
        mToast = Toast.makeText(MyApplication.mContext,s,Toast.LENGTH_LONG);
        mToast.show();
    }

    public void showShort(int i){
        cancel();
        mToast = Toast.makeText(MyApplication.mContext,i,Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void showShort(String s){
        cancel();
        mToast = Toast.makeText(MyApplication.mContext,s,Toast.LENGTH_SHORT);
        mToast.show();
    }

    /**
     * 取消Toast显示
     */
    public void cancel(){
        if (mToast!=null){
            mToast.cancel();
        }
    }
}
