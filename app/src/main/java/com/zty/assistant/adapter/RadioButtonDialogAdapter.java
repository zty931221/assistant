package com.zty.assistant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zty.assistant.R;

import java.util.ArrayList;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/18  16:16
 * desc   :
 * version: 1.0
 */
public class RadioButtonDialogAdapter extends RecyclerView.Adapter<RadioButtonDialogAdapter.ViewHolder> {
    private static final String TAG = RadioButtonDialogAdapter.class.getSimpleName();
    private Context mContext;
    private ArrayList<String> mList;
    private String value;

    public RadioButtonDialogAdapter(Context mContext, ArrayList<String> mList, String value){
        this.mContext = mContext;
        this.mList = mList;
        this.value = value;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recycleview_item_dialog_radio_button,parent,false);
        ViewHolder holder = new ViewHolder(view);
        holder.layout = view.findViewById(R.id.layout);
        holder.text = view.findViewById(R.id.text);
        holder.image = view.findViewById(R.id.image);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.text.setText(mList.get(position));
        if (mList.indexOf(value) == position){
//            holder.image.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.checked));
            holder.image.setBackgroundResource(R.drawable.checked);
        } else {
//            holder.image.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(), R.drawable.unchecked));
            holder.image.setBackgroundResource(R.drawable.unchecked);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = mList.get(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private RelativeLayout layout;
        private TextView text;
        private ImageView image;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public String getValue() {
        return value;
    }
}
