package com.zty.assistant.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.zty.assistant.R;

/**
 * author : zhang.tianyi
 * e-mail : 1055026824@qq.com
 * date   : 2019/11/19  15:24
 * desc   :
 * version: 1.0
 */
public class TextViewDialog extends Dialog {
    private static final String TAG = TextViewDialog.class.getSimpleName();
    private TextView mTextViewTitle;
    private Button mButtonConfirm,mButtonCancel;
    private DialogClick click;
    private TextView mTextView;
    private LinearLayout mCheckLayout;
    private CheckBox mCheckBox;
    private TextView mCheckTextView;

    public TextViewDialog(@NonNull Context context,String title,String text,String confirmText,String cancelText,DialogClick click) {
        super(context);
        setContentView(R.layout.dialog_textview);
        this.click = click;
        init(title,text,confirmText,cancelText);
    }

    private void init(String title,String text,String confirmText,String cancelText){
        initView();
        setTitle(title);
        setText(text);
        setConfirmText(confirmText);
        setCancelText(cancelText);
        setListener(title);
    }

    private void initView(){
        mTextViewTitle = findViewById(R.id.title);
        mButtonConfirm = findViewById(R.id.confirm);
        mButtonCancel = findViewById(R.id.cancel);
        mTextView = findViewById(R.id.textview);
        mCheckLayout = findViewById(R.id.checkLayout);
        mCheckBox = findViewById(R.id.checkbox);
        mCheckTextView = findViewById(R.id.checktext);
    }

    private void setTitle(String title){
        if (title != null){
            mTextViewTitle.setText(title);
        }
    }

    private void setText(String text){
        if (text != null){
            mTextView.setText(text);
        }
    }

    private void setConfirmText(String text){
        if (text != null){
            mButtonConfirm.setText(text);
        }
    }

    private void setCancelText(String text){
        if (text != null){
            mButtonCancel.setText(text);
        }
    }

    public void setIsShowCheck(boolean isShowCheck){
        mCheckLayout.setVisibility(isShowCheck?View.VISIBLE:View.GONE);
    }

    public void setCheckText(String text){
        if (text != null) {
            mCheckTextView.setText(text);
        }
    }

    private void setListener(final String title){
        mButtonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.onConfirm(title,mCheckBox.isChecked());
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

    public void setSize(){
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = getWindow().getWindowManager().getDefaultDisplay().getWidth()/4*3;
        this.getWindow().setAttributes(params);
    }
}
