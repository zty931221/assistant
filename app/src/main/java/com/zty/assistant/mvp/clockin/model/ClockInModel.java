package com.zty.assistant.mvp.clockin.model;

import android.annotation.SuppressLint;
import android.content.Context;

import com.zty.assistant.greendao.bean.ClockInBean;
import com.zty.assistant.greendao.bean.DaoManager;
import com.zty.assistant.greendao.bean.WorkPlaceBean;

import java.util.List;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/11  9:32
 * desc   :
 * version: 1.0
 */
public class ClockInModel implements IClockInModel{
    private static final String TAG = ClockInModel.class.getSimpleName();

    private Context mContext;
    private ClockInBean mBean;
    private List<WorkPlaceBean> mList;

    public ClockInModel(Context context){
        mContext = context;
    }



    /**
     * 获得状态栏的高度
     *
     * @return 返回状态栏高度
     */
    public int getStatusHeight() {

        int statusHeight = -1;
        try {
            @SuppressLint("PrivateApi") Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height")
                    .get(object).toString());
            statusHeight = mContext.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * dp转px
     * @param dpValue dp值
     * @return int
     */
    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void insertClockInBean(ClockInBean bean) {
        DaoManager.getInstance().insertClockInBean(bean);
        mBean = DaoManager.getInstance().queryClockInBean();
    }

    @Override
    public ClockInBean queryClockInBean() {
        if (mBean == null){
            mBean = DaoManager.getInstance().queryClockInBean();
        }
        return mBean;
    }

    @Override
    public void insertWorkPlaceBean(WorkPlaceBean bean) {
        DaoManager.getInstance().insertWorkPlaceBean(bean);
        mList = DaoManager.getInstance().queryWorkPlaceBean();
    }

    @Override
    public List<WorkPlaceBean> queryWorkPlaceBean() {
        if (mList == null){
            mList = DaoManager.getInstance().queryWorkPlaceBean();
        }
        return mList;
    }

    @Override
    public void deleteWorkPlaceBean(WorkPlaceBean bean) {
        DaoManager.getInstance().deleteWorkPlaceBean(bean);
        mList = DaoManager.getInstance().queryWorkPlaceBean();
    }
}
