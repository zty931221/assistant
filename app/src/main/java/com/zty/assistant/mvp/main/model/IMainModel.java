package com.zty.assistant.mvp.main.model;

import com.zty.assistant.greendao.bean.MainBean;

import java.util.ArrayList;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/7  15:18
 * desc   :
 * version: 1.0
 */
public interface IMainModel {
    void init();
    ArrayList<MainBean> getList();
    String[] getPermission();//动态权限申请的具体权限（新增的权限都添加在这里）
}
