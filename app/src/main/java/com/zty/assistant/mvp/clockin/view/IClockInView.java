package com.zty.assistant.mvp.clockin.view;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/11  9:32
 * desc   :
 * version: 1.0
 */
public interface IClockInView {
    void initView();
    void setListener();
    void setRecycleViewVisibleAndAlpha(boolean isCustomWorkPlace,boolean isHaveWorkPlaceData);
    void startClockIn();
    void setPermissionVisible();
}
