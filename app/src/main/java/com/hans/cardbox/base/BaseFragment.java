package com.hans.cardbox.base;


import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.hans.cardbox.iinterface.CallbackAvailableListener;
import com.hans.cardbox.iinterface.MessageListener;
import com.hans.cardbox.tools.LG;
import com.hans.cardbox.tools.ToastUtil;


public abstract class BaseFragment extends Fragment implements MessageListener,View.OnClickListener{
    protected Handler mHandler;
    protected String LOG_TAG;
    protected BaseActivity mActivity;
    protected BaseFragment mSelf;
    protected OnFragmentInteractionListener mConector;

    public BaseFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LOG_TAG = this.getClass().getSimpleName();
        mSelf = this;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return createView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandler = new Handler();
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract View createView(LayoutInflater inflater, ViewGroup container,
                                       Bundle savedInstanceState);

    protected View findViewById(int id) {
        return getView().findViewById(id);
    }

    protected void logWarnMsg(String msg) {
        LG.w(LOG_TAG, msg);
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (BaseActivity) activity;
        try {
            mConector = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mConector = null;
    }




    protected void toast(String msg){
        ToastUtil.showMessageShort(msg);
    }

    @Override
    public boolean isAvaliable() {
        return isAdded();
    }

    @Override
    public void notifyMsg(Uri uri){

    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onClick(View v) {

    }
}
