package com.hans.cardbox;

import android.app.Application;

import com.hans.cardbox.tools.Prefs;
import com.hans.mydb.in.DBConfig;
import com.hans.mydb.in.DD;

/**
 * 项目名称：Bmob_Sample_fast
 * 创建人：韩波
 * 创建时间：2015/8/8 16:37
 * 备注：
 */
public class App extends Application {

    public static App mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        initPrefs();
        initDataBase();
    }



    private void initPrefs(){
        new Prefs.Builder()
                .setContext(this)
                .setMode(MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

    private void initDataBase() {
        String[] ts = getResources().getStringArray(R.array.card_box);
        DBConfig dc = new DBConfig(this, ts, 1, "card_box.db");
        DD.init(dc);
    }
}
