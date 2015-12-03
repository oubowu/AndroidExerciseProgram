package com.oubowu.exerciseprogram;

import android.content.Intent;
import android.widget.Button;

import com.oubowu.exerciseprogram.jsonParse.JsonParseActivity;
import com.oubowu.exerciseprogram.floatlayout.FloatLeafActivity;
import com.oubowu.exerciseprogram.kotlin.LoginActivity;
import com.oubowu.exerciseprogram.mvp.MvpActivity;
import com.oubowu.exerciseprogram.refreshrecyclerview.RefreshActivity;
import com.oubowu.exerciseprogram.rxjava.RxJavaActivity;

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
