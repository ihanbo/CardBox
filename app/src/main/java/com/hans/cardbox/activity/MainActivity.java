package com.hans.cardbox.activity;

import android.net.Uri;
import android.os.Bundle;
import com.hans.cardbox.base.BaseActivity;
import com.hans.cardbox.fragment.LoginFragment;
import com.hans.cardbox.fragment.RegisterFragment;
import com.hans.cardbox.fragment.SearchMainFragment;
import com.hans.cardbox.tools.UriTools;
import com.hans.cardbox.tools.UserUtils;

public class MainActivity extends BaseActivity {


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        if (UserUtils.getCachedAccount() == null) {
            addFragment(getRootConyainerID(),LoginFragment.newInstance());
        } else {
            addFragment(getRootConyainerID(), SearchMainFragment.newInstance());
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        super.onFragmentInteraction(uri);
        if (uri == null) {
            return;
        }
        if(isUserLogin(uri)){
            if(UriTools.getBooleanQueryParameter(uri,"register")){
                getFragmentManager().popBackStack();
            }
            replaceFragment(getRootConyainerID(), SearchMainFragment.newInstance());
        }else if(isUserRegister(uri)){
            getFragmentManager().beginTransaction()
                    .replace(getRootConyainerID(), RegisterFragment.newInstance())
                    .addToBackStack("zhuce").commit();
        }
    }


    private boolean isUserLogin(Uri uri) {
        if (UriTools.HOST_USER_CENTER.equals(uri.getHost())&&UriTools.PATH_USER_LOGIN.equals(uri.getPath()) ) {
            return true;
        }
        return false;
    }

    private boolean isUserRegister(Uri uri) {
        if (UriTools.HOST_USER_CENTER.equals(uri.getHost())&&UriTools.PATH_USER_REGISTER.equals(uri.getPath()) ) {
            return true;
        }
        return false;
    }
}
