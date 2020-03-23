package com.zty.assistant.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zty.assistant.R;
import com.zty.assistant.adapter.RadioButtonDialogAdapter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/18  15:49
 * desc   :
 * version: 1.0
 */
public class RadioButtonDialog extends Dialog {
    private static final String TAG = RadioButtonDialog.class.getSimpleName();
    private TextView mTextViewTitle;
    private RecyclerView mRecyclerView;
    private Button mButtonConfirm,mButtonCancel;
    private RadioButtonDialogAdapter adapter;
    private DialogClick click;

    public RadioButtonDialog(@NonNull Context context,String title,String value,DialogClick click) {
        super(context);
        setContentView(R.layout.dialog_radio_button);
        this.click = click;
        init(title,value);
    }

    private void init(String title,String value){
        initView();
        setTitle(title);
        setRecycleView(value);
        setListener(title);
    }

    private void initView(){
        mRecyclerView = findViewById(R.id.recycleview);
        mTextViewTitle = findViewById(R.id.title);
        mButtonConfirm = findViewById(R.id.confirm);
        mButtonCancel = findViewById(R.id.cancel);
    }

    private void setTitle(String title){
        if (title != null){
            mTextViewTitle.setText(title);
        }
    }

    private void setRecycleView(String value){
        if (adapter == null){
            ArrayList<String> appPackageName = new ArrayList<>(Arrays.asList(getContext().getResources().getStringArray(R.array.delay_choise)));
            adapter = new RadioButtonDialogAdapter(getContext(), appPackageName,value);
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }

    private void setListener(final String title){
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onConfirm(title,adapter.getValue());
                cancel();
            }
        });

        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onCancel();
                cancel();
            }
        });
    }
}
