package com.hans.cardbox.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.hans.cardbox.model.Account;
import com.hans.cardbox.R;
import com.hans.cardbox.base.BaseFragment;
import com.hans.cardbox.iinterface.BmobFindListener;
import com.hans.cardbox.tools.MD5;
import com.hans.cardbox.tools.UriTools;
import com.hans.cardbox.tools.UserUtils;

import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;

public class LoginFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public static LoginFragment newInstance() {
            LoginFragment fragment = new LoginFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
        return fragment;
    }

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private Button mEmailSignInButton;
    private Button mRegister;

    private View mLoginFormView;


    public LoginFragment() {
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        getActivity().getLoaderManager().initLoader(0, null, this);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        mRegister = (Button) findViewById(R.id.button_register);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyToRegister();
            }
        });
        mProgressView = findViewById(R.id.login_progress);

        mLoginFormView = findViewById(R.id.login_form);
    }

    private void notifyToRegister() {
        Uri uri = UriTools.getUri(UriTools.HOST_USER_CENTER,UriTools.PATH_USER_REGISTER);
        mConector.onFragmentInteraction(uri);
    }

    @Override
    protected View createView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }



    public void attemptLogin() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

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
            showProgress(true);
            login(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }


    private void login(String key, String password){
        BmobQuery<Account> query = new BmobQuery<Account>();
        query.addWhereEqualTo("key", key);
        query.addWhereEqualTo("password", password);
        query.findObjects(getActivity(), new BmobFindListener<Account>(this) {

            @Override
            public void onSucced(List<Account> list) {
                if(list!=null&&list.size()>0){
                    toast("登陆成功");
                    UserUtils.saveAccount(list.get(0));
                    notifyActivitySucc();
                }else{
                    mEmailView.setError("账号或密码错误");
                    mEmailView.requestFocus();
                    toast("登陆失败");
                }
            }

            @Override
            public void onFailed(int code, String msg) {
                showProgress(false);
                mEmailView.setError(msg);
                mEmailView.requestFocus();
                toast("登陆失败："+msg+code);
            }

        });
    }

    private void notifyActivitySucc() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mConector.onFragmentInteraction(UriTools.getUri(UriTools.HOST_USER_CENTER, UriTools.PATH_USER_LOGIN, null));
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(),
                // Retrieve kps rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<String>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(mActivity,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }
}
