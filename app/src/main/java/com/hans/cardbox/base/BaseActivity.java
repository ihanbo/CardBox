package com.hans.cardbox.base;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.hans.cardbox.tools.IntentBuilder;
import com.hans.cardbox.tools.ToastUtil;

import cn.bmob.v3.Bmob;

import static com.hans.cardbox.base.BaseFragment.*;

public abstract class BaseActivity extends Activity implements OnFragmentInteractionListener, View.OnClickListener {

    private String BMOB_APPID = "d206196911e244219bac77f280f27df6";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(this, BMOB_APPID);
        initView(savedInstanceState);
        initData(savedInstanceState);
    }

    public void toast(String text) {
        ToastUtil.showMessageShort(text);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
    @Override
    public void onClick(View v) {}

    protected int getRootConyainerID(){
        return android.R.id.content;
    }

    protected void addFragment(int containerID, Fragment f) {
        getFragmentManager().beginTransaction().add(containerID, f)
                .commitAllowingStateLoss();
    }

    protected void removeFragment(Fragment f) {
        getFragmentManager().beginTransaction().remove(f)
                .commitAllowingStateLoss();
    }

    protected void replaceFragment(int containerID, Fragment f) {
        getFragmentManager().beginTransaction().replace(containerID, f)
                .commitAllowingStateLoss();
    }


    protected IntentBuilder getIB(Class clazz){
        return IntentBuilder.create(this,clazz);
    }


    protected abstract  void initView(Bundle savedInstanceState);
    protected  abstract void initData(Bundle savedInstanceState);
}
