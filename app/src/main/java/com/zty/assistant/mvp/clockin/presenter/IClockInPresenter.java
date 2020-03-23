package com.zty.assistant.mvp.clockin.presenter;

import com.zty.assistant.greendao.bean.ClockInBean;
import com.zty.assistant.greendao.bean.WorkPlaceBean;

import java.util.List;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/11  9:33
 * desc   :
 * version: 1.0
 */
public interface IClockInPresenter {
    int getStatusHeight();
    int dip2px(float dpValue);
    void insertClockInBean(ClockInBean bean);
    ClockInBean queryClockInBean();
    void insertWorkPlaceBean(WorkPlaceBean bean);
    List<WorkPlaceBean> queryWorkPlaceBean();
    void deleteWorkPlaceBean(WorkPlaceBean bean);
}
