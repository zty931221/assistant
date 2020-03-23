package com.zty.assistant.greendao.bean;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/15  16:52
 * desc   :
 * version: 1.0
 */
@Entity
public class ClockInBean {
    @Id
    private Long id;
    private boolean autoPicture;
    private String delayAutoPicture;
    private boolean autoSubmitPicture;
    private String delayAutoSubmitPicture;
    private boolean forbidInWork;
    private boolean forbidNotInWorkPlace;
    private boolean customWorkPlace;
    @Generated(hash = 1587184840)
    public ClockInBean(Long id, boolean autoPicture, String delayAutoPicture,
            boolean autoSubmitPicture, String delayAutoSubmitPicture,
            boolean forbidInWork, boolean forbidNotInWorkPlace,
            boolean customWorkPlace) {
        this.id = id;
        this.autoPicture = autoPicture;
        this.delayAutoPicture = delayAutoPicture;
        this.autoSubmitPicture = autoSubmitPicture;
        this.delayAutoSubmitPicture = delayAutoSubmitPicture;
        this.forbidInWork = forbidInWork;
        this.forbidNotInWorkPlace = forbidNotInWorkPlace;
        this.customWorkPlace = customWorkPlace;
    }
    @Generated(hash = 1742141663)
    public ClockInBean() {
    }
    public boolean getAutoPicture() {
        return this.autoPicture;
    }
    public void setAutoPicture(boolean autoPicture) {
        this.autoPicture = autoPicture;
    }
    public String getDelayAutoPicture() {
        return this.delayAutoPicture;
    }
    public void setDelayAutoPicture(String delayAutoPicture) {
        this.delayAutoPicture = delayAutoPicture;
    }
    public boolean getAutoSubmitPicture() {
        return this.autoSubmitPicture;
    }
    public void setAutoSubmitPicture(boolean autoSubmitPicture) {
        this.autoSubmitPicture = autoSubmitPicture;
    }
    public String getDelayAutoSubmitPicture() {
        return this.delayAutoSubmitPicture;
    }
    public void setDelayAutoSubmitPicture(String delayAutoSubmitPicture) {
        this.delayAutoSubmitPicture = delayAutoSubmitPicture;
    }
    public boolean getForbidInWork() {
        return this.forbidInWork;
    }
    public void setForbidInWork(boolean forbidInWork) {
        this.forbidInWork = forbidInWork;
    }
    public boolean getForbidNotInWorkPlace() {
        return this.forbidNotInWorkPlace;
    }
    public void setForbidNotInWorkPlace(boolean forbidNotInWorkPlace) {
        this.forbidNotInWorkPlace = forbidNotInWorkPlace;
    }
    public boolean getCustomWorkPlace() {
        return this.customWorkPlace;
    }
    public void setCustomWorkPlace(boolean customWorkPlace) {
        this.customWorkPlace = customWorkPlace;
    }

    @NonNull
    @Override
    public String toString() {
        return "ClockInBean : "
                + "\n 自动拍照 = " + (autoPicture?"开":"关")
                + (autoPicture?"\n 延迟自动拍照 = " + delayAutoPicture:"")
                + "\n 自动提交拍照 = " + (autoSubmitPicture?"开":"关")
                + (autoSubmitPicture?"\n 延迟自动提交拍照 = " + delayAutoSubmitPicture:"")
                + "\n 上班时间禁止打卡 = " + (forbidInWork?"开":"关")
                + "\n 非工业园禁止打卡 = " + (forbidNotInWorkPlace?"开":"关")
                + "\n 自定义打卡地址 = " + (customWorkPlace?"开":"关");
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
