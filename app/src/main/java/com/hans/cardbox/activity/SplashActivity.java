package com.hans.cardbox.activity;
import android.os.Bundle;
import com.hans.cardbox.R;
import com.hans.cardbox.base.BaseActivity;
import com.hans.cardbox.tools.Prefs;
import com.hans.cardbox.tools.UserUtils;

public class SplashActivity extends BaseActivity {


    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        UserUtils.getCachedAccount();
        startActivity(getIB(MainActivity.class).build());
        finish();
    }
}
