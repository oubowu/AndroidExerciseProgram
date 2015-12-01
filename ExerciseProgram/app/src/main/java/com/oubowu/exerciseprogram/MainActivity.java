package com.oubowu.exerciseprogram;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.oubowu.exerciseprogram.floatlayout.FloatLeafActivity;
import com.oubowu.exerciseprogram.kotlin.LoginActivity;
import com.oubowu.exerciseprogram.mvp.MvpActivity;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshActivity;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Bind(R.id.bt_mvp)
    Button mMvpBt;

    @BindString(R.string.mvp_text)
    String mMvpText;

    @OnClick(R.id.bt_leaf)
    void leaf() {
        startActivity(new Intent(this, FloatLeafActivity.class));
    }

    @OnClick(R.id.bt_mvp)
    void mvp() {
        startActivity(new Intent(this, MvpActivity.class));
    }

    @OnClick(R.id.bt_refresh)
    void refresh() {
        startActivity(new Intent(this, RefreshActivity.class));
    }

    @OnClick(R.id.bt_kotlin)
    void kotlin() {
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        mMvpBt.setText(mMvpText);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
