package com.hans.cardbox.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hans.cardbox.R;
import com.hans.cardbox.base.BaseFragment;
import com.hans.cardbox.control.CardControl;
import com.hans.cardbox.control.UserControl;
import com.hans.cardbox.model.Card;
import com.hans.cardbox.model.KeyPass;
import com.hans.cardbox.model.QuickKey;
import com.hans.cardbox.tools.UserUtils;
import com.hans.cardbox.view.KeyPassItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    private EditText cardName;
    private EditText cardQuickKey;
    private EditText cardDesc;
    private LinearLayout viewKeypass;
    private Button addKeyPass;
    private Button save;

    public AddCardFragment() {
        // Required empty public constructor
    }


    /**
     * Handle button click events<br />
     * <br />
     * Auto-created on 2015-08-11 10:47:06 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    @Override
    public void onClick(View v) {
        if ( v == addKeyPass ) {
            viewKeypass.addView(new KeyPassItem(mActivity),new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        } else if ( v == save ) {
            if(!checked()){
                return;
            }
            String carID = System.currentTimeMillis()+"";
            String name = cardName.getText().toString().trim();
            String quickKeyStr = cardQuickKey.getText().toString().trim();
            //快速搜索的key
            List<QuickKey> quickKeys = getQuickKeys(quickKeyStr,carID);
            //账户密码键值对
            List<KeyPass> kps = getAllKeyPass();
            String desc = cardDesc.getText().toString().trim();

            Card card = new Card(carID,name,desc)
            .kkps(kps)
            .quicyKeys(quickKeys);

            CardControl.addCard(card,this);
        }
    }

    /**
     * 搜索用的key
     * @param quickKeyStr
     * @return
     */
    private List<QuickKey> getQuickKeys(String quickKeyStr,String cardID) {
        String[] qks = quickKeyStr.split(",");
        if(qks==null||qks.length==0){
            return null;
        }
        List<QuickKey> quickKeys = new ArrayList<>();
        for(int i=0,size = qks.length;i<size;i++){
            quickKeys.add(new QuickKey(qks[i],cardID));
        }
        return quickKeys;
    }

    private boolean checked() {
        if(TextUtils.isEmpty(cardName.getText().toString().trim())){
            cardName.setError("卡名不能为空");
            cardName.requestFocus();
            return false;
        }
        if(TextUtils.isEmpty(cardQuickKey.getText().toString().trim())){
            cardQuickKey.setError("快速检索不能为空");
            cardQuickKey.requestFocus();
            return false;
        }
        return true;
    }

    /**
     * 获取所有的账号密码对
     * @return
     */
    private List<KeyPass> getAllKeyPass() {
        int cc = viewKeypass.getChildCount();
        if(cc==0){
            return null;
        }
        List<KeyPass> kps = new ArrayList<>();
        for (int i = 0;i<cc;i++){
            KeyPassItem kpi = (KeyPassItem) viewKeypass.getChildAt(i);
            KeyPass kp = kpi.getKeyPass();
            if(kp!=null){
                kps.add(kp);
            }
        }
        return kps;
    }


    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        cardName = (EditText)findViewById( R.id.card_name );
        cardQuickKey = (EditText)findViewById( R.id.card_quick_key );
        cardDesc = (EditText) findViewById(R.id.card_desc);
        viewKeypass = (LinearLayout)findViewById( R.id.view_keypass );
        addKeyPass = (Button)findViewById( R.id.add_key_pass );
        save = (Button)findViewById( R.id.save );

        addKeyPass.setOnClickListener( this );
        save.setOnClickListener(this);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_card, container, false);
    }


}
