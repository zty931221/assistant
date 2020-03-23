package com.zty.assistant.mvp.main.view;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zty.assistant.R;
import com.zty.assistant.adapter.MainAdapter;
import com.zty.assistant.greendao.bean.MainBean;
import com.zty.assistant.mvp.clockin.view.ClockInActivity;
import com.zty.assistant.mvp.main.presenter.MainPresenter;
import com.zty.assistant.mvp.wechat.view.WechatActivity;
import com.zty.assistant.utils.MyToast;
import com.zty.assistant.utils.TtsUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements IMainView, MainAdapter.Click {
    private static final String TAG = MainActivity.class.getSimpleName();

    RecyclerView mRecyclerView;

    MainPresenter mPresenter;

    MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG,"-----------onCreate-----------");
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    protected void onRestart() {
        Log.e(TAG,"-----------onRestart-----------");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.e(TAG,"-----------onStart-----------");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.e(TAG,"-----------onResume-----------");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e(TAG,"-----------onPause-----------");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e(TAG,"-----------onStop-----------");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e(TAG,"-----------onDestroy-----------");
        super.onDestroy();
    }

    //初始化一些内容
    public void init(){
        if (mPresenter == null){
            mPresenter = new MainPresenter(this);
        }
        mPresenter.init();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mPresenter.RequestPermission(this);
        }
        setViews();
        setRecycleView();
        //初始化音频
        TtsUtils.getInstance(this);
    }

    private void setViews(){
        mRecyclerView = findViewById(R.id.recycleview);

        //字体加粗
        TextView title = findViewById(R.id.title);
        TextPaint tp = title.getPaint();
        tp.setFakeBoldText(true);

        //设置状态栏的占位图
        LinearLayout status_layout = findViewById(R.id.status_bar_layout);
        ViewGroup.LayoutParams params = status_layout.getLayoutParams();
        int StatusHeight = getStatusHeight();
        params.height = (StatusHeight==(-1) ? dip2px(24+46) : (StatusHeight + dip2px(46)));
        status_layout.setLayoutParams(params);
    }

    private void setRecycleView(){
        if (mAdapter == null) {
            mAdapter = new MainAdapter(this,mPresenter.getList(),this);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this,4);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
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
            statusHeight = getResources().getDimensionPixelSize(height);
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
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public void onClick(MainBean bean) {
        switch (bean.getAppName()){
            case "考勤系统":
                if (bean.isInstall()){
                    ClockInActivity.startActivity(this);
                } else {
                    MyToast.getInstance().showLong("未检测到“考勤系统”的应用");
                }
//                Intent intent = new Intent(this, SecondActivity.class);
//                startActivity(intent);
                break;
            case "微信":
                if (bean.isInstall()){
                    WechatActivity.startActivity(this);
                } else {
                    MyToast.getInstance().showLong("未检测到“微信”");
                }
                break;
        }
    }
}
