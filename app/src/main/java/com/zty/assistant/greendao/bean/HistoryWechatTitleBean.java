package com.zty.assistant.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zhang.tianyi on 2020/3/16 13:59
 */

@Entity
public class HistoryWechatTitleBean {
    private static final String TAG = HistoryWechatTitleBean.class.getSimpleName();

    @Id
    private Long id;

    private String title;

    @Generated(hash = 1355922403)
    public HistoryWechatTitleBean(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    @Generated(hash = 173774993)
    public HistoryWechatTitleBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
