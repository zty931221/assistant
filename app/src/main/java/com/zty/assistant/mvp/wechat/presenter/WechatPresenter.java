package com.zty.assistant.mvp.wechat.presenter;

import com.zty.assistant.greendao.bean.WeChatBean;
import com.zty.assistant.mvp.wechat.model.WechatModel;

/**
 * Created by zhang.tianyi on 2019/12/20 14:19
 */

public class WechatPresenter implements IWechatpresenter{
    private static final String TAG = WechatPresenter.class.getSimpleName();

    private WechatModel mModel;

    public WechatPresenter(){
        if (mModel == null){
            mModel = new WechatModel();
        }
    }

    @Override
    public int getStatusHeight() {
        return mModel.getStatusHeight();
    }

    @Override
    public int dip2px(float dpValue) {
        return mModel.dip2px(dpValue);
    }

    @Override
    public WeChatBean mWechatBean() {
        return mModel.getWechatBean();
    }
}
