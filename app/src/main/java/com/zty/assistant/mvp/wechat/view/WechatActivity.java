package com.zty.assistant.mvp.wechat.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zty.assistant.R;
import com.zty.assistant.greendao.bean.DaoManager;
import com.zty.assistant.mvp.wechat.presenter.WechatPresenter;
import com.zty.assistant.utils.ConstantUtils;
import com.zty.assistant.utils.NormalUtils;
import com.zty.assistant.utils.PermmisionApply;
import com.zty.assistant.view.DialogClick;
import com.zty.assistant.view.SwitchButton;
import com.zty.assistant.view.TextViewDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


/**
 * Created by zhang.tianyi on 2019/12/20 14:16
 */

public class WechatActivity extends AppCompatActivity implements IWechatView, View.OnClickListener, SwitchButton.OnChangedListener, DialogClick {
    private static final String TAG = WechatActivity.class.getSimpleName();

    private WechatPresenter mPresenter;

    private LinearLayout mLinearNoPM,mLinearWithPM;
    private SwitchButton switchBlackAndWhite,switchOutBlackList,switchInWhiteList;
    private RelativeLayout mBack;
    private RelativeLayout mRelativeBlackAndWhite;
    private TextView mTextPermissionRequest;

    private RecyclerView mWhiteRecyclerView,mBlackRecyclerView;

