package com.zty.assistant.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/18  10:35
 * desc   :
 * version: 1.0
 */
@Entity
public class WorkPlaceBean {
    @Id
    private Long id;
    private String workPlace;

    @Generated(hash = 1648778964)
    public WorkPlaceBean(Long id, String workPlace) {
        this.id = id;
        this.workPlace = workPlace;
    }

    @Generated(hash = 576965898)
    public WorkPlaceBean() {
    }

    public String getWorkPlace() {
        return this.workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
