package com.zty.assistant.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zty.assistant.R;
import com.zty.assistant.greendao.bean.MainBean;

import java.util.ArrayList;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/7  15:05
 * desc   :
 * version: 1.0
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    private static final String TAG = MainAdapter.class.getSimpleName();
    private ArrayList<MainBean> mList;
    private Click click;
    private Context mContext;

    public MainAdapter(Context mContext,ArrayList<MainBean> mList,Click click){
        this.mContext = mContext;
        this.mList = mList;
        this.click = click;
    }

    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_main,parent,false);
        MainViewHolder viewHolder = new MainViewHolder(view);
        viewHolder.mLayout = view.findViewById(R.id.layout);
        viewHolder.mImageView = view.findViewById(R.id.image);
        viewHolder.mTextView = view.findViewById(R.id.text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {
        final MainBean bean = mList.get(position);
        holder.mLayout.setAlpha(bean.isInstall()?1:0.5f);
        holder.mImageView.setLayoutParams(getParams(holder.mImageView));
        holder.mTextView.setText(bean.getAppName());
        switch (bean.getPackageName()){
            case "com.byd.moaais":
                holder.mImageView.setBackground(mContext.getResources().getDrawable(R.drawable.com_byd_moaais));
                break;
            case "com.tencent.mm":
                holder.mImageView.setBackground(mContext.getResources().getDrawable(R.drawable.com_tencent_mm));
                break;
        }
        holder.mLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onClick(bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private ViewGroup.LayoutParams getParams(View view){
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = (getScreenWidth() - dip2px()) / 4;
        params.height = (getScreenWidth() - dip2px()) / 4;
        return params;
    }

    /**
     * 获取屏幕宽度
     * @return int
     */
    private int getScreenWidth(){
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * dp转px
     * @return int
     */
    private int dip2px() {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) ((float) 30 * scale + 0.5f);
    }

    class MainViewHolder extends RecyclerView.ViewHolder{
        private View mLayout;
        private ImageView mImageView;
        private TextView mTextView;

        MainViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface Click{
        void onClick(MainBean bean);
    }
}
