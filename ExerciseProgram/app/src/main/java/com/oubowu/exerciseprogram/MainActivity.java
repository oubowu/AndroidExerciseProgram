package com.oubowu.exerciseprogram;

import android.content.Intent;
import android.widget.Button;

import com.oubowu.exerciseprogram.aigestudiostudy.AigeActivity;
import com.oubowu.exerciseprogram.databinding.DataBindingActivity;
import com.oubowu.exerciseprogram.jsonparse.JsonParseActivity;
import com.oubowu.exerciseprogram.floatlayout.FloatLeafActivity;
import com.oubowu.exerciseprogram.kotlin.LoginActivity;
import com.oubowu.exerciseprogram.mvp.MvpActivity;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshActivity;
import com.oubowu.exerciseprogram.rxjava.RxJavaActivity;
import com.oubowu.exerciseprogram.tailview.SettingsActivity;
import com.oubowu.exerciseprogram.toucheventdelivery.TouchEventActivity;
import com.oubowu.exerciseprogram.viewdraghelper.SwipeActivity;

import butterknife.Bind;
import butterknife.BindString;
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

    @OnClick(R.id.bt_rxjava)
    void rxJava() {
        startActivity(new Intent(this, RxJavaActivity.class));
    }

    @OnClick(R.id.bt_json)
    void jsonParse() {
        startActivity(new Intent(this, JsonParseActivity.class));
    }

    @OnClick(R.id.bt_drag_helper)
    void dragHelper() {
        startActivity(new Intent(this, SwipeActivity.class));
    }

    @OnClick(R.id.bt_binding)
    void binding() {
        startActivity(new Intent(this, DataBindingActivity.class));
    }

    @OnClick(R.id.bt_tail)
    void tail() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @OnClick(R.id.bt_touch)
    void touch() {
        startActivity(new Intent(this, TouchEventActivity.class));
    }

    @OnClick(R.id.bt_aige)
    void aige() {
        startActivity(new Intent(this, AigeActivity.class));
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
        // 将button设置给一个静态的对象的成员变量，MainActivity在Ondestroy后
        // 静态对象仍然持有MainActivity的实例，内存发生泄漏
        RetainDataModel.getInstance().setRetainedView(mMvpBt);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 回收掉静态对象
        RetainDataModel.getInstance().recycle();
    }
}
