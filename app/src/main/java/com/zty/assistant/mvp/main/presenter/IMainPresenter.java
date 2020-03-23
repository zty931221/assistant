package com.zty.assistant.mvp.main.presenter;

import android.app.Activity;

import com.zty.assistant.greendao.bean.MainBean;

import java.util.ArrayList;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/7  15:17
 * desc   :
 * version: 1.0
 */
public interface IMainPresenter {
    void init();
    ArrayList<MainBean> getList();
    void RequestPermission(Activity activity);
}
