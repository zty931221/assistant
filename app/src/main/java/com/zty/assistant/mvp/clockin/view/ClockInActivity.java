package com.zty.assistant.mvp.clockin.view;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zty.assistant.R;
import com.zty.assistant.adapter.ClockInAdapter;
import com.zty.assistant.greendao.bean.WorkPlaceBean;
import com.zty.assistant.mvp.clockin.presenter.ClockInPresenter;
import com.zty.assistant.service.MyAccessibilityService;
import com.zty.assistant.utils.MyToast;
import com.zty.assistant.utils.NormalUtils;
import com.zty.assistant.utils.PermmisionApply;
import com.zty.assistant.view.DialogClick;
import com.zty.assistant.view.EditTextDialog;
import com.zty.assistant.view.RadioButtonDialog;
import com.zty.assistant.view.SwitchButton;
import com.zty.assistant.view.TextViewDialog;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/8  17:01
 * desc   :
 * version: 1.0
 */
public class ClockInActivity extends AppCompatActivity implements IClockInView, View.OnClickListener, SwitchButton.OnChangedListener,  ClockInAdapter.ClockInAdapterClick, DialogClick {
    private static final String TAG = ClockInActivity.class.getSimpleName();

    private ClockInPresenter mPresenter;

    private RelativeLayout mBack, mLayoutDelayPicture,mLayoutDelaySubmitPicture;
    private LinearLayout mLayoutNoPermission, mLayoutWithPermission,mLayoutRecycleLayout;
    private TextView mTextPermissionRequest,mTextDelayPicture,mTextDelaySubmitPicture;
    private ImageView mImageStartClockIn,mImageAdd;
    private SwitchButton mSwitchAutoPicture,mSwitchAutoSubmitPicture,mSwitchForbidClockInWork,
            mSwitchForbidClockNotInWorkPlace,mSwitchCustomPlace;
    private RecyclerView mRecycleview;
    private ClockInAdapter mAdapter;

    private static final String DELAY_AUTO_PICURE = "延迟自动拍照";
    private static final String DELAY_AUTO_SUBMIT_PICURE = "延迟自动提交拍照";
    private static final String CUSTOM_PLACE = "自定义地址";
    private static final String NOTICE = "友情提示";
    private static final String NOTICE_TEXT = "你还没有添加打卡地址，设置了打卡地址后会很方便哦";

