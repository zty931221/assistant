package com.zty.assistant.mvp.main.model;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import com.zty.assistant.MyApplication;
import com.zty.assistant.R;
import com.zty.assistant.greendao.bean.MainBean;
import com.zty.assistant.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.RequiresApi;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/7  15:18
 * desc   :
 * version: 1.0
 */
public class MainModel implements IMainModel {
    private static final String TAG = MainModel.class.getSimpleName();

    private Context mContext;

    public MainModel(Context context){
        mContext = context;
    }

    @Override
    public void init() {

    }

    @Override
    public ArrayList<MainBean> getList() {
        ArrayList<MainBean> reList = new ArrayList<>();
        ArrayList<String> appName = new ArrayList<>(Arrays.asList(mContext.getResources().getStringArray(R.array.support_app_name)));
        ArrayList<String> appPackageName = new ArrayList<>(Arrays.asList(mContext.getResources().getStringArray(R.array.support_app_packagename)));
        ArrayList<String> list = getInstalledPN(mContext.getPackageManager().getInstalledPackages(0));
        for (int i = 0; i < appPackageName.size(); i++) {
            MainBean bean = new MainBean(appName.get(i),appPackageName.get(i),list.contains(appPackageName.get(i)));
            reList.add(bean);
        }
        return reList;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public String[] getPermission() {
        return new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.LOCATION_HARDWARE,Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_SETTINGS,Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO,Manifest.permission.READ_CONTACTS
        };
    }

    /**
     * 拿取app图片资源
     * @param fileName 图片保存的名称
     * @param appName app应用名称
     */
    private void setPicture(String fileName,String appName){
        //获取PackageInfo
        List<PackageInfo> packages = MyApplication.mContext.getPackageManager().getInstalledPackages(0);
        // 获取需要的信息并存储(当然这里可以用增强for循环)
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (packageInfo.applicationInfo.loadLabel(MyApplication.mContext.getPackageManager()).equals(appName)){
                if (!FileUtils.getInstance().isFileExistInAppPath(fileName)){
                    Log.e(TAG, "文件不存在  = " + fileName);
                    Drawable appIcon = packageInfo.applicationInfo.loadIcon(MyApplication.mContext.getPackageManager());// 将drawable转成Bitmap对象
                    Bitmap bm = drawableToBitmap(appIcon);
                    // 指定存储路径以及存储文件名格式
                    File outputImg = new File(FileUtils.getInstance().getAppPath(),  "Picture/" + fileName + ".jpg");
                    // Bitmap存储过程
                    try {
                        FileOutputStream out = new FileOutputStream(outputImg);
                        bm.compress(Bitmap.CompressFormat.PNG, 90, out);
                        out.flush();
                        out.close();
                    } catch (Exception e) {
                        // 不处理异常了
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "文件已存在  = " + fileName);
                }
            }
        }
    }

    /**
     * 将Drawable转成Bitmap
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    private ArrayList<String> getInstalledPN(List<PackageInfo> list){
        ArrayList<String> reList = new ArrayList<>();
        for (PackageInfo info : list) {
            reList.add(info.packageName);
        }
        return reList;
    }

//    private static String string = "a";
    public static void main(String[] args)
    {

//        Long start1 = System.currentTimeMillis();//获取开始时间
//        for (int i=0;i<100000;i++)//重复10万次进行String变量加操作
//        {
//            String str = "a";
//            str+="b";
//        }
//        Long end1 = System.currentTimeMillis();//获取结束时间
//        System.out.println("String花费时间："+(end1-start1));//打印出花费的时间
//
//        Long start2 = System.currentTimeMillis();
//        for (int i=0;i<100000;i++)//重复10万次进行StringBuilder变量加操作
//        {
//            StringBuilder str2 = new StringBuilder("a");
//            str2.append("b");
//        }
//        Long end2 = System.currentTimeMillis();
//        System.out.println("StringBuilder花费时间："+(end2-start2));
//
//        Long start3 = System.currentTimeMillis();
//        for (int i=0;i<100000;i++)//重复10万次进行StringBuffer变量加操作
//        {
//            StringBuffer str2 = new StringBuffer("a");
//            str2.append("b");
//        }
//        Long end3 = System.currentTimeMillis();
//        System.out.println("StringBuffer花费时间："+(end3-start3));

        //证明StringBuffer线程安全，StringBuilder线程不安全
//        final StringBuffer stringBuffer = new StringBuffer();
//        final StringBuilder stringBuilder = new StringBuilder();
//        final CountDownLatch latch1 = new CountDownLatch(1000);
//        final CountDownLatch latch2 = new CountDownLatch(1000);
//        final CountDownLatch latch3 = new CountDownLatch(1000);
//        for(int i=0;i<1000;i++){
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//                    try {
//                        stringBuilder.append(1);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        latch1.countDown();
//                    }
//                }
//            }).start();
//        }
//        for(int i=0;i<1000;i++){
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    try {
//                        stringBuffer.append(1);
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        latch2.countDown();
//                    }
//
//                }
//            }).start();
//        }
//        for(int i=0;i<1000;i++){
//            new Thread(new Runnable() {
//
//                @Override
//                public void run() {
//
//                    try {
//                        string+="1";
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        latch3.countDown();
//                    }
//
//                }
//            }).start();
//        }
//        try {
//            latch1.await();
//            System.out.println(stringBuilder.length());
//            latch2.await();
//            System.out.println(stringBuffer.length());
//            latch3.await();
//            System.out.println(string.length());
//        } catch (InterruptedException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
