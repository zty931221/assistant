package com.zty.assistant.greendao.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zty.assistant.greendao.dao.DaoMaster;
import com.zty.assistant.greendao.dao.DaoSession;
import com.zty.assistant.greendao.dao.HistoryWechatTitleBeanDao;
import com.zty.assistant.greendao.dao.HistoryWorkPlaceBeanDao;
import com.zty.assistant.utils.ConstantUtils;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/11  16:17
 * desc   :
 * version: 1.0
 */
public class DaoManager {
    private static final String TAG = DaoManager.class.getSimpleName();
    private SQLiteDatabase db;
    private DaoSession mDaoSession;
    private static DaoManager mInstance;
    public static DaoManager getInstance(){
        if (mInstance == null){
            mInstance = new DaoManager();
        }
        return mInstance;
    }

    private DaoManager(){ }

    public void setDatebase(Context context){
        MySqlLiteOpenHelper mHelper = new MySqlLiteOpenHelper(context, "clock_in", null);
        db= mHelper.getWritableDatabase();
        DaoMaster mDaoMaster = new DaoMaster(db);
        mDaoSession= mDaoMaster.newSession();
    }

    /*
     ***************************************************************************
     ***************************  考勤系统 ***************************************
     ***************************************************************************
     */

    public void insertClockInBean(ClockInBean bean){
        List<ClockInBean> list = mDaoSession.getClockInBeanDao().queryBuilder().build().list();
        if (list.size() == 0){
            mDaoSession.insert(bean);
        } else {
            mDaoSession.getClockInBeanDao().deleteByKey(list.get(0).getId());
            mDaoSession.getClockInBeanDao().insert(bean);
        }
    }

    public ClockInBean queryClockInBean(){
        List<ClockInBean> list = mDaoSession.getClockInBeanDao().queryBuilder().build().list();
        if (list.size() == 0){
            return new ClockInBean(null,false,"关",
                    false,"关",
                    true,true,false);
        } else {
            return list.get(0);
        }
    }

    public void insertWorkPlaceBean(WorkPlaceBean bean){
        mDaoSession.getWorkPlaceBeanDao().insert(bean);
    }

    public List<WorkPlaceBean> queryWorkPlaceBean(){
        return mDaoSession.getWorkPlaceBeanDao().queryBuilder().build().list();
    }

    public void deleteWorkPlaceBean(WorkPlaceBean bean){
        mDaoSession.getWorkPlaceBeanDao().delete(bean);
    }

    public void insertHistoryWorkPlaceBean(HistoryWorkPlaceBean bean){
        if (mDaoSession.getHistoryWorkPlaceBeanDao().queryBuilder().build().list().size() == 0){
            mDaoSession.getHistoryWorkPlaceBeanDao().insert(bean);
        } else {
            if (mDaoSession.getHistoryWorkPlaceBeanDao().queryBuilder()
                    .where(HistoryWorkPlaceBeanDao.Properties.WorkPlace.eq(bean.getWorkPlace()))
                    .build().list().size() == 0){
                mDaoSession.getHistoryWorkPlaceBeanDao().insert(bean);
            }
        }
    }

    public List<HistoryWorkPlaceBean> queryHistoryWorkPlaceBean(){
        List<HistoryWorkPlaceBean> HistoryWorkPlaceBeanList = mDaoSession.getHistoryWorkPlaceBeanDao().queryBuilder().build().list();
        List<WorkPlaceBean> WorkPlaceBeanList = queryWorkPlaceBean();
        List<HistoryWorkPlaceBean> reList = new ArrayList<>(HistoryWorkPlaceBeanList);
        if (WorkPlaceBeanList.size() != 0 && HistoryWorkPlaceBeanList.size() != 0){
            for (HistoryWorkPlaceBean historyWorkPlaceBean : HistoryWorkPlaceBeanList) {
                Log.e(TAG,"historyWorkPlaceBean = " + historyWorkPlaceBean.getWorkPlace());
                for (WorkPlaceBean workPlaceBean : WorkPlaceBeanList) {
                    if (workPlaceBean.getWorkPlace().equals(historyWorkPlaceBean.getWorkPlace())){
                        reList.remove(historyWorkPlaceBean);
                    }
                }
            }
            return reList;
        } else {
            return HistoryWorkPlaceBeanList;
        }
    }