    public static void startActivity(Context context){
        Intent i = new Intent(context,WechatActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"———————— onCreate ————————");
        setContentView(R.layout.activity_wechat);
        init();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"———————— onStart ————————");
    }

    @Override
    protected void onResume() {
        super.onResume();
        initializeVisible();
        Log.e(TAG,"———————— onResume ————————");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG,"———————— onPause ————————");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"———————— onStop ————————");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"———————— onDestroy ————————");
    }

    @Override
    public void init() {
        mPresenter = new WechatPresenter();
        setViews();
        setSwitch();
        setListener();
    }

    private void setViews(){
        mLinearWithPM = findViewById(R.id.withPermissionLayout);
        mLinearNoPM = findViewById(R.id.noPermissionLayout);
        mBack = findViewById(R.id.back);
        mTextPermissionRequest = findViewById(R.id.permission_request);
        mRelativeBlackAndWhite = findViewById(R.id.black_white_layout);

        switchBlackAndWhite = findViewById(R.id.black_white_list_switch);
        switchOutBlackList = findViewById(R.id.out_of_black_switch);
        switchInWhiteList = findViewById(R.id.in_white_list_switch);

        mWhiteRecyclerView = findViewById(R.id.white_recycleview);
        mBlackRecyclerView = findViewById(R.id.black_recycleview);

        //字体加粗
        TextView title = findViewById(R.id.title);
        TextPaint tp = title.getPaint();
        tp.setFakeBoldText(true);

        //设置状态栏的占位图
        LinearLayout status_layout = findViewById(R.id.status_bar_layout);
        ViewGroup.LayoutParams params = status_layout.getLayoutParams();
        int StatusHeight = mPresenter.getStatusHeight();
        params.height = (StatusHeight==(-1) ? mPresenter.dip2px(24+46) : (StatusHeight + mPresenter.dip2px(46)));
        status_layout.setLayoutParams(params);
    }

    private void setSwitch(){
        switchBlackAndWhite.setCheck(mPresenter.mWechatBean().getSwitchPlayByBlackAndWhite());
        switchOutBlackList.setCheck(mPresenter.mWechatBean().getSwitchOutBlackListPlay());
        switchInWhiteList.setCheck(mPresenter.mWechatBean().getSwitchInWhiteListPlay());
    }

    private void setListener(){
        mBack.setOnClickListener(this);
        switchBlackAndWhite.setOnChangedListener(this);
        switchOutBlackList.setOnChangedListener(this);
        switchInWhiteList.setOnChangedListener(this);
        mTextPermissionRequest.setOnClickListener(this);
    }

    private void initializeVisible(){
        mRelativeBlackAndWhite.setAlpha(mPresenter.mWechatBean().getSwitchPlayByBlackAndWhite()?1:0.5f);

        if (PermmisionApply.getInstance(this).isAccessibilitySettingsOn(this)
                && PermmisionApply.getInstance(this).isNotificationListenersEnabled(this)){
            Log.e(TAG,"有权限");
            mLinearWithPM.setVisibility(View.VISIBLE);
            mLinearNoPM.setVisibility(View.GONE);

            //存在有权限但是没有开始服务的情况。需要用户重新开启权限
            if (NormalUtils.getInstance().isServiceRunning(this,NormalUtils.MYACCESSIBILITYSERVICE_NAME)){
                Log.e(TAG,"无障碍服务已运行");
            } else {
                Log.e(TAG,"无障碍服务未运行");
                switch (DaoManager.getInstance().queryTypeOfApplyPermissionBean().getType()) {
                    case ConstantUtils.TypeOfApplyPermission_Normal:
                        showTextDialog("无障碍服务未运行","无障碍服务未运行，请将权限关闭后重新开启");
                        break;
                    case ConstantUtils.TypeOfApplyPermission_Never_Mind_Apply:
                        PermmisionApply.getInstance(this).requestAccessibility(this);
                        break;
                    case ConstantUtils.TypeOfApplyPermission_Never_Mind_Not_Apply:

                        break;
                }
            }
            if (NormalUtils.getInstance().isServiceRunning(this,NormalUtils.MYNOTIFICATIONLISTENERSERVICE_NAME)){
                Log.e(TAG,"通知栏服务已运行");
            } else {
                Log.e(TAG,"通知栏服务未运行");
                switch (DaoManager.getInstance().queryTypeOfApplyPermissionBean().getType()) {
                    case ConstantUtils.TypeOfApplyPermission_Normal:
                        showTextDialog("通知栏服务未运行","通知栏服务未运行，请将权限关闭后重新开启");
                        break;
                    case ConstantUtils.TypeOfApplyPermission_Never_Mind_Apply:
                        PermmisionApply.getInstance(this).requestNotificationPermission(this);
                        break;
                    case ConstantUtils.TypeOfApplyPermission_Never_Mind_Not_Apply:

                        break;
                }
            }
        } else {
            Log.e(TAG,"无权限");
            mLinearWithPM.setVisibility(View.GONE);
            mLinearNoPM.setVisibility(View.VISIBLE);
        }
        setRecycleView();
    }

    private void showTextDialog(String title,String text){
        TextViewDialog dialog = new TextViewDialog(this,title,text,"跳转权限申请",null,this);
        dialog.setIsShowCheck(true);
        dialog.setCheckText("不再提示，直接申请权限");
        dialog.setSize();
        dialog.show();
    }

    private void setRecycleView(){
        mWhiteRecyclerView.setVisibility(mPresenter.mWechatBean().getSwitchInWhiteListPlay()?View.VISIBLE:View.GONE);
        mBlackRecyclerView.setVisibility(mPresenter.mWechatBean().getSwitchOutBlackListPlay()?View.VISIBLE:View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                finish();
                break;
            case R.id.permission_request:
                if (!PermmisionApply.getInstance(this).isAccessibilitySettingsOn(this)){
                    PermmisionApply.getInstance(this).requestAccessibility(this);
                }
                if (!PermmisionApply.getInstance(this).isNotificationListenersEnabled(this)){
                    PermmisionApply.getInstance(this).requestNotificationPermission(this);
                }
                break;
        }
    }

    @Override
    public void onChanged(View v, boolean checkState) {
        switch (v.getId()){
            case R.id.black_white_list_switch:
                if (checkState){
                    mPresenter.mWechatBean().setSwitchPlayByBlackAndWhite(true);
                    mRelativeBlackAndWhite.setAlpha(1);
                } else {
                    mPresenter.mWechatBean().setSwitchPlayByBlackAndWhite(false);
                    mRelativeBlackAndWhite.setAlpha(0.5f);
                }
                break;
            case R.id.out_of_black_switch:
                if (checkState){
                    if (!PermmisionApply.getInstance(this).isAccessibilitySettingsOn(this)){
                        PermmisionApply.getInstance(this).requestAccessibility(this);
                    } else if (!PermmisionApply.getInstance(this).isNotificationListenersEnabled(this)){
                        PermmisionApply.getInstance(this).requestNotificationPermission(this);
                    } else {
                        mPresenter.mWechatBean().setSwitchOutBlackListPlay(true);
                        mPresenter.mWechatBean().setSwitchInWhiteListPlay(false);
                    }
                } else {
                    mPresenter.mWechatBean().setSwitchOutBlackListPlay(false);
                }
                setRecycleView();
                break;
            case R.id.in_white_list_switch:
                if (checkState){
                    if (!PermmisionApply.getInstance(this).isAccessibilitySettingsOn(this)){
                        PermmisionApply.getInstance(this).requestAccessibility(this);
                    } else if (!PermmisionApply.getInstance(this).isNotificationListenersEnabled(this)){
                        PermmisionApply.getInstance(this).requestNotificationPermission(this);
                    } else {
                        mPresenter.mWechatBean().setSwitchInWhiteListPlay(true);
                        mPresenter.mWechatBean().setSwitchOutBlackListPlay(false);
                    }
                } else {
                    mPresenter.mWechatBean().setSwitchInWhiteListPlay(false);
                }
                setRecycleView();
                break;
        }
        setSwitch();
        DaoManager.getInstance().insertWeChatBean(mPresenter.mWechatBean());
    }

    @Override
    public void onConfirm(String title, Object o) {
        switch (title) {
            case "无障碍服务未运行":
                PermmisionApply.getInstance(this).requestAccessibility(this);
                break;
            case "通知栏服务未运行":
                PermmisionApply.getInstance(this).requestNotificationPermission(this);
                break;
        }
        if ((boolean)o){
            DaoManager.getInstance().updateTypeOfApplyPermissionBean(ConstantUtils.TypeOfApplyPermission_Never_Mind_Apply);
        }
    }

    @Override
    public void onCancel() {

    }
}
