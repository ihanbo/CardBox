package com.hans.cardbox.control;

import com.hans.cardbox.model.Card;
import com.hans.cardbox.model.KeyPass;
import com.hans.cardbox.model.LocalChange;
import com.hans.cardbox.tools.TT;
import com.hans.mydb.in.DD;

import java.util.List;

/**
 * Created by hanbo1 on 2015/8/10.
 */
public class CardControl {

    public static void addCard(Card card){
        DD.saveSingle(card);
        List<KeyPass> kps = card.data;
        if(!TT.isEmpty(kps)){
            DD.saveaLot(kps);
        }
        LocalChange lc = new LocalChange(Card.class.getName(),DD.getPK(Card.class).getColumn());
        DD.saveSingle(lc);
    }


}
