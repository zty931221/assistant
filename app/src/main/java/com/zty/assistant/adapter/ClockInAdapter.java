package com.zty.assistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zty.assistant.R;
import com.zty.assistant.greendao.bean.WorkPlaceBean;

import java.util.List;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/19  14:14
 * desc   :
 * version: 1.0
 */
public class ClockInAdapter extends RecyclerView.Adapter<ClockInAdapter.ViewHolder> {
    private static final String TAG = ClockInAdapter.class.getSimpleName();

    private Context mContext;
    private List<WorkPlaceBean> mList;
    private ClockInAdapterClick click;

    public ClockInAdapter(Context mContext,List<WorkPlaceBean> mList,ClockInAdapterClick click){
        this.mContext = mContext;
        this.mList = mList;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_clock_in,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.text = view.findViewById(R.id.text);
        holder.delete = view.findViewById(R.id.delete);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final WorkPlaceBean bean = mList.get(position);
        holder.text.setText(bean.getWorkPlace());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onDelete(bean);
            }
        });
    }

    public void setmList(List<WorkPlaceBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView text;
        private RelativeLayout delete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface ClockInAdapterClick{
        void onDelete(WorkPlaceBean bean);
    }
}
