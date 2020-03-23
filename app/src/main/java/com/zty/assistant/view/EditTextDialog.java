package com.zty.assistant.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zty.assistant.R;
import com.zty.assistant.adapter.EditTextDialogAdapter;
import com.zty.assistant.greendao.bean.DaoManager;
import com.zty.assistant.greendao.bean.HistoryWorkPlaceBean;

import java.util.List;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/19  14:58
 * desc   :
 * version: 1.0
 */
public class EditTextDialog extends Dialog implements EditTextDialogAdapter.EditTextDialogAdapterClick {
    private static final String TAG = EditTextDialog.class.getSimpleName();
    private TextView mTextViewTitle;
    private Button mButtonConfirm,mButtonCancel;
    private DialogClick click;
    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private EditTextDialogAdapter mAdapter;
    private List<HistoryWorkPlaceBean> beans;
    private RelativeLayout mRecycleViewLayout;

    public EditTextDialog(@NonNull Context context,String title,DialogClick click) {
        super(context);
        setContentView(R.layout.dialog_edittext);
        this.click = click;
        init(title);
    }

    private void init(String title){
        initView();
        setTitle(title);
        setListener(title);
        setVisible();
    }

    private void initView(){
        mTextViewTitle = findViewById(R.id.title);
        mRecycleViewLayout = findViewById(R.id.recycleviewLayout);
        mButtonConfirm = findViewById(R.id.confirm);
        mButtonCancel = findViewById(R.id.cancel);
        mEditText = findViewById(R.id.edittext);
        mRecyclerView = findViewById(R.id.recycleview);
    }

    private void setTitle(String title){
        if (title != null){
            mTextViewTitle.setText(title);
        }
    }

    private void setListener(final String title){
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onConfirm(title,mEditText.getText().toString());
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
        beans = DaoManager.getInstance().queryHistoryWorkPlaceBean();
        mAdapter = new EditTextDialogAdapter(getContext(), beans,this);
        GridLayoutManager manager = new GridLayoutManager(getContext(),1);
        manager.setOrientation(RecyclerView.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setVisible(){
        beans = DaoManager.getInstance().queryHistoryWorkPlaceBean();
        if (beans.size() == 0){
            mRecycleViewLayout.setVisibility(View.GONE);
        } else {
            mRecycleViewLayout.setVisibility(View.VISIBLE);
            mAdapter.setmList(DaoManager.getInstance().queryHistoryWorkPlaceBean());
        }
    }

    @Override
    public void click(HistoryWorkPlaceBean bean) {
        mEditText.setText(bean.getWorkPlace());
    }

    @Override
    public void delete(HistoryWorkPlaceBean bean) {
        DaoManager.getInstance().deleteHistoryWorkPlaceBean(bean);
        setVisible();
    }
}
