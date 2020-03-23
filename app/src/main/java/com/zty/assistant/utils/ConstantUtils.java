package com.zty.assistant.utils;

/**
 * Created by zhang.tianyi on 2020/3/16 14:31
 */

public class ConstantUtils {
    private static ConstantUtils mInstance;

    public static ConstantUtils getInstance(){
        if (mInstance == null){
            mInstance = new ConstantUtils();
        }
        return mInstance;
    }

    private ConstantUtils(){ }

    public static final String TypeOfApplyPermission_Normal = "TypeOfApplyPermission_Normal";
    public static final String TypeOfApplyPermission_Never_Mind_Not_Apply = "TypeOfApplyPermission_Never_Mind_Not_Apply";
    public static final String TypeOfApplyPermission_Never_Mind_Apply = "TypeOfApplyPermission_Never_Mind_Apply";


}
