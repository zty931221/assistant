package com.zty.assistant.mvp.wechat.presenter;

import com.zty.assistant.greendao.bean.WeChatBean;

/**
 * Created by zhang.tianyi on 2019/12/20 14:20
 */

public interface IWechatpresenter {
    int getStatusHeight();
    int dip2px(float dpValue);
    WeChatBean mWechatBean();
}
