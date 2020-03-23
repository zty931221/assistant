package com.zty.assistant.mvp.main.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.zty.assistant.greendao.bean.MainBean;
import com.zty.assistant.mvp.main.model.MainModel;
import com.zty.assistant.utils.PermmisionApply;

import java.util.ArrayList;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/7  15:16
 * desc   :
 * version: 1.0
 */
public class MainPresenter implements IMainPresenter {
    private static final String TAG = MainPresenter.class.getSimpleName();
    private MainModel mModel;

    public MainPresenter(Context context){
        if (mModel == null){
            mModel = new MainModel(context);
        }
    }

    @Override
    public void init() {
        mModel.init();
    }

    @Override
    public ArrayList<MainBean> getList() {
        return mModel.getList();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void RequestPermission(Activity activity) {
        PermmisionApply.getInstance(activity).requestPermissions(mModel.getPermission());
    }
}
