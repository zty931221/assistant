package com.zty.assistant.service;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.zty.assistant.greendao.bean.ClockInBean;
import com.zty.assistant.greendao.bean.DaoManager;
import com.zty.assistant.greendao.bean.HistoryWorkPlaceBean;
import com.zty.assistant.greendao.bean.WorkPlaceBean;
import com.zty.assistant.utils.MyToast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/11  11:35
 * desc   : 这个服务是不需要你在activity里去开启的，属于系统级别辅助服务 需要在设置里去手动开启 和我们平常app里
 * 经常使用的service 是有很大不同的 非常特殊
 * version: 1.0
 */
public class MyAccessibilityService extends AccessibilityService {
    private static final String TAG = MyAccessibilityService.class.getSimpleName();

    //考勤系统主界面
    private static final String HOMEACTIVITY = "com.byd.moa.ais.activity.HomeActivity";
    //拍照界面
    private static final String RECORDACTIVITY = "com.byd.moa.ais.activity.RecordActivity";
    //提交界面
    private static final String NEWVISITACTIVITY = "com.byd.moa.ais.activity.NewVisitActivity";

    //主界面
    private static final String HOMEACTIVITY_BUTTON_WORK_ID = "com.byd.moaais:id/tab_img";//“工作”fragment（防止主界面不在工作界面）
    private static final String HOMEACTIVITY_BUTTON_CLOCK_IN_ID = "com.byd.moaais:id/v_attendance";//打卡按钮
    //拍照界面
    private static final String RECORDACTIVITY_BUTTON_TAKE_PICTURE_ID = "com.byd.moaais:id/circle_progress";//拍照按钮
    private static final String RECORDACTIVITY_BUTTON_CONFIRM_ID = "com.byd.moaais:id/iv_confirm";//确定按钮
    //提交界面
    private static final String NEWVISITACTIVITY_TEXT_GONGHAO_ID = "com.byd.moaais:id/tv_gonghao";//工号文本
    private static final String NEWVISITACTIVITY_TEXT_NAME_ID = "com.byd.moaais:id/tv_name";//姓名文本
    private static final String NEWVISITACTIVITY_TEXT_LOCATION_TYPE_ID = "com.byd.moaais:id/tv_location_type";//工业园打卡文本
    private static final String NEWVISITACTIVITY_TEXT_DECICE_LOCATION_ID = "com.byd.moaais:id/tv_device_location";//定位地点文本
    private static final String NEWVISITACTIVITY_BUTTON_LOCATION_ID = "com.byd.moaais:id/iv_locate";//定位按钮
    private static final String NEWVISITACTIVITY_BUTTON_REPHOTO_ID = "com.byd.moaais:id/tv_take_photo";//重拍按钮
    private static final String NEWVISITACTIVITY_BUTTON_CONFIRM_ID = "com.byd.moaais:id/tv_submit";//提交按钮

    public static boolean isStartClockIn = false;
    public static boolean isServiceConnected = false;

