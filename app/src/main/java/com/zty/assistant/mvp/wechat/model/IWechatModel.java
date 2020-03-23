package com.zty.assistant.mvp.wechat.model;

import com.zty.assistant.greendao.bean.WeChatBean;

/**
 * Created by zhang.tianyi on 2019/12/20 14:19
 */

public interface IWechatModel {
    int getStatusHeight();
    int dip2px(float dpValue);
    WeChatBean getWechatBean();
}
