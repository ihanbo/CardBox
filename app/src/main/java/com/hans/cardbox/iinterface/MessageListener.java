package com.hans.cardbox.iinterface;

import android.net.Uri;

/**
 * Created by hanbo1 on 2015/8/11.
 */
public interface MessageListener extends CallbackAvailableListener {
    void notifyMsg(Uri uri);
}