    public void deleteHistoryWorkPlaceBean(HistoryWorkPlaceBean bean){
        mDaoSession.getHistoryWorkPlaceBeanDao().delete(bean);
    }


    /*
     ***************************************************************************
     *************************** 微信 *******************************************
     ***************************************************************************
     */

    //微信设置

    public void insertWeChatBean(WeChatBean bean){
        if (mDaoSession.getWeChatBeanDao().queryBuilder().build().list().size() != 0){
            mDaoSession.getWeChatBeanDao().deleteAll();
        }
        mDaoSession.getWeChatBeanDao().insert(bean);
    }

    public void deleteWeChatBean(){
        mDaoSession.getWeChatBeanDao().deleteAll();
    }

    public WeChatBean queryWechatBean(){
        List<WeChatBean> list = mDaoSession.getWeChatBeanDao().queryBuilder().build().list();
        if (list.size() != 0){
            return list.get(0);
        } else {
            return new WeChatBean(null,false,false
                    ,false);
        }
    }


    //微信历史标题记录
    public void insertHistoryWechatTitleBean(HistoryWechatTitleBean bean){
        if (mDaoSession.getHistoryWechatTitleBeanDao().queryBuilder().where(HistoryWechatTitleBeanDao.Properties.Title.eq(bean.getTitle())).list().size() == 0){
            mDaoSession.getHistoryWechatTitleBeanDao().insert(bean);
        } else {
            Log.e(TAG,"已经存在该微信历史标题 title = " + bean.getTitle());
        }
    }

    public void deleteHistoryWechatTitleBean(HistoryWechatTitleBean bean){
        mDaoSession.getHistoryWechatTitleBeanDao().delete(bean);
    }

    public List<HistoryWechatTitleBean> queryHistoryWechatTitleBean(){
        return mDaoSession.getHistoryWechatTitleBeanDao().queryBuilder().build().list();
    }


    /*
     ***************************************************************************
     *************************** 权限申请类型 ************************************
     ***************************************************************************
     */

    public void initTypeOfApplyPermissionBean(){//初始化逻辑：大小不等于一就删除所有再插入一个新的bean
        if (mDaoSession.getTypeOfApplyPermissionBeanDao().queryBuilder().build().list().size() != 1){
            mDaoSession.getTypeOfApplyPermissionBeanDao().deleteAll();
            TypeOfApplyPermissionBean bean = new TypeOfApplyPermissionBean();
            bean.setType(ConstantUtils.getInstance().TypeOfApplyPermission_Normal);
            mDaoSession.getTypeOfApplyPermissionBeanDao().insert(bean);
        }
    }

    public void updateTypeOfApplyPermissionBean(String type){
        TypeOfApplyPermissionBean bean = mDaoSession.getTypeOfApplyPermissionBeanDao().queryBuilder().build().list().get(0);
        bean.setType(type);
        mDaoSession.getTypeOfApplyPermissionBeanDao().update(bean);
    }

    public TypeOfApplyPermissionBean queryTypeOfApplyPermissionBean(){
        return mDaoSession.getTypeOfApplyPermissionBeanDao().queryBuilder().build().list().get(0);
    }

    /*
     ***************************************************************************
     *************************** 数据库升级 **************************************
     ***************************************************************************
     */
    public class MySqlLiteOpenHelper extends DaoMaster.OpenHelper{

        public MySqlLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.e("greendao onUpgrade : "," oldVersion = "+oldVersion+" , newVersion = "+newVersion);
            DaoMaster.dropAllTables(db, true);
            onCreate(db);
            try {
                switch (newVersion){
                    case 8:

                        break;
                    case 9:

                        break;
                    case 16:

                        break;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
