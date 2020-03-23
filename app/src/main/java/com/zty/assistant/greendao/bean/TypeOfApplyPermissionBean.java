package com.zty.assistant.greendao.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by zhang.tianyi on 2020/3/16 14:28
 */

@Entity
public class TypeOfApplyPermissionBean {
    private static final String TAG = TypeOfApplyPermissionBean.class.getSimpleName();

    @Id
    private Long id;

    private String type;

    @Generated(hash = 1592365631)
    public TypeOfApplyPermissionBean(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    @Generated(hash = 1511327018)
    public TypeOfApplyPermissionBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
