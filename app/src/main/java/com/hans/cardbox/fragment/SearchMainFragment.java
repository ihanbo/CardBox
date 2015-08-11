package com.hans.cardbox.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hans.cardbox.R;
import com.hans.cardbox.base.BaseFragment;
import com.hans.cardbox.iinterface.BombInsertListener;
import com.hans.cardbox.model.Card;
import com.hans.cardbox.model.KeyPass;
import com.hans.cardbox.model.QuickKey;
import com.hans.cardbox.tools.LG;
import com.hans.cardbox.tools.UserUtils;

import java.util.ArrayList;
import java.util.List;

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
        if (UserUtils.getCachedAccount() != null) {
            uname.setText("欢迎你，" + UserUtils.getCachedAccount().getName());
        }
        uname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Card card = new Card("12345","招商银行","北京招商银行");
                List<KeyPass> kps = new ArrayList<>();
                kps.add(new KeyPass("6214 8301","567896").ccarID("12345"));
                kps.add(new KeyPass("6214 8302","124354").ccarID("12345"));
                kps.add(new KeyPass("6214 8303","643423").ccarID("12345"));
                List<QuickKey> qks = new ArrayList<>();
                qks.add(new QuickKey("qwe","12345"));
                qks.add(new QuickKey("dsa","12345"));
                qks.add(new QuickKey("dfs","12345"));
                qks.add(new QuickKey("ss","12345"));
                card.kkps(kps);
                card.quicyKeys(qks);
                card.save(mActivity, new BombInsertListener<Card>(mSelf,card) {
                    @Override
                    public void onSucced(Card card) {
                        LG.hh("成功---》");
                    }

                    @Override
                    public void onFailed(int code, String msg) {
                        LG.hh("失败---》"+code+msg);
                    }
                });
            }
        });

    }

    @Override
    protected void initView() {
        uname = (TextView) findViewById(R.id.uname);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_main, container, false);
    }


}