    public static void startActivity(Context context){
        Intent i = new Intent(context,ClockInActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(i);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"-----------onCreate-----------");
        setContentView(R.layout.activity_clock_in);
        init();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG,"-----------onRestart-----------");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG,"-----------onStart-----------");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"-----------onResume-----------");
        setPermissionVisible();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG,"-----------onStop-----------");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"-----------onDestroy-----------");
    }

    private void init(){
        if (mPresenter == null){
            mPresenter = new ClockInPresenter(this);
        }
        initView();
        setListener();
    }

    @Override
    public void initView() {
        mBack = findViewById(R.id.back);
        mLayoutDelayPicture = findViewById(R.id.layout_delay_picture);
        mLayoutDelaySubmitPicture = findViewById(R.id.layout_delay_submit_picture);
        mLayoutNoPermission = findViewById(R.id.noPermissionLayout);
        mLayoutWithPermission = findViewById(R.id.withPermissionLayout);
        mTextPermissionRequest = findViewById(R.id.permission_request);
        mTextDelayPicture = findViewById(R.id.delay_picture);
        mTextDelaySubmitPicture = findViewById(R.id.delay_submit_picture);
        mImageStartClockIn = findViewById(R.id.image_start_clockIn);
        mImageAdd = findViewById(R.id.image_add);
        mSwitchAutoPicture = findViewById(R.id.auto_picture);
        mSwitchAutoSubmitPicture = findViewById(R.id.auto_submit_picture);
        mSwitchForbidClockInWork = findViewById(R.id.forbid_clock_in_work);
        mSwitchForbidClockNotInWorkPlace = findViewById(R.id.forbid_clock_not_in_work_place);
        mSwitchCustomPlace = findViewById(R.id.custom_place);
        mRecycleview = findViewById(R.id.recycleview);
        mLayoutRecycleLayout = findViewById(R.id.recycleLayout);

        mTextDelayPicture.setText(mPresenter.queryClockInBean().getDelayAutoPicture());
        mTextDelaySubmitPicture.setText(mPresenter.queryClockInBean().getDelayAutoSubmitPicture());
        mSwitchAutoPicture.setCheck(mPresenter.queryClockInBean().getAutoPicture());
        mSwitchAutoSubmitPicture.setCheck(mPresenter.queryClockInBean().getAutoSubmitPicture());
        mSwitchForbidClockInWork.setCheck(mPresenter.queryClockInBean().getForbidInWork());
        mSwitchForbidClockNotInWorkPlace.setCheck(mPresenter.queryClockInBean().getForbidNotInWorkPlace());
        mSwitchCustomPlace.setCheck(mPresenter.queryClockInBean().getCustomWorkPlace());

        mLayoutDelayPicture.setVisibility(mPresenter.queryClockInBean().getAutoPicture()?View.VISIBLE:View.GONE);
        mLayoutDelaySubmitPicture.setVisibility(mPresenter.queryClockInBean().getAutoSubmitPicture()?View.VISIBLE:View.GONE);
        setRecycleViewVisibleAndAlpha(mPresenter.queryClockInBean().getCustomWorkPlace(),mPresenter.queryWorkPlaceBean().size() != 0);

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

    @Override
    public void setListener(){
        mBack.setOnClickListener(this);
        mTextPermissionRequest.setOnClickListener(this);
        mImageStartClockIn.setOnClickListener(this);
        mImageAdd.setOnClickListener(this);
        mLayoutDelayPicture.setOnClickListener(this);
        mLayoutDelaySubmitPicture.setOnClickListener(this);

        mSwitchAutoPicture.setOnChangedListener(this);
        mSwitchAutoSubmitPicture.setOnChangedListener(this);
        mSwitchForbidClockInWork.setOnChangedListener(this);
        mSwitchForbidClockNotInWorkPlace.setOnChangedListener(this);
        mSwitchCustomPlace.setOnChangedListener(this);

        mAdapter = new ClockInAdapter(this,mPresenter.queryWorkPlaceBean(),this);

        GridLayoutManager manager = new GridLayoutManager(this,1);
        manager.setOrientation(RecyclerView.VERTICAL);
        mRecycleview.setLayoutManager(manager);
        mRecycleview.setAdapter(mAdapter);
    }

    @Override
    public void setRecycleViewVisibleAndAlpha(boolean isCustomWorkPlace,boolean isHaveWorkPlaceData) {
        if (isCustomWorkPlace){
            mLayoutRecycleLayout.setVisibility(View.VISIBLE);
            mLayoutRecycleLayout.setAlpha(1);
        } else {
            if (isHaveWorkPlaceData){
                mLayoutRecycleLayout.setVisibility(View.VISIBLE);
                mLayoutRecycleLayout.setAlpha(0.3f);
            } else {
                mLayoutRecycleLayout.setVisibility(View.GONE);
                mLayoutRecycleLayout.setAlpha(1);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                this.finish();
                break;
            case R.id.permission_request:
                PermmisionApply.getInstance(this).requestAccessibility(this);
                break;
            case R.id.image_start_clockIn:
                if (mPresenter.queryClockInBean().getCustomWorkPlace() && mPresenter.queryWorkPlaceBean().size() == 0){
                    showTextViewDialog();
                } else {
                    startClockIn();
                }
//                Intent intent = new Intent(this, MainActivity.class);
//                startActivity(intent);
                break;
            case R.id.image_add:
                showEditTextDialog();
                break;
            case R.id.layout_delay_picture:
                showRadioButtonDialog(DELAY_AUTO_PICURE,mPresenter.queryClockInBean().getDelayAutoPicture());
                break;
            case R.id.layout_delay_submit_picture:
                showRadioButtonDialog(DELAY_AUTO_SUBMIT_PICURE,mPresenter.queryClockInBean().getDelayAutoSubmitPicture());
                break;
        }
    }

    private void showRadioButtonDialog(String title,String value){
        RadioButtonDialog dialog = new RadioButtonDialog(this,title,value,this);
        dialog.show();
    }

    private void showEditTextDialog(){
        EditTextDialog dialog = new EditTextDialog(this,CUSTOM_PLACE,this);
        dialog.show();
    }

    private void showTextViewDialog(){
        TextViewDialog dialog = new TextViewDialog(this,NOTICE,NOTICE_TEXT,"头铁别管我","回去再看看",this);
        dialog.show();
    }

    @Override
    public void startClockIn() {
        MyAccessibilityService.isStartClockIn = true;
        PackageManager packageManager = getPackageManager();
        Intent it = packageManager.getLaunchIntentForPackage("com.byd.moaais");
        startActivity(it);
    }

    @Override
    public void setPermissionVisible() {
        if (PermmisionApply.getInstance(this).isAccessibilitySettingsOn(this)){
            mLayoutNoPermission.setVisibility(View.GONE);
            mLayoutWithPermission.setVisibility(View.VISIBLE);
            if (NormalUtils.getInstance().isServiceRunning(this,NormalUtils.MYACCESSIBILITYSERVICE_NAME)){
                Log.e(TAG,"无障碍服务已运行");
            } else {
                Log.e(TAG,"无障碍服务未运行");
            }
        } else {
            mLayoutNoPermission.setVisibility(View.VISIBLE);
            mLayoutWithPermission.setVisibility(View.GONE);
        }
    }

    @Override
    public void onChanged(View v, boolean checkState) {
        switch (v.getId()){
            case R.id.auto_picture:
                if (!mPresenter.queryClockInBean().getAutoPicture() == checkState){
                    mPresenter.queryClockInBean().setAutoPicture(checkState);
                    mPresenter.insertClockInBean(mPresenter.queryClockInBean());
                }
                mLayoutDelayPicture.setVisibility(checkState?View.VISIBLE:View.GONE);
                break;
            case R.id.auto_submit_picture:
                if (!mPresenter.queryClockInBean().getAutoSubmitPicture() == checkState){
                    mPresenter.queryClockInBean().setAutoSubmitPicture(checkState);
                    mPresenter.insertClockInBean(mPresenter.queryClockInBean());
                }
                mLayoutDelaySubmitPicture.setVisibility(checkState?View.VISIBLE:View.GONE);
                break;
            case R.id.forbid_clock_in_work:
                if (!mPresenter.queryClockInBean().getForbidInWork() == checkState){
                    mPresenter.queryClockInBean().setForbidInWork(checkState);
                    mPresenter.insertClockInBean(mPresenter.queryClockInBean());
                }
                break;
            case R.id.forbid_clock_not_in_work_place:
                if (!mPresenter.queryClockInBean().getForbidNotInWorkPlace() == checkState){
                    mPresenter.queryClockInBean().setForbidNotInWorkPlace(checkState);
                    mPresenter.insertClockInBean(mPresenter.queryClockInBean());
                }
                break;
            case R.id.custom_place:
                if (!mPresenter.queryClockInBean().getCustomWorkPlace() == checkState){
                    mPresenter.queryClockInBean().setCustomWorkPlace(checkState);
                    mPresenter.insertClockInBean(mPresenter.queryClockInBean());
                }
                setRecycleViewVisibleAndAlpha(checkState,mPresenter.queryWorkPlaceBean().size() != 0);
                break;
        }
    }

    @Override
    public void onConfirm(String title, Object o) {
        String value;
        switch (title){
            case DELAY_AUTO_PICURE:
                value = (String)o;
                if (!mPresenter.queryClockInBean().getDelayAutoPicture().equals(value)){
                    mPresenter.queryClockInBean().setDelayAutoPicture(value);
                    mPresenter.insertClockInBean(mPresenter.queryClockInBean());
                    mTextDelayPicture.setText(mPresenter.queryClockInBean().getDelayAutoPicture());
                }
                break;
            case DELAY_AUTO_SUBMIT_PICURE:
                value = (String)o;
                if (!mPresenter.queryClockInBean().getDelayAutoSubmitPicture().equals(value)){
                    mPresenter.queryClockInBean().setDelayAutoSubmitPicture(value);
                    mPresenter.insertClockInBean(mPresenter.queryClockInBean());
                    mTextDelaySubmitPicture.setText(mPresenter.queryClockInBean().getDelayAutoSubmitPicture());
                }
                break;
            case CUSTOM_PLACE:
                value = (String)o;
                if (!value.equals("")){
                    WorkPlaceBean bean = new WorkPlaceBean();
                    bean.setWorkPlace(value);
                    mPresenter.insertWorkPlaceBean(bean);
                    mAdapter.setmList(mPresenter.queryWorkPlaceBean());
                } else {
                    MyToast.getInstance().showLong("请输入正确的地址");
                }
                break;
            case NOTICE:
                startClockIn();
                break;
        }
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onDelete(WorkPlaceBean bean) {
        mPresenter.deleteWorkPlaceBean(bean);
        mAdapter.setmList(mPresenter.queryWorkPlaceBean());
        setRecycleViewVisibleAndAlpha(mPresenter.queryClockInBean().getCustomWorkPlace(),mPresenter.queryWorkPlaceBean().size() != 0);
    }
}
