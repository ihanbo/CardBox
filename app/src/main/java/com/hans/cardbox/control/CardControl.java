package com.hans.cardbox.control;

import android.net.Uri;
import android.text.TextUtils;

import com.hans.cardbox.App;
import com.hans.cardbox.iinterface.BackRunnable;
import com.hans.cardbox.iinterface.MessageListener;
import com.hans.cardbox.model.Card;
import com.hans.cardbox.model.KeyPass;
import com.hans.cardbox.model.LocalCardChange;
import com.hans.cardbox.model.QuickKey;
import com.hans.cardbox.tools.BackThreadPool;
import com.hans.cardbox.tools.TT;
import com.hans.cardbox.tools.UriTools;
import com.hans.mydb.in.DD;
import com.hans.mydb.model.SeLectInfo;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by hanbo1 on 2015/8/10.
 */
public class CardControl {

    public static void addCard(final Card card, MessageListener listener) {
        if (TextUtils.isEmpty(card.cardID)) {
            throw new RuntimeException("主键未设置啊");
        }
        BackThreadPool.post(new BackRunnable(listener) {
            @Override
            public void run() {
                DD.saveSingle(card);
                List<KeyPass> kps = card.kps;
                if (!TT.isEmpty(kps)) {
                    DD.saveaLot(kps);
                }
                List<QuickKey> qks = card.quickKeys;
                if (!TT.isEmpty(qks)) {
                    DD.saveaLot(qks);
                }

                LocalCardChange lc = new LocalCardChange(card.cardID);
                lc.setTypeAdd();

                DD.saveSingle(lc);
                Uri uri = UriTools.getAsyncMsgUri(true, "添加成功", null);
                notifyMsgIfAvailable(uri);
            }
        }, null);
    }


    /**
     * 完整版的获取card信息（包括：账户键值对list、搜索keyList）<br/>
     * 正常查询显示Card不需要查询“keyList”，应为他对用户基本没啥意义
     *
     * @param cardID
     * @return
     */
    private static Card getCard(String cardID) {
        if (TextUtils.isEmpty(cardID)) {
            return null;
        }
        Card card = null;
        try {
            card = DD.get(Card.class, SeLectInfo._PkValueIs(Card.class, cardID)).get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return card;
        }
        List<KeyPass> kps = DD.get(KeyPass.class, SeLectInfo.columnIs(new String[]{Card.getPKColumn()}, new String[]{cardID}));
        List<QuickKey> qks = DD.get(QuickKey.class, SeLectInfo.columnIs(new String[]{Card.getPKColumn()}, new String[]{cardID}));
        card.kkps(kps);
        card.quicyKeys(qks);
        return card;
    }

    public static void syncCard(final MessageListener listener) {
        BackThreadPool.post(new BackRunnable(listener) {
            @Override
            public void run() {
                List<LocalCardChange> allChanged = DD.get(LocalCardChange.class, null);
                notifyMsgIfAvailable(UriTools.getPromptUri("同步数量：" + allChanged.size()));
                List<BmobObject> inserts = new ArrayList<>();
                List<BmobObject> updates = new ArrayList<>();
                for (int i = 0, size = allChanged.size(); i < size; i++) {
                    LocalCardChange lc = allChanged.get(i);
                    Card card = getCard(lc.carID);
                    if (TextUtils.isEmpty(card.bmobID)) {
                        inserts.add(card);
                    } else {
                        updates.add(card);
                    }
                }
                if (!TT.isEmpty(inserts)) {
                    new BmobObject().insertBatch(App.mApp, inserts, new SaveListener() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(int i, String s) {

                        }
                    });
                }
                if (!TT.isEmpty(updates)) {
                    new BmobObject().updateBatch(App.mApp, updates, new UpdateListener() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onFailure(int code, String msg) {
                        }
                    });
                }
            }
        }, null);
    }


}
