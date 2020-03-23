package com.zty.assistant.mvp.wechat.model;

import android.annotation.SuppressLint;

import com.zty.assistant.MyApplication;
import com.zty.assistant.greendao.bean.DaoManager;
import com.zty.assistant.greendao.bean.WeChatBean;

/**
 * Created by zhang.tianyi on 2019/12/20 14:19
 */

public class WechatModel implements IWechatModel {
    private static final String TAG = WechatModel.class.getSimpleName();

    private WeChatBean mWeChatBean;

    public WechatModel(){

    }

    /**
     * 获得状态栏的高度
     *
     * @return 返回状态栏高度
     */
    public int getStatusHeight() {

        int statusHeight = -1;
        try {
            @SuppressLint("PrivateApi") Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = MyApplication.mContext.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * dp转px
     * @param dpValue dp值
     * @return int
     */
    public int dip2px(float dpValue) {
        final float scale = MyApplication.mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public WeChatBean getWechatBean() {
        if (mWeChatBean == null) {
            mWeChatBean = DaoManager.getInstance().queryWechatBean();
        }
        return mWeChatBean;
    }
}
