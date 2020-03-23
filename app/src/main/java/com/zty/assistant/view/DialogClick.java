package com.zty.assistant.view;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/19  15:03
 * desc   :
 * version: 1.0
 */
public interface DialogClick {
    void onConfirm(String title,Object o);
    void onCancel();
}
