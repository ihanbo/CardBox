package com.hans.cardbox.tools;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.widget.Toast;
import com.hans.cardbox.App;

public class ToastUtil {

    private static Handler handler = new Handler(Looper.getMainLooper());
    private static Toast toast = null;



    public static void showMessageShort(int strId) {
        showMessage(strId, false);
    }

    public static void showMessageShort(String msg) {
        showMessage(msg, false);
    }


    public static void showMessageLong(String msg) {
        showMessage( msg, true);
    }

    public static void showMessageLong(int strId) {
        showMessage(strId,true);
    }




    private static void showMessage(String msg, boolean showLong) {
        if (TextUtils.isEmpty(msg)||getToast()==null) {
            return;
        }
        toast.setDuration(showLong? Toast.LENGTH_LONG: Toast.LENGTH_SHORT);
        toast.setText(msg);
        Runnable run = new ToastRunnable(toast);
        if(Looper.myLooper()== Looper.getMainLooper()){
            run.run();
        }else{
            handler.post(run);
        }
    }
    private static void showMessage(final int msgID, boolean showLong) {
        showMessage(ToolBox.getString(msgID),showLong);
    }


    private static Toast getToast() {
        if (toast == null) {
            synchronized (ToastUtil.class){
                if(toast == null){
                    try {
                        toast = Toast.makeText(App.mApp, "", Toast.LENGTH_LONG);
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                }
            }
        }
        return toast;
    }

    /**
     * 用于Toast的显示
     */
    private static class ToastRunnable implements Runnable {
        private Toast mToast;

        public ToastRunnable(Toast mToast) {
            this.mToast = mToast;
        }

        @Override
        public void run() {
            mToast.show();
        }
    }


}
