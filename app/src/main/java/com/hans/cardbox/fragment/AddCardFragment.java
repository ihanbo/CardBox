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
public class AddCardFragment extends BaseFragment {
    public static AddCardFragment newInstance() {
        AddCardFragment fragment = new AddCardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public AddCardFragment() {
        // Required empty public constructor
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_main, container, false);
    }


}
