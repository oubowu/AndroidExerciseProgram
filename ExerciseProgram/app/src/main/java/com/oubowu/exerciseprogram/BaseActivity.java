package com.oubowu.exerciseprogram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.ButterKnife;

/**
 * 类名： BaseActivity
 * 作者: oubowu
 * 时间： 2015/11/28 9:40
 * 功能：
 * svn版本号:$$Rev$$
 * 更新时间:$$Date$$
 * 更新人:$$Author$$
 * 更新描述:
 */
public abstract class BaseActivity extends AppCompatActivity {


    /*class ExampleActivity extends Activity {
        @Bind(R.id.user)
        EditText username;
        @Bind(R.id.pass) EditText password;

        @BindString(R.string.login_error)
        String loginErrorMessage;

        @OnClick(R.id.submit) void submit() {
            // TODO call server...
        }

        @Override public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.simple_activity);
            ButterKnife.bind(this);
            // TODO Use fields...
        }
    }*/

    protected abstract int provideLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null)
            setSupportActionBar(toolbar);

        initView();
        initData();

    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T getViewById(int id) {
        return (T) findViewById(id);
    }

}
