package com.hans.cardbox.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.hans.cardbox.R;
import com.hans.cardbox.model.KeyPass;
import com.hans.cardbox.tools.TT;

/**
 * Created by hanbo1 on 2015/8/10.
 */
public class KeyPassItem extends RelativeLayout implements View.OnClickListener {


    private EditText editKey;
    private ImageButton btnAddDesc;
    private EditText editPassword;
    private EditText editDesc;

    public KeyPassItem(Context context) {
        super(context);
        init(context);
    }

    public KeyPassItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context){
        int padding = TT.dip2px(15);
        setPadding(padding,padding,padding,padding);
        LayoutInflater.from(context).inflate(R.layout.view_usercenter_keypass, this, true);
        findViews();
    }

    private void findViews() {
        editKey = (EditText)findViewById( R.id.edit_key );
        btnAddDesc = (ImageButton)findViewById( R.id.btn_add_desc );
        editPassword = (EditText)findViewById( R.id.edit_password );
        editDesc = (EditText)findViewById( R.id.edit_desc );

        btnAddDesc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if ( v == btnAddDesc ) {
            editDesc.setVisibility(editDesc.isShown()?GONE:VISIBLE);
        }
    }


    public boolean isAvilabile(){
        if(TT.isEmpty(editKey.getText().toString().trim())){
            editKey.setError("不能为空！！");
            editKey.requestFocus();
            return false;
        }

        if(TT.isEmpty(editPassword.getText().toString().trim())){
            editPassword.setError("不能为空！！");
            editPassword.requestFocus();
            return false;
        }
        return true;
    }

    public KeyPass getKeyPass(){
        if(!isAvilabile()){
            return null;
        }
        KeyPass kp = new KeyPass(editKey.getText().toString().trim()
                ,editPassword.getText().toString().trim());
        String desc = editDesc.getText().toString().trim();
        if(!TextUtils.isEmpty(desc)){
            kp.setDesc(desc);
        }
       return kp;
    }

}
