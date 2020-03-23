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
import com.zty.assistant.greendao.bean.HistoryWorkPlaceBean;

import java.util.List;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/19  16:54
 * desc   :
 * version: 1.0
 */
public class EditTextDialogAdapter extends RecyclerView.Adapter<EditTextDialogAdapter.ViewHolder> {
    private static final String TAG = EditTextDialogAdapter.class.getSimpleName();
    private Context mContext;
    private List<HistoryWorkPlaceBean> mList;
    private EditTextDialogAdapterClick click;

    public EditTextDialogAdapter(Context mContext, List<HistoryWorkPlaceBean> mList,EditTextDialogAdapterClick click){
        this.mContext = mContext;
        this.mList = mList;
        this.click = click;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_dialog_edittext,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.layout = view.findViewById(R.id.layout);
        holder.delete = view.findViewById(R.id.delete);
        holder.text = view.findViewById(R.id.text);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final HistoryWorkPlaceBean bean = mList.get(position);
        holder.text.setText(bean.getWorkPlace());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.click(bean);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.delete(bean);
            }
        });
    }

    public void setmList(List<HistoryWorkPlaceBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout layout;
        private RelativeLayout delete;
        private TextView text;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface EditTextDialogAdapterClick{
        void click(HistoryWorkPlaceBean bean);
        void delete(HistoryWorkPlaceBean bean);
    }
}
