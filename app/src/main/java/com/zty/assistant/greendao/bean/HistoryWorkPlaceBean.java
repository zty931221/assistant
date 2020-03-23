package com.zty.assistant.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/19  15:43
 * desc   :
 * version: 1.0
 */
@Entity
public class HistoryWorkPlaceBean {
    @Id
    private Long id;
    private String workPlace;

    @Generated(hash = 1272474469)
    public HistoryWorkPlaceBean(Long id, String workPlace) {
        this.id = id;
        this.workPlace = workPlace;
    }

    @Generated(hash = 756230765)
    public HistoryWorkPlaceBean() {
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