    /**
     * AccessibilityService 这个服务可以关联很多属性，这些属性 一般可以通过代码在这个方法里进行设置，
     * 我这里偷懒 把这些设置属性的流程用xml 写好 放在manifest里，如果你们要使用的时候需要区分版本号
     * 做兼容，在老的版本里是无法通过xml进行引用的 只能在这个方法里手写那些属性 一定要注意.
     * 同时你的业务如果很复杂比如需要初始化广播啊之类的工作 都可以在这个方法里写。
     */
    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        Log.e(TAG,"---------onServiceConnected---------");
        isServiceConnected = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"---------onDestroy---------");
    }

    /**
     * 当你这个服务正常开启的时候，就可以监听事件了，当然监听什么事件，监听到什么程度 都是由给这个服务的属性来决定的，
     * 我的那些属性写在xml里了。
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.e(TAG, "event.getEventType  = " + event.getEventType());
        String className = event.getClassName().toString();
        Log.e(TAG, "className  = " + className);
        ClockInBean bean;
        switch (event.getEventType()){
            case AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED://32
                switch (className){
                    case HOMEACTIVITY:{
                        if (isStartClockIn){
                            //点击打卡按钮
                            if (findViewAndClick(this, HOMEACTIVITY_BUTTON_CLOCK_IN_ID,false)){
                                Log.e(TAG, "点击成功1");
                            } else {//在主界面点击打卡失败，可能是在“统计”或者“设置”的界面，点击一次工作按钮再尝试点击打卡按钮
                                Log.e(TAG, "点击失败1，尝试点击工作界面");
                                if (findViewAndClick(this, HOMEACTIVITY_BUTTON_WORK_ID,true)){
                                    Log.e(TAG, "点击成功2");
                                    findViewAndClick(this, HOMEACTIVITY_BUTTON_CLOCK_IN_ID,false);
                                } else {
                                    Log.e(TAG, "点击失败2");
                                }
                            }
                        }
                        break;
                    }
                    case RECORDACTIVITY:{
                        bean = DaoManager.getInstance().queryClockInBean();
                        if (findViewVisibleById(this,RECORDACTIVITY_BUTTON_TAKE_PICTURE_ID)){//拍照按钮可见
                            Log.e(TAG,"拍照按钮可见");
                            if (bean.getAutoPicture()){
                                if (bean.getDelayAutoPicture().equals("关")){
                                    findViewAndClick(this,RECORDACTIVITY_BUTTON_TAKE_PICTURE_ID,false);
                                } else {
                                    long time = getLong(bean.getDelayAutoPicture().replace("秒",""));
                                    startDelayClick(RECORDACTIVITY_BUTTON_TAKE_PICTURE_ID,time);
                                }
                            }
                        } else {
                            Log.e(TAG,"拍照按钮不可见");
                        }
                        if (findViewVisibleById(this,RECORDACTIVITY_BUTTON_CONFIRM_ID)){//确定按钮可见
                            Log.e(TAG,"确定按钮可见");
                            if (bean.getAutoSubmitPicture()){
                                if (bean.getDelayAutoSubmitPicture().equals("关")){
                                    findViewAndClick(this,RECORDACTIVITY_BUTTON_CONFIRM_ID,false);
                                } else {
                                    long time = getLong(bean.getDelayAutoPicture().replace("秒",""));
                                    startDelayClick(RECORDACTIVITY_BUTTON_CONFIRM_ID,time);
                                }
                            }
                        } else {
                            Log.e(TAG,"确定按钮不可见");
                        }
                        break;
                    }
                    case NEWVISITACTIVITY:{
                        isStartClockIn = false;
                        bean = DaoManager.getInstance().queryClockInBean();
                        if (bean.getForbidInWork()){
                            if (isInWorkTime()){
                                MyToast.getInstance().showLong("当前是工作时间，不能提交打卡");
                                return;
                            }
                        }
                        String locationType = findViewTextByID(this,NEWVISITACTIVITY_TEXT_LOCATION_TYPE_ID);
                        String location = findViewTextByID(this,NEWVISITACTIVITY_TEXT_DECICE_LOCATION_ID);
                        DaoManager.getInstance().insertHistoryWorkPlaceBean(new HistoryWorkPlaceBean(null,location));

                        if (bean.getForbidNotInWorkPlace()){
                            while (!locationType.equals("工业园打卡")) {
                                findViewAndClick(this,NEWVISITACTIVITY_BUTTON_LOCATION_ID,false);
                                DaoManager.getInstance().insertHistoryWorkPlaceBean(new HistoryWorkPlaceBean(null,location));
                            }
                        }
                        if (bean.getCustomWorkPlace()){
                            List<WorkPlaceBean> beans = DaoManager.getInstance().queryWorkPlaceBean();
                            List<String> mList = new ArrayList<>();
                            for (WorkPlaceBean workPlaceBean : beans) {
                                mList.add(workPlaceBean.getWorkPlace());
                            }
                            if (mList.size() != 0){
                                while (!mList.contains(location)){
                                    findViewAndClick(this,NEWVISITACTIVITY_BUTTON_LOCATION_ID,false);
                                    DaoManager.getInstance().insertHistoryWorkPlaceBean(new HistoryWorkPlaceBean(null,location));
                                }
                                findViewAndClick(this, NEWVISITACTIVITY_BUTTON_CONFIRM_ID,true);
                            } else {
                                MyToast.getInstance().showLong("请手动提交");
                            }
                        } else {
                            MyToast.getInstance().showLong("请手动提交");
                        }
                        break;
                    }
                }
                break;
        }
    }

    @Override
    public void onInterrupt() {

    }

    private long getLong(String time){
        switch (time){
            case "1秒":
                return 1000;
            case "1.5秒":
                return 1500;
            case "2秒":
                return 2000;
            case "2.5秒":
                return 2500;
            case "3秒":
                return 3000;
            default:
                return 1000;
        }
    }

    private boolean isInWorkTime(){
        long time = System.currentTimeMillis();
        Calendar mCalendar=Calendar.getInstance();
        mCalendar.setTimeInMillis(time);
        int mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        int mMinuts = mCalendar.get(Calendar.MINUTE);
        int now = mHour*60 + mMinuts;
        return (now > 510 && now < 720) || (now > 780 && now < 1050);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void startDelayClick(final String id, long time){
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                findViewAndClick(MyAccessibilityService.this,id,false);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,time);
    }

    /**
     * 通过ID查看控件是否可见
     * @param accessibilityService accessibilityService
     * @param id 控件ID
     * @return 是否可见（如果未找到该控件也会返还false）
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean findViewVisibleById(AccessibilityService accessibilityService, String id){
        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return false;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            return nodeInfoList.get(0).isVisibleToUser();
        }
        return false;
    }


    /**
     * 通过控件ID找到控件文本内容
     * @param accessibilityService accessibilityService
     * @param id 控件ID
     * @return 控件文本内容
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String findViewTextByID(AccessibilityService accessibilityService, String id){
        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return "";
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            return nodeInfoList.get(0).getText().toString();
        }
        return "";
    }

    /**
     * 找到控件并进行点击
     * @param accessibilityService accessibilityService
     * @param id 控件id
     * @param findParent 如果该控件不可点击，希望找到父控件并点击则传ture，不想找到父控件则传false
     * @return 是否点击成功
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static boolean findViewAndClick(AccessibilityService accessibilityService, String id, boolean findParent) {
        AccessibilityNodeInfo accessibilityNodeInfo = accessibilityService.getRootInActiveWindow();
        if (accessibilityNodeInfo == null) {
            return false;
        }
        List<AccessibilityNodeInfo> nodeInfoList = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(id);
        if (nodeInfoList != null && !nodeInfoList.isEmpty()) {
            for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
                if (findParent){
                    return performClick(nodeInfo);
                } else {
                    if (nodeInfo.isClickable()){
                        return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                    }
                }
            }
        }
        return false;
    }

    //模拟点击事件
    public static boolean performClick(AccessibilityNodeInfo nodeInfo) {
        if (nodeInfo == null) {
            return false;
        }
        if (nodeInfo.isClickable()) {
            return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        } else {
            return performClick(nodeInfo.getParent());
        }
    }
}
