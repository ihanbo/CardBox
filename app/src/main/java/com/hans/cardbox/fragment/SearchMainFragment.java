package com.hans.cardbox.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hans.cardbox.R;
import com.hans.cardbox.base.BaseFragment;
import com.hans.cardbox.tools.UserUtils;

/**
 *
 */
public class SearchMainFragment extends BaseFragment {
    public static SearchMainFragment newInstance() {
        SearchMainFragment fragment = new SearchMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    TextView uname;
    public SearchMainFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initData() {
            if(UserUtils.getCachedAccount()!=null){
                uname.setText("欢迎你，"+UserUtils.getCachedAccount().getName());
            }
    }

    @Override
    protected void initView() {
        uname= (TextView) findViewById(R.id.uname);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_main, container, false);
    }


}
