package com.hans.cardbox.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hans.cardbox.model.Account;
import com.hans.cardbox.R;
import com.hans.cardbox.base.BaseFragment;
import com.hans.cardbox.iinterface.BombInsertListener;
import com.hans.cardbox.tools.DESUtil;
import com.hans.cardbox.tools.MD5;
import com.hans.cardbox.tools.UriTools;
import com.hans.cardbox.tools.UserUtils;

public class RegisterFragment extends BaseFragment {


    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private EditText name;
    private EditText mobile;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mAuthKey;
    private View mProgressView;
    private Button mEmailSignInButton;

    private View mLoginFormView;


    public RegisterFragment() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        mEmailView = (EditText) findViewById(R.id.email);
        mAuthKey =(EditText)findViewById(R.id.auth_key);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        mProgressView = findViewById(R.id.login_progress);

        mLoginFormView = findViewById(R.id.login_form);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register, container, false);
    }


    public void attemptRegister() {
        // Reset errors.
        mEmailView.setError(null);
        name.setError(null);
        mobile.setError(null);
        mPasswordView.setError(null);

        String names = name.getText().toString().trim();
        String mobiles = mobile.getText().toString().trim();
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            String token = MD5.getMD5ofStr(email + password);
            String primaryKey = System.currentTimeMillis()+password;
            Account account = new Account(email, password,names,mobiles,token, primaryKey);
            register(account);
        }
    }

    private boolean isEmailValid(String email) {
        return email.length()>4;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    private void register(Account account) {
        showProgress(true);
        account.insertObject(mActivity, new BombInsertListener<Account>(this,account) {
            @Override
            public void onSuccessed(Account account) {
                showProgress(false);
                toast("注册成功");
                UserUtils.saveAccount(account);
                notifyActivitySucc();
            }

            @Override
            public void onFailed(String s) {
                showProgress(false);
                toast("注册失败：" + s);
            }
        });
    }

    private void notifyActivitySucc() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mConector.onFragmentInteraction(UriTools.getUri(UriTools.HOST_USER_CENTER, UriTools.PATH_USER_LOGIN, "register=true"));
            }
        }, 200);
    }

    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

}
