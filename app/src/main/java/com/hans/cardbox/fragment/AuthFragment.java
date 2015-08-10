package com.hans.cardbox.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hans.cardbox.R;
import com.hans.cardbox.base.BaseFragment;
import com.hans.cardbox.tools.UriTools;
import com.hans.cardbox.tools.UserUtils;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：开发
 * 创建时间：2015/8/9 19:30
 * 备注：
 */
public class AuthFragment extends BaseFragment {

    private TextView prompt;
    private LinearLayout passwordView;
    private Button b1;
    private Button b2;
    private Button b3;
    private Button b4;
    private Button b5;
    private Button b6;
    private Button b7;
    private Button b8;
    private Button b9;
    private Button b0;
    private Button clear;
    private Button delete;

    private StringBuilder pass = new StringBuilder();
    private int passCount = 0;
    @Override
    protected void initData() {
        passCount = passwordView.getChildCount();
    }

    @Override
    protected void initView() {
        b0 = (Button) findViewById(R.id.b0);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5 = (Button) findViewById(R.id.b5);
        b6 = (Button) findViewById(R.id.b6);
        b7 = (Button) findViewById(R.id.b7);
        b8 = (Button) findViewById(R.id.b8);
        b9 = (Button) findViewById(R.id.b9);
        clear = (Button) findViewById(R.id.clear);
        delete = (Button) findViewById(R.id.delete);
        prompt = (TextView) findViewById(R.id.text_prompt);


        b0.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);
        b7.setOnClickListener(this);
        b8.setOnClickListener(this);
        b9.setOnClickListener(this);
        clear.setOnClickListener(this);
        delete.setOnClickListener(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_auth,container,false);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if(pass.length()==4){
            if(v==clear||v==delete){
                prompt.setText(R.string.prompt_auth);
                prompt.setBackgroundColor(Color.TRANSPARENT);
            }else{
                return;
            }
        }
        if(v==b0){
            pass.append(0);
        }else if(v==b1){
            pass.append(1);
        }else if(v==b2){
            pass.append(2);
        }else if(v==b3){
            pass.append(3);
        }else if(v==b4){
            pass.append(4);
        }else if(v==b5){
            pass.append(5);
        }else if(v==b6){
            pass.append(6);
        }else if(v==b7){
            pass.append(7);
        }else if(v==b8){
            pass.append(8);
        }else if(v==b9){
            pass.append(9);
        }else if(v==clear){
            pass.delete(0,pass.length());
        }else if(v==delete){
            if(pass.length()==0){
                return;
            }
            pass.deleteCharAt(pass.length()-1);
        }
        auth();
    }

    private void auth() {
        int len = pass.length();
        for(int i = 0;i<passCount;i++ ){
            if(i<len){
                passwordView.getChildAt(i).setVisibility(View.VISIBLE);
            }else{
                passwordView.getChildAt(i).setVisibility(View.GONE);
            }
        }
        if(pass.length()<4){
            return;
        }
        if(pass.toString().equals(UserUtils.getCachedAccount().getAuthKey())){
            mConector.onFragmentInteraction(UriTools.getUri(UriTools.HOST_USER_CENTER,UriTools.PATH_USER_NUM_AUTH));
        }else{
            prompt.setText("密码验证失败");
            prompt.setBackgroundColor(Color.RED);
        }
    }
}
