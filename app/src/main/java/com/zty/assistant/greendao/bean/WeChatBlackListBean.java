package com.zty.assistant.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import androidx.annotation.NonNull;

/**
 * Created by zhang.tianyi on 2020/1/13 11:17
 */
@Entity
public class WeChatBlackListBean {
    private static final String TAG = WeChatBlackListBean.class.getSimpleName();

    @Id
    private Long id;
    private String name;

    @Generated(hash = 1588230182)
    public WeChatBlackListBean() {
    }

    @Generated(hash = 1060897287)
    public WeChatBlackListBean(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "WeChatBlackListBean : "
                + "\n id = " + id
                + "\n name = " + name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
