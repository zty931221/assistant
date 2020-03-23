package com.zty.assistant.mvp.clockin.presenter;

import android.content.Context;

import com.zty.assistant.greendao.bean.ClockInBean;
import com.zty.assistant.greendao.bean.WorkPlaceBean;
import com.zty.assistant.mvp.clockin.model.ClockInModel;

import java.util.List;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/11  9:32
 * desc   :
 * version: 1.0
 */
public class ClockInPresenter implements IClockInPresenter{
    private static final String TAG = ClockInPresenter.class.getSimpleName();

    private ClockInModel mModel;

    public ClockInPresenter(Context context){
        if (mModel == null) {
            mModel = new ClockInModel(context);
        }
    }

    @Override
    public int getStatusHeight() {
        return mModel.getStatusHeight();
    }

    @Override
    public int dip2px(float dpValue) {
        return mModel.dip2px(dpValue);
    }

    @Override
    public void insertClockInBean(ClockInBean bean) {
        mModel.insertClockInBean(bean);
    }

    @Override
    public ClockInBean queryClockInBean() {
        return mModel.queryClockInBean();
    }

    @Override
    public void insertWorkPlaceBean(WorkPlaceBean bean) {
        mModel.insertWorkPlaceBean(bean);
    }

    @Override
    public List<WorkPlaceBean> queryWorkPlaceBean() {
        return mModel.queryWorkPlaceBean();
    }

    @Override
    public void deleteWorkPlaceBean(WorkPlaceBean bean) {
        mModel.deleteWorkPlaceBean(bean);
    }
}
